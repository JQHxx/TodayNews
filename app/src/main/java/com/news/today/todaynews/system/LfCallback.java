package com.news.today.todaynews.system;


import com.news.today.http.parser.IResult;
import com.news.today.task.lf.ITaskCallback;
import com.news.today.todaynews.system.control.IControl;
import com.news.today.todaynews.system.control.ILoadingControl;
import com.news.today.todaynews.system.control.IShowInfoControl;
import com.news.today.todaynews.system.control.IShowSuccessControl;

/**
 * Created by ye on 2017/4/5.
 */

public abstract class LfCallback<T> implements ITaskCallback<T>, IResultCallBack<T> {
    protected ILoadingControl iLoadingControl;
    protected IShowInfoControl showInfoControl;
    protected IShowSuccessControl showSuccessControl;

    public LfCallback() {
    }

    public LfCallback<T> setControl(IControl control) {
        if (control instanceof ILoadingControl) {
            this.iLoadingControl = (ILoadingControl) control;
        }
        if (control instanceof IShowInfoControl) {
            this.showInfoControl = (IShowInfoControl) control;
        }
        if (control instanceof IShowSuccessControl) {
            this.showSuccessControl = (IShowSuccessControl) control;
        }
        return this;
    }

    public LfCallback<T> setLoading(ILoadingControl iLoadingControl) {
        this.iLoadingControl = iLoadingControl;
        return this;
    }

    public LfCallback<T> setShowInfoControl(IShowInfoControl showInfoControl) {
        this.showInfoControl = showInfoControl;
        return this;
    }

    public LfCallback<T> setShowSuccessControl(IShowSuccessControl showSuccessControl) {
        this.showSuccessControl = showSuccessControl;
        return this;
    }

    public IShowInfoControl getShowInfoControl() {
        return showInfoControl;
    }


    @Override
    public void onBeforeCall() {
        if (iLoadingControl != null) {
            iLoadingControl.showLoading();
        }
    }

    @Override
    public void onAfterCall() {
        if (iLoadingControl != null) {
            iLoadingControl.dismissLoading();
        }
    }

    public void onException(Throwable t) {
    }

    public boolean showErrorScene(String scene, Object data) {
        if (showInfoControl != null) {
            return showInfoControl.showScene(scene, data);
        }
        return false;
    }


    protected void doSuccess(IResult<T> result) {
        if (showSuccessControl != null) {
            showSuccessControl.onSuccess(result);
        }
        onSuccess(result);
    }


    protected void doFailure(IResult result) {
        onFailure(result);
    }


    @Override
    public final void onComplete(T data) {
        if (data instanceof IResult) {
            IResult result = (IResult) data;
            if (result.success()) {
                doSuccess(result);
            } else {
                doFailure(result);
            }
        }
    }

}
