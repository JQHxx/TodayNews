package com.news.today.http.okhttp;

import android.text.TextUtils;

import com.news.today.http.cookie.ICookieIterator;
import com.news.today.http.cookie.ICookieSetListener;
import com.news.today.http.cookie.ICookieStoreInterceptor;
import com.news.today.http.cookie.ILocalCookieManager;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by yh on 2016/4/21.
 */
public class OkhttpCookieManager implements CookieJar, ILocalCookieManager {
    private IOkhttpCookieStore cookieStore;
    private List<Cookie> empty = new ArrayList<>();
    private ICookieSetListener cookieSetListener;
    private ICookieStoreInterceptor cookieStoreInterceptor;

    public OkhttpCookieManager(IOkhttpCookieStore cookieStore) {
        this.cookieStore = cookieStore;
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (url == null || cookies == null) {
            return;
        }
        String urlStr = url.toString();
        if (TextUtils.isEmpty(urlStr)) {
            return;
        }
        boolean isSeted = false;
        for (Cookie cookie : cookies) {
            if (cookie == null) {
                continue;
            }
            if (cookieStoreInterceptor != null && !cookieStoreInterceptor.supportCookie(urlStr)) {
                continue;
            }
            cookieStore.add(url, cookie);
            isSeted = true;
        }
        if (isSeted && cookieSetListener != null) {
            cookieSetListener.setCookie(urlStr);
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        if (url == null) {
            return empty;
        }
        List<Cookie> cookies = cookieStore.get(url);
        if (cookies == null) {
            return empty;
        }
        return cookies;
    }


    @Override
    public void setCookieSetListener(ICookieSetListener cookieSetListener) {
        this.cookieSetListener = cookieSetListener;
    }

    public void setCookieStoreInterceptor(ICookieStoreInterceptor cookieStoreInterceptor) {
        this.cookieStoreInterceptor = cookieStoreInterceptor;
    }

    @Override
    public boolean iteratorCookies(String url, ICookieIterator iterator) {
        return cookieStore.iteratorCookies(url, iterator);
    }

    @Override
    public void setCookie(String urlStr, String[] cookieArr) {
        HttpUrl httpUrl = null;
        try {
            httpUrl = HttpUrl.parse(urlStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (httpUrl == null) {
            return;
        }
        cookieStore.remove(httpUrl);//全部使用h5的cookie
        if (cookieArr != null) {
            for (String cookieStr : cookieArr) {
                Cookie cookie = null;
                try {
                    cookie = Cookie.parse(httpUrl, cookieStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (cookie != null) {
                    cookieStore.add(httpUrl, cookie);
                }
            }
        }
    }

    @Override
    public void removeAll() {
        cookieStore.removeAll();
    }
}