package com.news.today.http.builder;

import com.news.today.http.api.IApi;

import java.util.Map;

/**
 * Created by yh on 2016/5/19.
 */
public interface IParamBuilder {
    /**
     * 组装参数
     *
     * @param inputs 传过来的参数
     * @return
     */
    Map<String, Object> buildParams(IApi api, Object... inputs);
}