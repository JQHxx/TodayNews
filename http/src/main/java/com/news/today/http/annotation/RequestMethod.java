package com.news.today.http.annotation;

/**
 * 请求方式
 * Created by yh on 2016/4/15.
 */

import android.support.annotation.IntDef;

import static com.news.today.http.annotation.RequestMethod.Delete;
import static com.news.today.http.annotation.RequestMethod.Get;
import static com.news.today.http.annotation.RequestMethod.Post;
import static com.news.today.http.annotation.RequestMethod.Put;

@IntDef({Get, Post, Put, Delete})
public @interface RequestMethod {
    int Get = 1;
    int Post = 2;
    int Put = 3;
    int Delete = 4;

}
