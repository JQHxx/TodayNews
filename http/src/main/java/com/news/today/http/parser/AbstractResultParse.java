package com.news.today.http.parser;

import android.text.TextUtils;

import com.news.today.http.api.IApi;
import com.news.today.http.builder.IRequest;

import java.lang.reflect.Type;

/**
 * Created by yh on 2016/4/20.
 */
public abstract class AbstractResultParse extends LfResultParese {

    @Override
    public IResult parseResult(IResponse response, IRequest request) {
        IApi iApi = request.getApi();
        Type type = iApi.getResultType();
        String json = response.getBody();
        Result result = null;
        if (TextUtils.isEmpty(json)) {
            return Result.fail(ResultCodes.CODE_NETWORKERROR);
        }
        result = parseResultCommon(json, type, iApi);
        if (result == null) {//返回结果为空
            if (BuildHelper.isDebug()) {
                KernalLog.network.e("request(%s)解析失败\n返回结果类型:%s", request.hashCode(), type);
            }
            throw new NetworkResultParserException();
        }
        return result;
    }

    public abstract Result parseResultCommon(String json, Type type, IApi iApi);


}
