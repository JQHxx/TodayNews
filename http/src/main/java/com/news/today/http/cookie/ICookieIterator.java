package com.news.today.http.cookie;

/**
 * Created by yh on 2016/12/22.
 */

public interface ICookieIterator {
    boolean iterator(String urlHost, String cookieStr);
}
