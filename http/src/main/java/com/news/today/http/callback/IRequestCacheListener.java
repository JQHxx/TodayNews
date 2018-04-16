package com.news.today.http.callback;


import com.news.today.http.builder.IRequest;

import java.net.CacheResponse;

/**
 * 缓存监听器
 * Created by yh on 2016/7/15.
 */
public interface IRequestCacheListener {

    /**
     * 网络未开始前
     *
     * @param request
     * @return
     */
    CacheResponse readCacheBeforeExecute(IRequest request);

    /**
     * 在网络失败的时候调用
     *
     * @param request
     * @return
     */
    CacheResponse readCacheWhenResponseError(IRequest request);


    /**
     * 塞回缓存
     *
     * @param request
     * @param response
     */
    void putCache(IRequest request, CacheResponse response);
}
