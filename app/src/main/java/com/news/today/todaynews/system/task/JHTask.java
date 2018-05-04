package com.news.today.todaynews.system.task;

import com.news.today.http.parser.IResult;
import com.news.today.http.parser.Result;
import com.news.today.http.parser.ResultCodes;
import com.news.today.task.AbsTask;

/**
 * Created by anson on 2018/4/8.
 */

public abstract class JHTask<T> extends AbsTask<T,IResult<T>> implements IResultCallBack<T> {

    @Override
    public final void onComplete(IResult<T> data) {
        //根据不同的接口格式  去处理业务逻辑 包括错误码
        if (data.success()) {
            onSuccess(data);
        } else {
            onFailure(data);
        }
    }

    @Override
    public void onCancelled() {
        onFailure(Result.failed(ResultCodes.CODE_TASK_CANCELED));
    }

    @Override
    public boolean onFailure(IResult result) {
        if (result != null) {
            String code = result.code();
            switch (code) {
                case ResultCodes.CODE_TASK_CANCELED:
                    break;
                case ResultCodes.CODE_TASK_EXCEPTION:
                    //界面异常
                    break;
            }
        }
        return false;
    }

    @Override
    public void onException(Throwable t) {
        onFailure(Result.failed(ResultCodes.CODE_TASK_CANCELED));
    }

    @Override
    public void onBeforeCall() {

    }

    @Override
    public void onAfterCall() {

    }

}
