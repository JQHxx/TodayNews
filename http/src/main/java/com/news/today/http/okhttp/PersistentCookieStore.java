package com.news.today.http.okhttp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.news.today.http.cookie.ICookieIterator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * Created by yh on 2016/4/21.
 */
public class PersistentCookieStore implements IOkhttpCookieStore {

    protected final Map<String, ConcurrentHashMap<String, Cookie>> cookies;
    private final SharedPreferences cookiePrefs;

    public PersistentCookieStore(Context context) {
        this(context, "Cookies_Prefs");
    }

    public PersistentCookieStore(Context context, String spName) {
        this(context.getSharedPreferences(spName, 0));
    }

    public PersistentCookieStore(@NonNull SharedPreferences sharedPreferences) {
        cookiePrefs = sharedPreferences;
        cookies = new HashMap<>();

        //将持久化的cookies缓存到内存中 即map cookies
        Map<String, ?> prefsMap = cookiePrefs.getAll();
        for (Map.Entry<String, ?> entry : prefsMap.entrySet()) {
            String[] cookieNames = TextUtils.split((String) entry.getValue(), ",");
            for (String name : cookieNames) {
                String encodedCookie = cookiePrefs.getString(name, null);
                if (encodedCookie != null) {
                    Cookie decodedCookie = EncodeUtil.decodeCookie(encodedCookie);
                    if (decodedCookie != null) {
                        if (!cookies.containsKey(entry.getKey())) {
                            cookies.put(entry.getKey(), new ConcurrentHashMap<String, Cookie>());
                        }
                        cookies.get(entry.getKey()).put(name, decodedCookie);
                    }
                }
            }
        }
    }

    protected String getCookieToken(Cookie cookie) {
        return cookie.name() + "@" + cookie.domain();
    }

    public void add(HttpUrl url, Cookie cookie) {
        String name = getCookieToken(cookie);
        boolean hostOnly = cookie.hostOnly();
        String urlStr;
        if (hostOnly) {
            urlStr = urlToString(url);
        } else {
            urlStr = getDomain(url);
        }
        //将cookies缓存到内存中 如果缓存过期 就重置此cookie
        if (!cookie.persistent()) {
            if (!cookies.containsKey(urlStr)) {
                cookies.put(urlStr, new ConcurrentHashMap<String, Cookie>());
            }
            cookies.get(urlStr).put(name, cookie);
        } else {
            if (cookies.containsKey(urlStr)) {
                cookies.get(urlStr).remove(name);
            }
        }

        //讲cookies持久化到本地
        SharedPreferences.Editor prefsWriter = cookiePrefs.edit();
        ConcurrentHashMap<String, Cookie> map = cookies.get(urlStr);
        if (map != null) {
            prefsWriter.putString(urlStr, TextUtils.join(",", map.keySet()));
            prefsWriter.putString(name, EncodeUtil.encodeCookie(cookie));
            prefsWriter.apply();
        }
    }

    public List<Cookie> get(HttpUrl url) {
        ArrayList<Cookie> ret = new ArrayList<>();
        String domain = getDomain(url);
        if (cookies.containsKey(domain)) {
            ret.addAll(cookies.get(domain).values());
        }
        String urlStr = urlToString(url);
        if (cookies.containsKey(urlStr)) {
            ret.addAll(cookies.get(urlStr).values());
        }
        return ret;
    }

    public boolean removeAll() {
        SharedPreferences.Editor prefsWriter = cookiePrefs.edit();
        prefsWriter.clear();
        prefsWriter.apply();
        cookies.clear();
        return true;
    }

    @Override
    public void remove(HttpUrl url) {
        String urlStr = urlToString(url);
        cookies.remove(urlStr);
        SharedPreferences.Editor prefsWriter = cookiePrefs.edit();
        if (cookiePrefs.contains(urlStr)) {
            String cookieNameStr = cookiePrefs.getString(urlStr, null);
            String[] cookieNameArr = TextUtils.split(cookieNameStr, ",");
            for (String cookieName : cookieNameArr) {
                prefsWriter.remove(cookieName);
            }
        }
        prefsWriter.remove(urlStr);
        prefsWriter.apply();
    }

    public boolean remove(HttpUrl url, Cookie cookie) {
        String name = getCookieToken(cookie);
        String urlStr;
        if (cookie.hostOnly()) {
            urlStr = urlToString(url);
        } else {
            urlStr = getDomain(url);
        }
        if (cookies.containsKey(urlStr) && cookies.get(urlStr).containsKey(name)) {
            cookies.get(urlStr).remove(name);

            SharedPreferences.Editor prefsWriter = cookiePrefs.edit();
            if (cookiePrefs.contains(name)) {
                prefsWriter.remove(name);
            }
            prefsWriter.putString(urlStr, TextUtils.join(",", cookies.get(urlStr).keySet()));
            prefsWriter.apply();

            return true;
        } else {
            return false;
        }
    }

    public String urlToString(HttpUrl url) {
        String urlStr = CookieUtil.getUrl(url);
        return urlStr;
    }

    public String getDomain(HttpUrl url) {
        String urlStr = CookieUtil.getDomain(url);
        return urlStr;
    }

    @Override
    public boolean iteratorCookies(String url, ICookieIterator cookieIterator) {
        Iterator<Map.Entry<String, ConcurrentHashMap<String, Cookie>>> it = cookies.entrySet().iterator();
        boolean result = false;
        while (it.hasNext()) {
            Map.Entry<String, ConcurrentHashMap<String, Cookie>> entry = it.next();
            String urlStr = entry.getKey();
            ConcurrentHashMap<String, Cookie> cookieMap = entry.getValue();
            Set<Map.Entry<String, Cookie>> entrySet = cookieMap.entrySet();
            for (Map.Entry<String, Cookie> mapEntry : entrySet) {
                Cookie cookie = mapEntry.getValue();
                cookieIterator.iterator(urlStr, cookie.toString());
                result = true;
            }
        }
        return result;
    }

}
