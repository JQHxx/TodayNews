package com.news.today.http.builder;


import com.news.today.http.annotation.ParamType;
import com.news.today.http.api.IApi;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yh on 2016/5/12.
 */
public class LfHttpRequest implements IRequest {

    protected IApi api;
    protected Object params;
    protected IParamBuilder paramBuilder;
    protected boolean enbleCache;
    protected Map<String, String> headers;
    protected String defaultParams;
    protected String url;

    @Override
    public String getUrl() {
        if (url == null) {
            if (api != null) {
                StringBuilder urlStrBuilder = new StringBuilder(api.getUrl());
                String defaultParams = getDefaultParams();
                if (defaultParams != null) {
                    urlStrBuilder.append(defaultParams);
                }
                url = urlStrBuilder.toString();
            }
        }
        return url;
    }


    @Override
    public Map<String, Object> getParams() {
        if (paramBuilder != null) {
            return paramBuilder.buildParams(api, params, api.getParamData());
        } else if (params instanceof Map) {
            return (Map<String, Object>) params;
        } else {
            return null;
        }

    }

    @Override
    public IApi getApi() {
        return api;
    }

    @Override
    public String getDefaultParams() {
        if (defaultParams == null) {
            defaultParams = api.getDefaultParams();
        }
        return defaultParams;
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public boolean enableCache() {
        return enbleCache;
    }

    @Override
    public String getCacheKey() {
        if (api != null) {
            return api.getCacheKey();
        }
        return null;
    }


    public void setApi(IApi api) {
        this.api = api;
    }

    public void setParams(Object params) {
        this.params = params;
    }

    public IParamBuilder getParamBuilder() {
        return paramBuilder;
    }

    public void setParamBuilder(IParamBuilder paramBuilder) {
        this.paramBuilder = paramBuilder;
    }

    public void setEnbleCache(boolean enbleCache) {
        this.enbleCache = enbleCache;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setDefaultParams(String defaultParams) {
        this.defaultParams = defaultParams;
    }

    public static abstract class Builder<T extends LfHttpRequest> implements IRequestBuilder {
        public T request;

        protected abstract T makeInstance();

        public Builder(IApi api) {
            request = makeInstance();
            request.setApi(api);
            request.setParamBuilder(api.getParamBuilder());
            request.setHeaders(api.getHeaders());

            int paramType = api.getParamType();
            if (paramType == ParamType.file) {
                request.setEnbleCache(false);
            } else {
                request.setEnbleCache(api.enableCache());
            }

        }

        @Override
        public IRequestBuilder setParams(Object params) {
            request.setParams(params);
            return null;
        }


        public Builder setParamBuilder(IParamBuilder paramBuilder) {
            request.setParamBuilder(paramBuilder);
            return this;
        }

        public Builder setEnbleCache(Boolean enbleCache) {
            request.setEnbleCache(enbleCache);
            return this;
        }

        @Override
        public IRequestBuilder setHeaders(Map<String, String> headers) {
            request.setHeaders(headers);
            return this;
        }

        @Override
        public IRequestBuilder setDefaultParams(String defaultParams) {
            request.setDefaultParams(defaultParams);
            return this;
        }

        public Builder addParams(Map<String, Object> value) {
            Map<String, Object> params = request.getParams();
            if (params == null) {
                params = new HashMap<>();
            }
            params.putAll(value);
            return this;
        }

        public Builder addParam(String key, Object value) {
            Map<String, Object> params = request.getParams();
            if (params == null) {
                params = new HashMap<>();
            }
            if (params instanceof Map) {
                try {
                    Map<String, Object> map = (Map) params;
                    map.put(key, value);
                } catch (Exception e) {
                }
            }
            request.setParams(params);
            return this;
        }

        @Override
        public IRequest build() {
            return request;
        }
    }
}
