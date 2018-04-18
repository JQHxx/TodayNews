package com.news.today.http.callback;

import com.news.today.http.parser.IResult;

/**
 * Created by anson on 2018/4/8.
 */
public interface IRequestListener {
    /**
     * 请求开始前
     */
    void beforeRequest(ICall call);

    /**
     * 开始json解析前
     *
     * @param call
     * @param response
     */
    void beforeParse(ICall call, IResponse response);

    /**
     * 解析之后
     */
    void afterRequest(ICall call, IResponse response, IResult result);
}
