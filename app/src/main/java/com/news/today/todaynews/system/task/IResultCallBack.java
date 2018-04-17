package com.news.today.todaynews.system.task;

import com.news.today.http.parser.IResult;

/**
 * Created by anson on 2018/4/15.
 */

public interface IResultCallBack<T> {

    /**
     * 处理成功
     *
     * @param result
     */
    void onSuccess(IResult<T> result);

    /**
     * 处理失败
     *
     * @param result
     */
    boolean onFailure(IResult result);
}
