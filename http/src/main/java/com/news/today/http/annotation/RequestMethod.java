package com.news.today.http.annotation;

/**
 * Created by anson on 2018/4/8.
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
