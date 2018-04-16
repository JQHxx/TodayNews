package com.news.today.http.builder;

import java.util.Map;

/**
 * Created by ye on 2017/4/5.
 */

public interface IRequestBuilder {

    /**
     * 设置参数
     *
     * @param params
     * @return
     */
    IRequestBuilder setParams(Object params);

    /**
     * 设置头部
     *
     * @param headers
     * @return
     */

    IRequestBuilder setHeaders(Map<String, String> headers);


    /**
     * 设置默认参数
     *
     * @param defaultParams
     * @return
     */
    IRequestBuilder setDefaultParams(String defaultParams);


    /**
     * 开始构建
     *
     * @return
     */
    IRequest build();
}
