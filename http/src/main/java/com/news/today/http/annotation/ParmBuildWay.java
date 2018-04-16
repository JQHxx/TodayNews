package com.news.today.http.annotation;

/**
 * 请求方式
 * Created by yh on 2016/4/15.
 */

import android.support.annotation.IntDef;

import static com.news.today.http.annotation.ParmBuildWay.APPEND_URL;
import static com.news.today.http.annotation.ParmBuildWay.INTO_BODY;

@IntDef({APPEND_URL, INTO_BODY})
public @interface ParmBuildWay {
    int APPEND_URL = 1;
    int INTO_BODY = 2;

}
