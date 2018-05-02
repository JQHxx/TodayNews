package com.news.today.http.callback;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * Created by anson on 2018/4/15.
 */

public class CacheResponse implements IResponse, Serializable {
    String json;

    public CacheResponse(String json) {
        this.json = json;
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public String getHeader(String key, String defaultValue) {
        return null;
    }

    @Override
    public String getBody() {
        return json;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public long getTotalLength() {
        if (json != null) {
            return json.length();
        }
        return 0;
    }

    @Override
    public void destroy() {

    }
}
