package com.news.today.http;


import android.content.Context;

import com.news.today.http.api.IApi;
import com.news.today.http.callback.CacheListener;
import com.news.today.http.callback.ICall;
import com.news.today.http.callback.RequestListener;
import com.news.today.http.exception.NetworkNotAvailableException;
import com.news.today.http.okhttp.OkhttpCookieManager;
import com.news.today.http.okhttp.OkhttpScheduler;
import com.news.today.http.okhttp.PersistentCookieStore;
import com.news.today.http.parser.IResult;
import com.news.today.http.util.ConnectUtil;

import java.util.Map;

import okhttp3.OkHttpClient;

/**
 * Created by anson on 2018/4/15.
 */

public class HttpHelper {
    private static HttpScheduler httpScheduler;

    public static HttpScheduler getHttpScheduler(Context context) {
        if (httpScheduler == null) {
            //默认提供okhttp 实现
            httpScheduler = new OkhttpScheduler(new OkHttpClient.Builder().cookieJar(new OkhttpCookieManager(new PersistentCookieStore(context))).build());
            //打印网络日志
            httpScheduler.setRequestListener(new RequestListener());
            //本地缓存
            httpScheduler.setCacheListener(new CacheListener(context));
        }
        return httpScheduler;
    }

    //可自定义HttpScheduler
    public static void  setHttpScheduler(HttpScheduler httpSchedulers) {
        httpScheduler = httpSchedulers;
    }

    public static <T> IResult<T> execute(Context context, IApi api, Map<String,Object> params) {
        if (!ConnectUtil.getNetworkState(context)) {
            // CODEREVIEW:  代码审核： 自定义的网络异常
            throw new NetworkNotAvailableException();
        }
        //设置参数
        api.setParams(params);
        //生成call
        ICall call = getHttpScheduler(context).newCall(api);
        IResult<T> result = getHttpScheduler(context).execute(call);
        return result;
    }

}
