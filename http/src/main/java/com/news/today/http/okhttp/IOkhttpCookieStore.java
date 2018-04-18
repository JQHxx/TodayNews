package com.news.today.http.okhttp;


import com.news.today.http.cookie.ICookieIterator;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * Created by anson on 2018/4/8.
 */
public interface IOkhttpCookieStore {
    void add(HttpUrl url, Cookie item);

    List<Cookie> get(HttpUrl url);

    boolean removeAll();

    void remove(HttpUrl url);

    boolean iteratorCookies(String url, ICookieIterator cookieIterator);
}