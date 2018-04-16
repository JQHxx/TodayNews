package com.news.today.http.cookie;

/**
 * Created by ye on 2017/4/19.
 */

public interface ILocalCookieManager {
    void setCookieSetListener(ICookieSetListener cookieSetListener);

    boolean iteratorCookies(String url, ICookieIterator iterator);

    void setCookie(String urlStr, String[] cookieArr);

    void removeAll();
}
