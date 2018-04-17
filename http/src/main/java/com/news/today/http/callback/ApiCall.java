package com.news.today.http.callback;


import com.news.today.http.api.IApi;
import com.news.today.http.exception.NetWorkException;

/**
 * Created by anson on 2018/4/15.
 */
public abstract class ApiCall implements ICall {
    private int status = STATUS_NEW;
    private IApi api;
    private long beforeTime;
    private long befroreParseTime;
    private String url;

    public void setBeforeTime(long beforeTime) {
        this.beforeTime = beforeTime;
    }

    public void setBefroreParseTime(long befroreParseTime) {
        this.befroreParseTime = befroreParseTime;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getBeforeTime() {
        return beforeTime;
    }

    public long getBefroreParseTime() {
        return befroreParseTime;
    }

    public ApiCall(IApi api) {
        this.api = api;
    }


    @Override
    public IApi getRequest() {
        return api;
    }

    @Override
    public String getUrl() {
        return url;
    }

    public boolean isRunning() {
        if (status == STATUS_RUNNING) {
            return true;
        }
        return false;
    }


    protected final void setReady() {
        //只有新建的任务才能成为准备好的任务
        if (status == STATUS_NEW) {
            status = STATUS_READY;
        }
    }

    @Override
    public final IResponse execute() throws NetWorkException {
        IResponse response = null;
        if (status == STATUS_READY) {//可执行
            status = STATUS_RUNNING;
            response = doExecute();
            status = STATUS_OVER;
        }
        return response;
    }

    /**
     * 实现取消动作
     */
    protected abstract void doCancel();

    /**
     * 取得结果
     *
     * @return
     */
    protected abstract IResponse doExecute() throws NetWorkException;


}
