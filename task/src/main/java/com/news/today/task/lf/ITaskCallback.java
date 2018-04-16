package com.news.today.task.lf;

/**
 * Created by anson on 2018/4/6.
 */
public interface ITaskCallback<Result> {

    void onBeforeCall();

    void onAfterCall();

    void onComplete(Result data);

    void onException(Throwable t);

    void onCancelled();

}
