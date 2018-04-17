package com.news.today.http.parser;


import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by anson on 2018/4/15.
 */
public class GsonParse implements IParse {
    private Gson gson;

    public GsonParse(Gson gson) {
        this.gson = gson;
    }

    @Override
    public String toJson(Object bean) {
        if (bean != null) {
            try {
                return gson.toJson(bean);
            } catch (Throwable throwable) {
               throwable.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public <T> T fromJson(String json, Class<T> clazz) {
        if (json != null) {
            try {
                return gson.fromJson(json, clazz);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public <T> T fromJson(String json, Type type) {
        if (json != null) {
            try {
                return gson.fromJson(json, type);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return null;
    }

}
