package com.news.today.http.callback;


import com.news.today.http.builder.IRequest;
import com.news.today.http.exception.NetWorkException;

/**
 * http请求
 */
public abstract class ApiCall implements ICall {
    private int status = STATUS_NEW;
    private IRequest httpRequest;
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

    public ApiCall(IRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public IRequest getRequest() {
        return httpRequest;
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

    @Override
    public final void cancel() {
        //正在运行则取消任务
        if (status == STATUS_RUNNING) {
            try {
                doCancel();
            } catch (Exception e) {
                KernalLog.task.e(e);
            }
            status = STATUS_OVER;
        }
    }

    protected final void setReady() {
        //只有新建的任务才能成为准备好的任务
        if (status == STATUS_NEW) {
            status = STATUS_READY;
        } else {
            KernalLog.task.e("can't set ready to call,because call is not a new one");
        }
    }

    @Override
    public final IResponse execute() throws NetWorkException {
        IResponse response = null;
        if (status == STATUS_READY) {//可执行
            status = STATUS_RUNNING;
            response = doExecute();
            status = STATUS_OVER;
        } else {
            KernalLog.task.e("can't execute,because call is not a ready one");
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
