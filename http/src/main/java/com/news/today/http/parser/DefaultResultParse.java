package com.news.today.http.parser;

import com.news.today.http.api.IApi;
import com.news.today.http.util.JsonHelper;

import java.lang.reflect.Type;

/**
 * Created by anson on 2018/4/15.
 */
public class DefaultResultParse extends AbstractResultParse {

    private static DefaultResultParse instance = new DefaultResultParse();

    private DefaultResultParse() {
    }

    public static DefaultResultParse getInstance() {
        return instance;
    }

    @Override
    public Result parseResultCommon(String json, Type type, IApi iApi) {
        //可根据不同的业务类型注入不同的业务解析器
        Object object = JsonHelper.fromJson(json, type);
        if (object == null) {
            return Result.failed(ResultCodes.CODE_NETWORKERROR);
        }
        return Result.success(ResultCodes.CODE_SUCCESS, object);
    }
}