package com.news.today.http.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kaike.la.framework.http.ResultCodes;
import com.kaike.la.framework.http.api.KklApi;
import com.kaike.la.kernal.http.IApi;
import com.kaike.la.kernal.lf.http.IPage;
import com.kaike.la.kernal.util.lang.Strings;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import static com.kaike.la.framework.http.resultparse.GsonUtil.getJsonObject;

/**
 * Created by yh on 2016/4/20.
 */
public class NgResultParse extends AbstractResultParse {

    private static NgResultParse instance = new NgResultParse();

    private NgResultParse() {
    }

    public static NgResultParse getInstance() {
        return instance;
    }


    @Override
    public Result parseResultCommon(String json, Type type, IApi iApi) {
        JsonObject jsonObject = getJsonObject(json);
        if (jsonObject == null) {
            //解析不出来或数据为空表示网络异常
            return Result.fail(ResultCodes.CODE_NETWORKERROR);
        }
        boolean serResult = GsonUtil.getBoolean(jsonObject, "serResult", false);
        String remoteCode = GsonUtil.getString(jsonObject, "code", Strings.EMPTY);
        String message = GsonUtil.getString(jsonObject, "message", Strings.EMPTY);
        if (!serResult) {
            //网关异常
            return Result.fail(ResultCodes.CODE_NETGATE_ERROR, remoteCode);
        }
        boolean success = GsonUtil.getBoolean(jsonObject, "success", false);
        if (!success) {
            return Result.instance(success, ResultCodes.CODE_SERVER_ERROR, message, remoteCode);
        }
        //是否需要校验data字段
        boolean needCheckData = false;
        if (iApi instanceof KklApi) {
            KklApi api = (KklApi) iApi;
            needCheckData = api.isNeedCheckData();
        }
        Object body = null;
        JsonObject bodyObject = GsonUtil.getJsonObject(jsonObject, "data");
        if (bodyObject != null && bodyObject.isJsonObject()) {
            Class cls = null;
            if (type != null && type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type rawType = parameterizedType.getRawType();
                if (rawType != null && rawType instanceof Class) {
                    cls = (Class) rawType;
                }
            }

            if (cls != null) {
                if (IPage.class.isAssignableFrom(cls)) {//分页的对象
                    body = GsonUtil.fromJson(bodyObject, type);
                } else if (List.class.isAssignableFrom(cls)) {//列表的对象
                    JsonArray jsonArray = GsonUtil.getJsonArray(bodyObject, "resultList");
                    body = GsonUtil.fromJson(jsonArray, type);
                }
            }
            if (body == null) {
                JsonElement resultObject = GsonUtil.getJsonElement(bodyObject, "resultObject");
                if (resultObject != null) {
                    if (type != null) {
                        body = GsonUtil.fromJson(resultObject, type);
                    } else {
                        body = resultObject;
                    }
                }
            }
        }
        if (body == null && needCheckData) {
            return Result.fail(ResultCodes.CODE_ERROR_DATA_MISS);
        }
        return Result.instance(success, ResultCodes.CODE_SUCCESS, message, body);

    }
}