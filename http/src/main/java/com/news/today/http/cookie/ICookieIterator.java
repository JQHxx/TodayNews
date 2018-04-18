package com.news.today.http.cookie;

/**
 * Created by anson on 2018/4/8.
 */

public interface ICookieIterator {
    boolean iterator(String urlHost, String cookieStr);
}
