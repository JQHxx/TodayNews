package com.news.today.http.okhttp;


import com.news.today.http.callback.IResponse;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Response;

/**
 * Created by anson on 2018/4/15.
 */
public class OkHttpResponse implements IResponse {
    private Response response;
    private String body;

    public OkHttpResponse(Response response) {
        this.response = response;
    }

    @Override
    public String getHeader(String key, String defaultValue) {
        if (response == null) {
            return null;
        }
        return response.header(key, defaultValue);
    }

    @Override
    public String getBody() {
        if (body == null) {
            try {
                body = response.body().string();
            } catch (Exception e) {
               e.printStackTrace();
            }
        }
        return body;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        if (response == null) {
            return null;
        }
        return response.body().byteStream();
    }

    @Override
    public long getTotalLength() {
        if (response == null) {
            return -1;
        }
        return response.body().contentLength();
    }


    public Response getResponse() {
        return response;
    }

    @Override
    public boolean isSuccess() {
        if (response == null) {
            return false;
        }
        return response.isSuccessful();
    }

    @Override
    public void destroy() {
        body = null;
        response = null;
    }
}
