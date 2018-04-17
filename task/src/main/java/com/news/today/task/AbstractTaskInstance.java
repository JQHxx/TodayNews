package com.news.today.task;


import android.support.annotation.NonNull;
import android.util.Log;

import com.news.today.task.helper.TheadLocalHelper;
import com.news.today.task.lf.IGroup;
import com.news.today.task.lf.ITaskBackground;
import com.news.today.task.lf.ITaskCallback;
import com.news.today.task.utils.ITaskConstant;
import com.news.today.task.utils.ThreadUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import static com.news.today.task.lf.ITaskInstance.STATUS_NEW;
/**
 * Created by anson on 2018/4/6.
 */
public abstract class AbstractTaskInstance<Result> extends FutureTask<Result> implements ITaskConstant ,Comparable<AbstractTaskInstance>,IGroup{
    private String TAG = AbstractTaskInstance.class.getSimpleName();
    protected String taskName = DEFAULT_TASK_NAME;
    protected String groupName = DEFAULT_GROUP_NAME;
    protected ITaskCallback<Result> callback;
    protected ITaskBackground<Result> callable;
    protected boolean serialExecute;
    protected int dualPolicy = DISCARD_NEW;
    public int priority = PRIOR_NORMAL;
    public int status = STATUS_NEW;
    /**
     * 任务提交的时间
     */
    private long submitTime;
    /**
     * 开始执行时间
     */
    private long beginExecute;
    /**
     * 执行结束时间
     */
    private long endExecute;

    public AbstractTaskInstance(final ITaskBackground<Result> callable, final ITaskCallback<Result> callback) {
        super(new Callable<Result>() {
            @Override
            public Result call() throws Exception {
                if (callback != null) {
                    ThreadUtil.postUi(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                callback.onBeforeCall();
                            } catch (Throwable throwable) {
                            }
                        }
                    });
                }
                return callable.onBackground();
            }
        });
        this.callable = callable;
        this.callback = callback;
    }


    public ITaskBackground<Result> getCallable() {
        return callable;
    }

    public ITaskCallback<Result> getCallback() {
        return callback;
    }

    private void onAfterCall() {
        try {
            callback.onAfterCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onComplete() {
        try {
            Result result = get();
            if (result != null) {
                callback.onComplete(result);
            } else {
                Log.d(TAG, String.format("task(%s) result is null,callback.onComplete will not call", taskName()));
            }
        } catch (Exception throwable) {
            throwable.printStackTrace();
        }
    }


    private void onCancelled() {
        Log.d(TAG, String.format("task(%s) cancel", taskName()));
        try {
            callback.onCancelled();
        } catch (Exception throwable) {
            throwable.printStackTrace();
        }
    }

    public void onSubmit() {
        submitTime = System.currentTimeMillis();
    }

    @Override
    public void run() {
        Log.d(TAG, String.format("task(%s) execute start", taskName()));
        TheadLocalHelper.setTaskInfo(groupName(), taskName());
        beginExecute = System.currentTimeMillis();
        super.run();
    }

    /**
     * 任务执行结束时将会调用此方法
     * 不论是正常结束，异常结束，还是被取消此方法都将被调用
     */
    @Override
    protected void done() {
        endExecute = System.currentTimeMillis();
        if (callback != null) {
            ThreadUtil.postUi(new Runnable() {
                @Override
                public void run() {
                    onAfterCall();
                    if (isCancelled()) {
                        onCancelled();
                    } else {
                        onComplete();
                    }
                }
            });
        }
        long waitTime = beginExecute - submitTime;
        long runTime = endExecute - beginExecute;
        long totalTime = endExecute - submitTime;
        Log.d(TAG, String.format("task(%s)execute end.waitTime=%d,runTime=%d,totalTime=%d", taskName, waitTime, runTime, totalTime));
    }


    @Override
    protected void setException(final Throwable t) {
        super.setException(t);
        t.printStackTrace();
        this.endExecute = System.currentTimeMillis();//为了更好的打印日志，此时任务还未结束此字段为0
        if (!ThreadUtil.isUiThread()) {
            ThreadUtil.postUi(new Runnable() {
                @Override
                public void run() {
                    callException(t);
                }
            });
        } else {
            callException(t);
        }
    }

    private void callException(Throwable cause) {
        try {
            callback.onException(cause);
        } catch (Exception throwable1) {
            throwable1.printStackTrace();
        }
    }



    public int getStatus() {
        return status;
    }


    public void setStatus(int status) {
        this.status = status;
    }


    public int getPriority() {
        return priority;
    }


    public String taskName() {
        return taskName;
    }


    public String groupName() {
        return groupName;
    }


    public boolean serialExecute() {
        return serialExecute;
    }


    public int dualPolicy() {
        return dualPolicy;
    }

    //复写equals 方法，来判断到底满足什么条件达到相等的目的
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractTaskInstance that = (AbstractTaskInstance) o;

        return taskName.equals(that.taskName) && groupName.equals(that.groupName);
    }

    @Override
    public int hashCode() {
        int result = taskName.hashCode();
        result = 31 * result + groupName.hashCode();
        return result;
    }

    //用于线程池中 比较两个任务的优先级
    @Override
    public int compareTo(@NonNull AbstractTaskInstance o) {
        return o.getPriority() - priority;
    }

    public void retry() {
        TaskScheduler.instance().submit(this);
    }
}
