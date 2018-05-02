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
    private static Response mRes;

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
            Response response = call.execute();//同步
            /*call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                      mRes = response;

                }
            });*/

            return new OkHttpResponse(response);

        } catch (Throwable e) {
           e.printStackTrace();
            // CODEREVIEW:  代码审核： 自定义异常
            throw new NetworkNotAvailableException();
        }
    }

}
