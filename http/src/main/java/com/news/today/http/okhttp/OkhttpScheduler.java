package com.news.today.http.okhttp;


import com.news.today.http.HttpScheduler;
import com.news.today.http.annotation.ParamType;
import com.news.today.http.annotation.RequestMethod;
import com.news.today.http.api.IApi;
import com.news.today.http.callback.ICall;
import com.news.today.http.callback.IResponse;
import com.news.today.http.exception.NetWorkException;
import com.news.today.http.util.Chars;
import com.news.today.http.util.JsonHelper;
import com.news.today.http.util.Strings;

import java.io.File;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by anson on 2018/4/15.
 */
public class OkhttpScheduler extends HttpScheduler {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient client;

    public OkhttpScheduler(OkHttpClient client) {
        this.client = client;
    }

    public OkHttpClient getClient() {
        if (client == null) {
            client = new OkHttpClient();
        }
        return client;
    }

    @Override
    public ICall newCall(IApi api) {
        Map<String, Object> params = api.getParams();
        int method = api.getRequestMethod();
        StringBuilder urlStrBuilder = new StringBuilder(api.getUrl());
        Request.Builder requestBuilder = new Request.Builder();
        switch (method) {
            case RequestMethod.Get: {
                if (params != null) {
                    if (urlStrBuilder.lastIndexOf(Strings.QMARK) == -1) {
                        urlStrBuilder.append(Chars.QMARK);
                    } else {
                        urlStrBuilder.append(Chars.AND);
                    }
                    Iterator<Map.Entry<String, Object>> it = params.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry<String, Object> entry = it.next();
                        urlStrBuilder.append(entry.getKey())
                                .append(Chars.EQUAL)
                                .append(entry.getValue());
                        if (it.hasNext()) {
                            urlStrBuilder.append(Chars.AND);
                        }
                    }
                }
                requestBuilder.get();
                break;
            }

            case RequestMethod.Post: {
                int paramType = api.getParamType();
                switch (paramType) {
                    case ParamType.normal://普通请求方式
                    {
                        FormBody.Builder formBodyBuilder = new FormBody.Builder();
                        if (params != null) {
                            for (Map.Entry<String, Object> pair : params.entrySet()) {
                                Object value = pair.getValue();
                                formBodyBuilder.add(pair.getKey(), value == null ? Strings.EMPTY :
                                        value.toString());
                            }
                        }
                        RequestBody formBody = formBodyBuilder.build();
                        requestBuilder.post(formBody);
                    }
                    break;
                    case ParamType.file://文件上传请求
                    {
                        MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder();
                        if (params != null) {
                            for (Map.Entry<String, Object> pair : params.entrySet()) {
                                Object value = pair.getValue();
                                if (value instanceof File) {
                                    File file = (File) value;
                                    MediaType PNG = MediaType.parse("image/png");
                                    requestBodyBuilder.addFormDataPart(pair.getKey(), file.getName(),
                                            RequestBody.create(PNG, (File) value));
                                } else if (value instanceof byte[]) {
                                    MediaType stream = MediaType.parse("application/octet-stream; charset=utf-8");
                                    requestBodyBuilder.addFormDataPart(pair.getKey(), pair.getKey(),
                                            RequestBody.create(stream, (byte[]) value));
                                } else {
                                    requestBodyBuilder.addFormDataPart(pair.getKey(), value == null ? Strings.EMPTY :
                                            value.toString());
                                }
                            }
                        }
                        requestBuilder.post(requestBodyBuilder.build());
                    }
                    break;
                    case ParamType.json:
                    {
                        String json = Strings.EMPTY;
                        if (params != null) {
                            json = JsonHelper.toJSONString(params);
                        }
                        RequestBody body = RequestBody.create(JSON, json);
                        requestBuilder.post(body);
                    }
                    break;
                }
                break;
            }

        }
        Map<String, String> headers = api.getHeaders();
        if (headers != null) {
            requestBuilder.headers(Headers.of(headers));
        }

        Request request = requestBuilder
                .url(urlStrBuilder.toString())
                .build();
        Call call = getClient().newCall(request);
        OkHttpCall okHttpCall = new OkHttpCall(api, call);
        return okHttpCall;
    }

    @Override
    public IResponse doExecute(ICall icall) throws NetWorkException {
        return icall.execute();
    }
}
