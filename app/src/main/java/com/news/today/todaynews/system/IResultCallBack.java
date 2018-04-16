package com.news.today.todaynews.system;

import com.news.today.http.parser.IResult;

/**
 * Created by ye on 2017/4/6.
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
