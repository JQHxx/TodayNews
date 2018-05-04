package com.news.today.http.okhttp;


import com.news.today.http.api.IApi;
import com.news.today.http.callback.ApiCall;
import com.news.today.http.callback.IResponse;
import com.news.today.http.exception.NetworkNotAvailableException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by anson on 2018/4/15.
 */
public class OkHttpCall extends ApiCall {
    public Call call;

    public OkHttpCall(IApi api, Call call) {
        super(api);
        super.setUrl(api.getUrl());
        this.call = call;
        setReady();
    }

    @Override
    protected void doCancel() {
        call.cancel();
    }

    @Override
    protected IResponse doExecute() {
        try {
            Response response = call.execute();
            return new OkHttpResponse(response);
        } catch (Throwable e) {
           e.printStackTrace();
            throw new NetworkNotAvailableException();
        }
    }

}
