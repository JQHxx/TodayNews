package com.news.today.http.cookie;

/**
 * Created by ye on 2017/4/19.
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
