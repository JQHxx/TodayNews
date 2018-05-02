package com.news.today.http.util;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.GsonBuilder;
import com.news.today.http.parser.GsonParse;
import com.news.today.http.parser.IParse;

import java.lang.reflect.Type;

/**
 * Created by anson on 2018/4/15.
 */
public class JsonHelper {
    private static IParse parse;
    private static IParse getParse() {
        if (parse == null) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.serializeNulls();
            parse = new GsonParse(gsonBuilder.create());
        }
        return parse;
    }

    public static String toJSONString(@NonNull Object bean) {
        return getParse().toJson(bean);
    }

    @Nullable
    public static <T> T fromJson(@NonNull String json, @NonNull Type type) {
        String typeString = Strings.EMPTY;
        if (type instanceof Class) {
            typeString = ((Class) type).getSimpleName();
        }
        if (isString(typeString)) {
            try {
                return (T) json;
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                return null;
            }
        }
        return getParse().fromJson(json, type);
    }

    private static boolean isString(String typeString) {
        return typeString.startsWith("String");
    }

    /**
     * 判断不是json
     * 目前只是简单判断非json逻辑
     *
     * @param str
     * @return
     */
    public static boolean isNotJson(String str) {
        if (str == null) {
            return true;
        }
        if (!str.startsWith("{") || !str.endsWith("}")) {
            return true;
        }
        //还可以增加其他不是json的判断逻辑
        return false;
    }


}
