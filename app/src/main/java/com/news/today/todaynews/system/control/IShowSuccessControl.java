package com.news.today.todaynews.system.control;


import com.news.today.http.parser.IResult;

/**
 * Created by ye on 2017/5/11.
 */

public interface IShowSuccessControl<T> extends IControl{

      void onSuccess(IResult<T> result);

}
