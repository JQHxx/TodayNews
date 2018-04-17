package com.news.today.http.callback;


import android.util.Log;

import com.news.today.http.annotation.RequestMethod;
import com.news.today.http.api.IApi;
import com.news.today.http.parser.IResult;
import com.news.today.http.util.JsonHelper;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by anson on 2018/4/15.
 */

public class RequestListener implements IRequestListener {
    @Override
    public void beforeRequest(ICall call) {
        if (call instanceof ApiCall) {
            ApiCall apiCall = (ApiCall) call;
            apiCall.setBeforeTime(System.currentTimeMillis());
        }
        IApi api = call.getRequest();
        Map<String, Object> params = api.getParams();
        int requestMethod = api.getRequestMethod();
        String apiName = "";
        switch (requestMethod) {
            case RequestMethod.Get:
                apiName = "Get";
                break;
            case RequestMethod.Post:
                apiName = "Post";
                break;
        }
        String paramsJson = JsonHelper.toJSONString(params);
        String url = call.getUrl();
        Log.w("入参", String.format("request(%s)：%s请求\nurl=%s\nparams=%s", api.hashCode(), apiName,  url, paramsJson));
    }



    @Override
    public void beforeParse(ICall call, IResponse response) {
        if (call instanceof ApiCall) {
            ApiCall apiCall = (ApiCall) call;
            apiCall.setBefroreParseTime(System.currentTimeMillis());
        }
    }

    public void afterRequest(ICall call, IResponse response, IResult result) {
        String body = response.getBody();
        IApi iApi = call.getRequest();
        Type type = iApi.getResultType();
        long totalUseTime = 0;
        long networkUseTime = 0;
        if (call instanceof ApiCall) {
            ApiCall apiCall = (ApiCall) call;
            long now = System.currentTimeMillis();
            totalUseTime = now - apiCall.getBeforeTime();
            networkUseTime = apiCall.getBefroreParseTime() - apiCall.getBeforeTime();
        }

        Log.w("出参", String.format("request(%s)\n返回结果类型：%s\n返回对象：%s\n总共用时：%s毫秒,其中网络用时：%s毫秒",iApi.hashCode(), type, body, totalUseTime, networkUseTime));

        if (totalUseTime >= 2000) {
            Log.w("出参", String.format("\"request(%s)请求时间过长请优化！\"", iApi.hashCode()));
        }
    }
}
