package com.news.today.http;

import com.kaike.la.kernal.http.IApi;
import com.kaike.la.kernal.http.IRequest;
import com.kaike.la.kernal.lf.helper.HttpHelper;
import com.kaike.la.kernal.util.lang.Maps;
import com.news.today.http.api.IApi;
import com.news.today.http.parser.IResult;

/**
 * Created by ye on 2017/4/14.
 */

public class LfManager {
    protected <T> IResult<T> execute(IApi iApi)  {
        return HttpHelper.execute(iApi, Maps.mapNull);
    }

    protected <T> IResult<T> execute(IApi iApi, Object params)  {
        return HttpHelper.execute(iApi, params);
    }

}
