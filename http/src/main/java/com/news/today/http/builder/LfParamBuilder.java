package com.news.today.http.builder;


import com.news.today.http.api.IApi;
import com.news.today.http.util.BeanUtil;

import java.util.Map;

/**
 * Created by yh on 2016/5/12.
 */
public class LfParamBuilder implements IParamBuilder {
    private static LfParamBuilder instance;

    protected LfParamBuilder() {

    }

    public static LfParamBuilder instance() {
        if (instance == null) {
            synchronized (LfParamBuilder.class) {
                if (instance == null) {
                    instance = new LfParamBuilder();
                }
            }
        }
        return instance;
    }

    @Override
    public Map<String, Object> buildParams(IApi api, Object... inputs) {
        Map<String, Object> resultMap = null;
        boolean needPrint = true;
        if (inputs != null) {
            if (inputs.length > 0) {
                try {
                    if (inputs[0] instanceof Map) {
                        resultMap = (Map<String, Object>) inputs[0];
                        needPrint = false;
                        //inputs[0]为map时inputs的长度判断不正确
                    } else {
                        resultMap = BeanUtil.po2Map(inputs[0]);
                    }
                } catch (Exception e) {
                }
                if (inputs.length > 1 && needPrint) {
                    if(inputs[0]!=null&&inputs[1]!=null){
                    }
                }
            }
        }
        return resultMap;
    }
}
