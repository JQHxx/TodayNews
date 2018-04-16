package com.news.today.http.parser;



/**
 * Created by ye on 2017/5/12.
 */

public abstract class LfResultParese implements IResultParse {
    @Override
    public final IResult onException(ICall iCall, Exception e) {
//        IApi api = iCall.getRequest().getApi();
//        BtlLog.network.e(e, "网络层异常，url=%s", api.getUrl());
//        return LfResult.fail(IResult.CODE_LOCAL_EXCEPTION, e);
        return null;
    }
}
