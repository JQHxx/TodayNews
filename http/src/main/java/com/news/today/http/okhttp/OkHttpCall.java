package com.news.today.http.okhttp;


import com.news.today.http.builder.IRequest;
import com.news.today.http.callback.ApiCall;
import com.news.today.http.callback.IResponse;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by yh on 2016/4/15.
 */
public class OkHttpCall extends ApiCall {
    public Call call;

    public OkHttpCall(IRequest request, Call call) {
        super(request);
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
            KernalLog.network.e(e);
            throw new NetworkNotAvailableException();
        }
    }

}
