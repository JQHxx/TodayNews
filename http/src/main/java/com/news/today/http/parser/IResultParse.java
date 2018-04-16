package com.news.today.http.parser;

/**
 * Created by yh on 2016/4/20.
 */
public interface IResultParse {
    /**
     * 字符串解析成结果对象
     *
     * @param response
     * @param request
     * @return
     */
    IResult parseResult(IResponse response, IRequest request) throws RuntimeException;

    @Deprecated
    IResult onException(ICall iCall, Exception e);
}
