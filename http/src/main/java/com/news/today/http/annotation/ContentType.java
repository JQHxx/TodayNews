package com.news.today.http.annotation;


import android.support.annotation.StringDef;

import static com.news.today.http.annotation.ContentType.APP_FORM_URLENCODED;
import static com.news.today.http.annotation.ContentType.APP_JSON;
import static com.news.today.http.annotation.ContentType.APP_OCTET_STREAM;
import static com.news.today.http.annotation.ContentType.MULTIPART_FORM_DATA;
import static com.news.today.http.annotation.ContentType.TEXT_HTML;
import static com.news.today.http.annotation.ContentType.TEXT_PLAIN;
import static com.news.today.http.annotation.ContentType.WILDCARD;

/**
 * Created by anson on 2018/4/15.
 */
@StringDef({APP_FORM_URLENCODED, APP_JSON, APP_OCTET_STREAM, MULTIPART_FORM_DATA, TEXT_HTML, TEXT_PLAIN, WILDCARD})
public @interface ContentType {
    String APP_FORM_URLENCODED = "application/x-www-form-urlencoded";
    String APP_JSON = "application/json";
    String APP_OCTET_STREAM = "application/octet-stream";
    String MULTIPART_FORM_DATA = "multipart/form-data";
    String TEXT_HTML = "text/html";
    String TEXT_PLAIN = "text/plain";
    String WILDCARD = "*/*";
}
