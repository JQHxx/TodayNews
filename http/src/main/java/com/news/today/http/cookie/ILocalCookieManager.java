package com.news.today.http.cookie;

/**
 * Created by anson on 2018/4/8.
 */

public interface ILocalCookieManager {
    void setCookieSetListener(ICookieSetListener cookieSetListener);

    boolean iteratorCookies(String url, ICookieIterator iterator);

    void setCookie(String urlStr, String[] cookieArr);

    void removeAll();
}
