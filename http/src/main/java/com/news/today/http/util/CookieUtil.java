package com.news.today.http.util;

import okhttp3.HttpUrl;

/**
 * Created by anson on 2018/4/8.
 */

public class CookieUtil {
    public static String getUrl(HttpUrl url) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url.host());
        return stringBuilder.toString();
    }

    public static String getDomain(HttpUrl url) {
        String host = url.host();
        return getDomain(host);
    }

    public static String getDomain(String host) {
        int indexOfPoint = host.indexOf(".");
        int count = host.length();
        String domain;
        if (indexOfPoint > 0 && indexOfPoint < count) {
            domain = host.substring(host.indexOf(".") + 1);
        } else {
            domain = host;
        }
        return domain;
    }
}
