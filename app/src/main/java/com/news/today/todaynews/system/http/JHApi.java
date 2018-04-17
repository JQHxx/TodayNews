package com.news.today.todaynews.system.http;

import com.news.today.http.annotation.ParamType;
import com.news.today.http.annotation.RequestMethod;
import com.news.today.http.api.IApi;
import com.news.today.http.api.LfApi;
import com.news.today.http.parser.DefaultResultParse;

import java.lang.reflect.Type;

/**
 * Created by anson on 2018/4/15.
 */

public class JHApi extends LfApi {

    //聚合网络的get请求
    public static IApi sendHttp(String path, @RequestMethod int requestMethod, Type resultType) {
        JHApi api = new JHApi();
        api.requestMethod = requestMethod;
        api.paramType = ParamType.normal;
        api.path = path;
        api.resultType = resultType;
        api.resultParse = DefaultResultParse.getInstance();
        api.host = Hosts.jhData;
        return api;
    }
}
