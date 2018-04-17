package com.news.today.http.api;

import android.support.annotation.NonNull;

import com.news.today.http.annotation.ContentType;
import com.news.today.http.annotation.ParamType;
import com.news.today.http.annotation.RequestMethod;
import com.news.today.http.parser.IResultParse;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by anson on 2018/4/17.
 */
public interface IApi {

    //获取url
    @NonNull
    String getUrl();
    //设置参数
    void  setParams(Map<String,Object> params);
    //获取参数
    Map<String,Object> getParams();
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
    //是否缓存到本地
    boolean enableCache();
    //结果解析器
    IResultParse getResultParse();
    //请求头
    Map<String, String> getHeaders();
    //缓存到本地的key值
    String getCacheKey();

}
