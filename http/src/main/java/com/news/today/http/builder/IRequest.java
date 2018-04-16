package com.news.today.http.builder;

import com.news.today.http.api.IApi;

import java.util.Map;

/**
 * Created by yh on 2016/5/12.
 */
public interface IRequest {
    Map<String, Object> getParams();

    /**
     * 取得api
     *
     * @return
     */
    IApi getApi();

    String getUrl();

    String getDefaultParams();

    Map<String, String> getHeaders();

    /**
     * 是否需要缓存
     *
     * @return
     */
    boolean enableCache();

    /**
     * 缓存的key
     *
     * @return
     */
    String getCacheKey();
}
