package com.news.today.http;

import android.content.Context;


import com.news.today.http.api.IApi;
import com.news.today.http.parser.IResult;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by anson on 2018/4/15.
 */

public class LfManager {

    protected <T> IResult<T> execute(Context context,IApi iApi)  {
        return HttpHelper.execute(context,iApi, null);
    }

    //把封装好的请求方式及参数  去网络调度器中执行
    protected <T> IResult<T> execute(Context context,IApi iApi, Map<String,Object> params)  {
        return HttpHelper.execute(context,iApi, params);
    }

}
