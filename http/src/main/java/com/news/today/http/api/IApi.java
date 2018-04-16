package com.news.today.http.api;

import android.support.annotation.NonNull;

import com.news.today.http.annotation.ContentType;
import com.news.today.http.annotation.ParamType;
import com.news.today.http.annotation.ParmBuildWay;
import com.news.today.http.annotation.RequestMethod;
import com.news.today.http.builder.IParamBuilder;
import com.news.today.http.builder.IRequestBuilder;
import com.news.today.http.parser.IResultParse;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by yh on 2016/4/15.
 */
public interface IApi {

    //获取url
    @NonNull
    String getUrl();

    //请求方式
    @RequestMethod
    int getRequestMethod();

    //获取返回结果
    Type getResultType();

    //请求类型
    @ContentType
    String getContentType();

    //参数类型
    @ParamType
    int getParamType();

    String getDefaultParams();


    Object getParamData();

    //是否需要缓存请求
    boolean enableCache();

    /**
     * 请求构建起
     *
     * @return
     */
    IRequestBuilder newRequestBuilder();

    /**
     * 参数构建起
     *
     * @return
     */
    IParamBuilder getParamBuilder();

    /**
     * 结果解析器
     *
     * @return
     */
    IResultParse getResultParse();

    /**
     * 头部参数
     *
     * @return
     */
    Map<String, String> getHeaders();

    /**
     * 缓存的key
     *
     * @return
     */
    String getCacheKey();

    //参数构造路径
    @ParmBuildWay
    int parmBuildWay();
}
