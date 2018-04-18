package com.news.today.http.parser;

import java.lang.reflect.Type;

/**
 * Created by anson on 2018/4/8.
 */
public interface IParse {
    String toJson(Object bean);

    <T> T fromJson(String json, Class<T> clazz);

    <T> T fromJson(String json, Type type);
}
