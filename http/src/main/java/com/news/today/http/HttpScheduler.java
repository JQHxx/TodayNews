package com.news.today.http;


import com.news.today.http.api.IApi;
import com.news.today.http.builder.IRequest;
import com.news.today.http.callback.ICall;
import com.news.today.http.callback.IRequestCacheListener;
import com.news.today.http.callback.IRequestListener;
import com.news.today.http.callback.IResponse;
import com.news.today.http.exception.NetWorkException;
import com.news.today.http.parser.IResult;
import com.news.today.http.parser.IResultParse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CacheResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 网络调度
 * Created by yh on 2016/4/15.
 */
public abstract class HttpScheduler {
    private IResultParse defaultResultParse;
    private Map<String, Map<String, ICall>> callPool;
    private IRequestListener requestListener;
    /**
     * 缓存监听器
     */
    private IRequestCacheListener cacheListener;

    public HttpScheduler() {
        callPool = new HashMap<>();
    }

    public void setRequestListener(IRequestListener requestListener) {
        this.requestListener = requestListener;
    }

    public void setDefaultResultParse(IResultParse defaultResultParse) {
        this.defaultResultParse = defaultResultParse;
    }

    public void setCacheListener(IRequestCacheListener cacheListener) {
        this.cacheListener = cacheListener;
    }

    public abstract ICall newCall(IRequest httpRequest);

    /**
     * 不建议使用
     *
     * @param iCall
     * @return
     */
    public IResult execute(ICall iCall) throws Throwable {
        return execute(iCall, null, null);
    }

    public IResult execute(ICall iCall, String groupName, String taskName) {
        IRequest request = iCall.getRequest();
        IApi api = request.getApi();
        IResultParse resultParse = api.getResultParse();
        if (resultParse == null) {
            if (defaultResultParse == null) {
                KernalLog.network.e("must define resultParse for this api");
                return null;
            } else {
                resultParse = defaultResultParse;
            }
        }
        if (requestListener != null) {
            requestListener.beforeRequest(iCall);
        }

        IResponse reponse = null;
        boolean enableCache = request.enableCache();//是否需要缓存
        if (enableCache) {
            if (cacheListener == null) {
                KernalLog.network.e("配置了缓存但并未配置缓存listener");
                enableCache = false;
            } else {
                reponse = cacheListener.readCacheBeforeExecute(request);
            }
        }
        boolean needPutCache = enableCache;//是否需要在结束后放进缓存

        IResult result = null;
        if (reponse == null) {
            reponse = execute_(iCall, groupName, taskName);
        } else {
            needPutCache = false;
            KernalLog.network.d("未请求网络,本地已经配置缓存");
        }
        if (requestListener != null) {
            requestListener.beforeParse(iCall, reponse);
        }

        if (reponse == null || !reponse.isSuccess()) {
            //网络不成功
            if (enableCache) {
                KernalLog.network.e("网络请求失败,已经从本地缓存中读取");
                reponse = cacheListener.readCacheWhenResponseError(request);
                needPutCache = false;
            }
        }

        if (reponse != null && reponse.isSuccess()) {
            //开始解析result对象
            result = resultParse.parseResult(reponse, request);
        }

        if (result == null) {//网络异常
            throw new NetWorkException(null);
        } else if (needPutCache) {
            //需要塞回缓存
            if (!(reponse instanceof CacheResponse)) {
                //并非缓存的response
                CacheResponse cacheResponse = new CacheResponse(reponse.getBody());
                cacheListener.putCache(request, cacheResponse);
            }
        }
        if (requestListener != null) {
            requestListener.afterRequest(iCall, reponse, result);
        }
        return result;
    }

    public String getResult(ICall iCall, String groupName, String taskName) throws NetWorkException {
        IResponse reponse = execute_(iCall, groupName, taskName);
        if (reponse != null) {
            return reponse.getBody();
        }
        return null;
    }

    private IResponse execute_(ICall iCall, String groupName, String taskName) {
        IResponse reponse;
        if (groupName != null && taskName != null) {
            Map<String, ICall> calls = callPool.get(groupName);
            if (calls == null) {
                calls = new HashMap<>();
                callPool.put(groupName, calls);
            }
            calls.put(taskName, iCall); //向请求队列添加请求
            reponse = doExecute(iCall);
            calls.remove(taskName);
        } else {
            reponse = doExecute(iCall);
        }
        return reponse;
    }


    public abstract IResponse doExecute(ICall iCall) throws NetWorkException;

    /**
     * 取消某个分组里的所有请求
     *
     * @param groupName
     */
    public void cancelGroup(String groupName) {
        Map<String, ICall> calls = callPool.get(groupName);
        if (calls != null) {
            Set<Map.Entry<String, ICall>> entrySet = calls.entrySet();
            for (Map.Entry<String, ICall> entry : entrySet) {
                ICall call = entry.getValue();
                call.cancel();
            }
            callPool.remove(groupName);
        }
    }

    public void download(ICall iCall, File outFile, IDownloadProgress downloadProgress) {
        if (outFile == null) {
            KernalLog.network.e("file is null");
            return;
        }
        FileOutputStream fos = null;
        InputStream inputStream = null;
        try {
            IResponse response = doExecute(iCall);
            inputStream = response.getInputStream();
            fos = new FileOutputStream(outFile);
            byte[] buf = new byte[2048];
            int len = inputStream.read(buf);
            long totalLength = response.getTotalLength();
            long finishLength = 0;
            for (; len != -1; len = inputStream.read(buf)) {
                fos.write(buf, 0, len);
                finishLength += len;
                if (downloadProgress != null) {
                    downloadProgress.onProgress(finishLength, totalLength);
                }
            }
            fos.flush();
            //如果下载文件成功，第一个参数为文件的绝对路径
        } catch (Throwable e) {
            KernalLog.network.e(e);
            if (outFile != null) {
                outFile.deleteOnExit();//出现异常的时候删除文件
            }
            throw new DownloadException(e);
        } finally {
            try {
                if (inputStream != null) inputStream.close();
            } catch (IOException e) {
                KernalLog.network.e(e);
            }
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
                KernalLog.network.e(e);
            }
        }
    }
}
