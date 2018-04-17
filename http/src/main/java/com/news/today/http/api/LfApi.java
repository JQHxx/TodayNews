package com.news.today.http.api;

import android.support.annotation.NonNull;

import com.news.today.http.annotation.ContentType;
import com.news.today.http.annotation.ParamType;
import com.news.today.http.annotation.RequestMethod;
import com.news.today.http.parser.IResultParse;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by anson on 2018/4/17.
 */

public class LfApi implements IApi {
    protected Type resultType;
    @ParamType
    protected int paramType;
    @ContentType
    protected String contentType;
    private String url;
    // 区分不同的url
    protected String path;
    protected boolean enbleCache = false;
    protected Map<String, String> headers;
    protected Map<String,Object> params;
    @RequestMethod
    protected int requestMethod;
    protected IResultParse resultParse;
    // 主域名
    protected IHost host;

    @NonNull
    @Override
    public String getUrl() {
        if (url != null) {
            return url;
        }
        StringBuilder stringBuilder = new StringBuilder();
        String path = this.path;
        IHost host = getHost();
        if (host != null) {
            stringBuilder.append(host.getHost());
            if (path == null) {
                path = host.getDefaultPath();
            }
        }
        if (path != null) {
            stringBuilder.append(path);
        }
        url = stringBuilder.toString();
        return url;
    }

    @Override
    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    @Override
    public HashMap<String, Object> getParams() {
        return (HashMap<String, Object>) params;
    }

    @RequestMethod
    @Override
    public int getRequestMethod() {
        return requestMethod;
    }

    @ContentType
    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public Type getResultType() {
        return resultType;
    }

    @Override
    public int getParamType() {
        return paramType;
    }


    @Override
    public boolean enableCache() {
        return enbleCache;
    }


    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    @NonNull
    @Override
    public IResultParse getResultParse() {
        return resultParse;
    }


    @Override
    public String getCacheKey() {
        return null;
    }

    public IHost getHost() {
        return host;
    }

}
