package com.news.today.todaynews.system.http;

import com.news.today.http.annotation.ParamType;
import com.news.today.http.annotation.RequestMethod;
import com.news.today.http.api.IApi;
import com.news.today.http.api.LfApi;
import com.news.today.http.parser.NgResultParse;

import java.lang.reflect.Type;

/**
 * Created by anson on 2018/4/15.
 */

public class JHApi  extends LfApi {

    public static IApi sendHttp(String serviceName,@RequestMethod int requestMethod ,Type resultType) {
        JHApi api = new JHApi();
        api.requestMethod = requestMethod;
        api.paramType = ParamType.normal;
        api.paramData = serviceName;
        api.resultType = resultType;
        api.paramBuilder = NgParamBuilder.getInstance();
        api.resultParse = NgResultParse.getInstance();
        api.host = Hosts.jhData;
//        api.headers = normalHeaders;
        return api;
    }

}
