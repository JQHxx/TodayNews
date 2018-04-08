package com.news.today.task.lf;

/**
 * Created by anson on 2018/4/6.
 */
public interface ITaskCallback<T> {

    void onBeforeCall();

    void onAfterCall();

    void onComplete(T data);

    void onException(Throwable t);

    void onCancelled();

}
