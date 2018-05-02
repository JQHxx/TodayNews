package com.news.today.http.cookie;

/**
 * Created by anson on 2018/4/8.
 */

public interface ICookieStoreInterceptor {
    /**
     * 是否支持本地化cookie
     *
     * @param url
     * @return
     */
    boolean supportCookie(String url);
}
