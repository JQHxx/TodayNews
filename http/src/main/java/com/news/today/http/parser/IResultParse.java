package com.news.today.http.parser;

import com.news.today.http.api.IApi;
import com.news.today.http.callback.IResponse;

/**
 * Created by anson on 2018/4/15.
 */
public interface IResultParse {
    /**
     * 字符串解析成结果对象
     *
     * @param request
     * @return
     */
    IResult parseResult(IResponse response, IApi request) throws RuntimeException;

}
