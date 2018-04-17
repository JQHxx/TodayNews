package com.news.today.http.callback;


import android.content.Context;
import android.text.TextUtils;

import com.news.today.http.api.IApi;
import com.news.today.http.util.ACache;


/**
 * Created by anson on 2018/4/15.
 */
public class CacheListener implements IRequestCacheListener {
    private ACache aCache;

    public CacheListener(Context context) {
        aCache = ACache.get(context);
    }

    /**
     * 当网络异常的时候从缓存中读取
     *
     * @param request
     * @return
     */
    @Override
    public CacheResponse readCacheWhenResponseError(IApi request) {
        if (request != null) {
            String cacheKey = request.getCacheKey();
            if (!TextUtils.isEmpty(cacheKey)) {
                Object result = aCache.getAsObject(cacheKey);
                if (result instanceof CacheResponse) {
                    return (CacheResponse) result;
                }
            }else{

            }
        }
        return null;
    }

    @Override
    public void putCache(IApi request, CacheResponse response) {
        if (request == null || response == null) {
            return;
        }
        String cacheKey = request.getCacheKey();
        if (TextUtils.isEmpty(cacheKey)) {
            return;
        }
        aCache.put(cacheKey, response);
    }
}
