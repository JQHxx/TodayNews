package com.news.today.http.annotation;

import android.support.annotation.IntDef;


import static com.news.today.http.annotation.ParamType.file;
import static com.news.today.http.annotation.ParamType.normal;
import static com.news.today.http.annotation.ParamType.json;

/**
 * Created by anson on 2018/4/8.
 */
@IntDef({normal, json, file})
public @interface ParamType {
    int normal = 1;
    int json = 2;
    int file = 3;
}
