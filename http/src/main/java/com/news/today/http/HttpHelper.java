package com.news.today.http;


import android.content.Context;

import com.news.today.http.api.IApi;
import com.news.today.http.builder.IRequestBuilder;
import com.news.today.http.callback.ICall;
import com.news.today.http.exception.NetworkNotAvailableException;
import com.news.today.http.okhttp.OkhttpCookieManager;
import com.news.today.http.okhttp.OkhttpScheduler;
import com.news.today.http.okhttp.PersistentCookieStore;
import com.news.today.http.parser.IResult;
import com.news.today.http.util.ConnectUtil;

import okhttp3.OkHttpClient;

/**
 * Created by yh on 2016/5/5.
 */
public class HttpHelper {
    private static HttpScheduler httpScheduler;

    public static HttpScheduler getHttpScheduler(Context context) {
        if (httpScheduler == null) {
            //默认提供okhttp 实现
            httpScheduler = new OkhttpScheduler(new OkHttpClient.Builder().cookieJar(new OkhttpCookieManager(new PersistentCookieStore(context))).build());
        }
        return httpScheduler;
    }

    //设置HttpScheduler
    public static void  setHttpScheduler(HttpScheduler httpSchedulers) {
        httpScheduler = httpSchedulers;
    }

    public static <T> IResult<T> execute(Context context,IApi api, Object params) {
        if (!ConnectUtil.getNetworkState(context)) {
            throw new NetworkNotAvailableException();
        }
        IRequestBuilder requestBuilder = api.newRequestBuilder();
        requestBuilder.setParams(params);
        ICall call = getHttpScheduler(context).newCall(requestBuilder.build());
        // REMAKE: 待重构 2018/4/15  这里需要考虑到 两个功能库 进行了耦合
//        TheadLocalHelper.TaskInfo taskInfo = TheadLocalHelper.getTaskInfo();
        String groupName = null;
        String taskName = null;
//        if (taskInfo != null) {
//            groupName = taskInfo.groupName;
//            taskName = taskInfo.taskName;
//        }
        IResult<T> result = getHttpScheduler(context).execute(call, groupName, taskName);
        return result;
    }




//    public static File download(File folder, String url, IDownloadProgress progress) {
//        if (!ConnectUtil.getNetworkState(ContextHelper.getAppContext())) {
//            throw new NetworkNotAvailableException();
//        }
//        if (folder == null) {
//            return null;
//        }
//        if (!folder.exists()) {
//            try {
//                folder.mkdirs();
//            } catch (Throwable throwable) {
//                KernalLog.network.e("make dirs error");
//            }
//        }
//        if (!folder.exists()) {
//            return null;
//        }
//        String pre = System.currentTimeMillis() + Strings.EMPTY;
//        File tempFile = null;
//        try {
//            tempFile = File.createTempFile(pre, ".temp", folder);
//        } catch (IOException e) {
//            KernalLog.network.e("create file error");
//        }
//
//        IApi api = LfApi.GET(url);
//        LfHttpRequest.Builder builder = new LfHttpRequest.Builder(api) {
//            @Override
//            public LfHttpRequest makeInstance() {
//                return new LfHttpRequest();
//            }
//        };
//        IRequest request = builder.build();
//        ICall call = getHttpScheduler().newCall(request);
//        getHttpScheduler().download(call, tempFile, progress);
//        return tempFile;
//    }

    /**
     * 取消分组
     *
     * @param groupName
     */
    public static void cancelGroup(final Context context, final String groupName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    getHttpScheduler(context).cancelGroup(groupName);
                } catch (Throwable throwable) {
                }
            }
        }).start();
    }


}
