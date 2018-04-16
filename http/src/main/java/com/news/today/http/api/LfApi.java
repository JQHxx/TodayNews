package com.news.today.http.api;

import android.support.annotation.NonNull;

import com.news.today.http.annotation.ContentType;
import com.news.today.http.annotation.ParamType;
import com.news.today.http.annotation.ParmBuildWay;
import com.news.today.http.annotation.RequestMethod;
import com.news.today.http.builder.IParamBuilder;
import com.news.today.http.builder.IRequestBuilder;
import com.news.today.http.builder.LfHttpRequest;
import com.news.today.http.builder.LfParamBuilder;
import com.news.today.http.parser.IResultParse;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by ye on 2017/4/10.
 */

public class LfApi implements IApi {
    protected Type resultType;
    protected String defaultParams;
    @ParamType
    protected int paramType;
    @ContentType
    protected String contentType;
    protected IParamBuilder paramBuilder;
    private String url;
    protected String path;
    /**
     * 默认缓存
     */
    protected boolean enbleCache = false;
    protected Map<String, String> headers;
    @RequestMethod
    protected int requestMethod;
    protected IResultParse resultParse;

    protected IHost host;

    protected Object paramData;

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
    public String getDefaultParams() {
        return defaultParams;
    }

    @Override
    public boolean enableCache() {
        return enbleCache;
    }

    @Override
    public Object getParamData() {
        return paramData;
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

    @NonNull
    @Override
    public IParamBuilder getParamBuilder() {
        if (paramBuilder == null) {
            return LfParamBuilder.instance();
        }
        return paramBuilder;
    }

    @Override
    public String getCacheKey() {
        return null;
    }

    @Override
    public int parmBuildWay() {
        int requestMethod = getRequestMethod();
        switch (requestMethod) {
            case RequestMethod.Get:
                return ParmBuildWay.APPEND_URL;
            default:
                return ParmBuildWay.INTO_BODY;
        }
    }

    public IHost getHost() {
        return host;
    }

    public LfApi setHost(IHost host) {
        this.host = host;
        this.url = null;
        return this;
    }

    public LfApi setPath(String path) {
        this.path = path;
        this.url = null;
        return this;
    }

    public LfApi setUrlOrPath(String url) {
        if (isUrl(url)) {
            this.url = url;
        } else {
            this.path = url;
        }
        return this;
    }

    public LfApi setParamBuilder(IParamBuilder paramBuilder) {
        this.paramBuilder = paramBuilder;
        return this;
    }

    @Override
    public IRequestBuilder newRequestBuilder() {
        return new LfHttpRequest.Builder<LfHttpRequest>(this) {
            @Override
            public LfHttpRequest makeInstance() {
                return new LfHttpRequest();
            }
        }.setParamBuilder(LfParamBuilder.instance());
    }

    private static IHost defaultHost;

    public static void setDefaultHost(IHost defaultHost) {
        LfApi.defaultHost = defaultHost;
    }

    public static LfApi GET(String url) {
        LfApi api = new LfApi();
        api.requestMethod = RequestMethod.Get;
        api.paramType = ParamType.normal;
        api.setUrlOrPath(url);
        return api;
    }

    protected boolean isUrl(String url) {
        if (url != null) {
            if (url.startsWith("http://") || url.startsWith("https://")) {
                return true;
            }
        }
        return false;
    }
}
