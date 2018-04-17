package com.news.today.http.callback;


import com.news.today.http.api.IApi;



/**
 * 缓存监听器
 * Created by yh on 2016/7/15.
 */
public interface IRequestCacheListener {


    /**
     * 在网络失败的时候调用
     *
     * @param request
     * @return
     */
    CacheResponse readCacheWhenResponseError(IApi request);


    /**
     * 塞回缓存
     *
     * @param request
     * @param response
     */
    void putCache(IApi request, CacheResponse response);
}
