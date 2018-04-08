package com.news.today.task.helper;


import com.news.today.task.AsyncTaskInstance;
import com.news.today.task.TaskScheduler;

import com.news.today.task.lf.ITaskBackground;
import com.news.today.task.lf.ITaskCallback;

import com.news.today.task.utils.TaskUtil;

/**
 * Created by anson on 2018/4/6.
 */
public class TaskHelper {
    /**
     * 任务调度器
     */
    private static TaskScheduler taskScheduler = TaskScheduler.instance();

    public static void cancelGroup(String groupName) {
        taskScheduler.cancelGroup(groupName);
    }

    public static void onPause(String groupName) {
        taskScheduler.onPause(groupName);
    }

    public static void onResume(String groupName) {
        taskScheduler.onResume(groupName);
    }

    public static TaskScheduler getTaskScheduler() {
        return taskScheduler;
    }

    public static void cancelTask(String taskName, String groupName) {
        taskScheduler.cancelTask(groupName, taskName);
    }

    public static void cancelTask(AsyncTaskInstance taskInstance) {
        taskScheduler.stopTaskOuter(taskInstance);
    }

    public static AsyncTaskInstance submitTask(final String taskName, final String groupName,
                                               final ITaskBackground task, final ITaskCallback callback) {
        AsyncTaskInstance asyncTask;
        asyncTask = buildTask(taskName, groupName, task, callback);
        asyncTask.serialExecute(false);
        taskScheduler.submit(asyncTask);
//        IShowInfoControl showInfoControl = null;
//        if (callback != null&&callback instanceof LfCallback) {
//            LfCallback lfCallback= (LfCallback) callback;
//            showInfoControl = lfCallback.getShowInfoControl();
//        }
//        if (showInfoControl != null) {
//            ITaskAction taskAction = new ITaskAction() {
//                private AsyncTaskInstance taskInstance;
//
//                @Override
//                public AsyncTaskInstance doRetry() {
//                    taskInstance = buildTask(taskName, groupName, task, callback);
//                    taskInstance.serialExecute(false);
//                    taskScheduler.submit(taskInstance);
//                    return taskInstance;
//                }
//
//                @Override
//                public void doCancel() {
//                    if (taskInstance != null) {
//                        taskScheduler.stopTaskOuter(taskInstance);
//                    } else {
//                        taskScheduler.cancelTask(taskName, groupName);
//                    }
//                }
//            };
//            showInfoControl.setTaskAction(taskAction);
//            asyncTask = taskAction.doRetry();
//        } else {
//            asyncTask = buildTask(taskName, groupName, task, callback);
//            asyncTask.serialExecute(false);
//            taskScheduler.submit(asyncTask);
//        }
        return asyncTask;
    }


    private static <T> AsyncTaskInstance<T> buildTask(String taskName, String groupName, ITaskBackground<T> task, ITaskCallback<T> callback) {
        AsyncTaskInstance asyncTask = AsyncTaskInstance.build(task, callback)
                .taskName(taskName)
                .groupName(groupName);
        return asyncTask;
    }

    /**
     * task 组件的总入口
     *
     * @param groupName
     * @param task
     * @param callback
     * @param <T>
     * @return
     */
    public static final <T> AsyncTaskInstance<T> submitTask(String groupName, ITaskBackground task, final ITaskCallback callback) {
        return submitTask(getDefaultTaskName(), groupName, task, callback);
    }

    private static String getDefaultTaskName() {
        return TaskUtil.getTaskNameFromTrace(Thread.currentThread().getStackTrace(), 4);
    }

}
