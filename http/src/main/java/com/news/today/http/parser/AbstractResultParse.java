package com.news.today.http.parser;

import android.text.TextUtils;

import com.news.today.http.api.IApi;
import com.news.today.http.callback.IResponse;
import com.news.today.http.exception.NetworkResultParserException;

import java.lang.reflect.Type;

/**
 * Created by anson on 2018/4/15.
 */
public abstract class AbstractResultParse implements IResultParse {

    @Override
    public IResult parseResult(IResponse response, IApi iApi) {
        Type type = iApi.getResultType();
        String json = response.getBody();
        Result result = null;
        if (TextUtils.isEmpty(json)) {
            return Result.failed(ResultCodes.CODE_NETWORKERROR);
        }
        result = parseResultCommon(json, type, iApi);
        if (result == null) {//返回结果为空
            throw new NetworkResultParserException();
        }

        return result;
    }

    public abstract Result parseResultCommon(String json, Type type, IApi iApi);


}
