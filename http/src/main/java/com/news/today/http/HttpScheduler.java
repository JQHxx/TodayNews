package com.news.today.http;


import com.news.today.http.api.IApi;
import com.news.today.http.callback.CacheResponse;
import com.news.today.http.callback.ICall;
import com.news.today.http.callback.IRequestCacheListener;
import com.news.today.http.callback.IRequestListener;
import com.news.today.http.callback.IResponse;
import com.news.today.http.exception.NetWorkException;
import com.news.today.http.parser.DefaultResultParse;
import com.news.today.http.parser.IResult;
import com.news.today.http.parser.IResultParse;

/**
 * Created by anson on 2018/4/15.
 */
public abstract class HttpScheduler {
    //结果解析器
    private IResultParse defaultResultParse = DefaultResultParse.getInstance();
    //日志监听
    private IRequestListener requestListener;
    //缓存监听
    private IRequestCacheListener cacheListener;

    public HttpScheduler() {

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

    public abstract ICall newCall(IApi iApi);

    //执行网络请求
    public IResult execute(ICall iCall) {
        IApi api = iCall.getRequest();
        IResultParse resultParse = api.getResultParse();
        if (resultParse == null) {
            if (defaultResultParse == null) {
                return null;
            } else {
                resultParse = defaultResultParse;
            }
        }
        if (requestListener != null) {
            requestListener.beforeRequest(iCall);
        }

        IResponse reponse = null;
        boolean enableCache = api.enableCache();//是否需要缓存
        if (enableCache) {
            if (cacheListener == null) {
                enableCache = false;
            }
        }
        boolean needPutCache = enableCache;//是否需要在结束后放进缓存

        IResult result = null;
        if (reponse == null) {
            reponse = doExecute(iCall);
        } else {
            needPutCache = false;
        }
        if (requestListener != null) {
            requestListener.beforeParse(iCall, reponse);
        }

        if (reponse == null || !reponse.isSuccess()) {
            //网络不成功
            if (enableCache) {
                reponse = cacheListener.readCacheWhenResponseError(api);
                needPutCache = false;
            }
        }

        if (reponse != null && reponse.isSuccess()) {
            //开始解析result对象
            result = resultParse.parseResult(reponse, api);
        }

        if (result == null) {//网络异常
            throw new NetWorkException(null);
        } else if (needPutCache) {
            //需要塞回缓存
            if (!(reponse instanceof CacheResponse)) {
                //并非缓存的response
                CacheResponse cacheResponse = new CacheResponse(reponse.getBody());
                cacheListener.putCache(api, cacheResponse);
            }
        }
        if (requestListener != null) {
            requestListener.afterRequest(iCall, reponse, result);
        }
        return result;
    }

    public abstract IResponse doExecute(ICall iCall) throws NetWorkException;

}
