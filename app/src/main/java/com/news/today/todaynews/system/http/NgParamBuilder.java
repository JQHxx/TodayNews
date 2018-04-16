package com.news.today.todaynews.system.http;


import com.news.today.http.api.IApi;
import com.news.today.http.builder.AbstractParamBuilder;

import java.util.LinkedHashMap;
import java.util.Map;



/**
 * Created by yh on 2016/6/30.
 */
public class NgParamBuilder extends AbstractParamBuilder {

    private NgParamBuilder() {

    }

    private static NgParamBuilder instance = new NgParamBuilder();

    public static NgParamBuilder getInstance() {
        return instance;
    }


    @Override
    public Map<String, Object> buildParams(IApi api, Object... inputs) {
        Map<String, Object> allParams = new LinkedHashMap<>();
        Object paramObj;
        String serviceName;
        if (inputs != null && inputs.length >= 2 && inputs[1] instanceof String) {
            paramObj = inputs[0];
            serviceName = (String) inputs[1];
        } else {
            return allParams;
        }
        allParams.put("client_id", CLIENT_ID);
        allParams.put("service_name", serviceName);

        long timestamp = System.currentTimeMillis();
        allParams.put("timestamp", timestamp);
        String paramStr = JsonHelper.toJSONString(paramObj);
        if (paramStr == null) {
            paramStr = EMPTY_PARAM;
        }
        allParams.put("param", paramStr);

        allParams.put("sign", getSignMd5(timestamp + "", paramStr, serviceName));
        allParams.put("secret_state", getSecretState());

        if (api instanceof KklApi) {
            KklApi ngApi = (KklApi) api;
            if (ngApi.isNeedToken()) {
                String loginToken = UserHelper.getToken();
                allParams.put("token", loginToken);
                allParams.put("strategy", DUBBO);
            }
        }
        return allParams;

    }


}
