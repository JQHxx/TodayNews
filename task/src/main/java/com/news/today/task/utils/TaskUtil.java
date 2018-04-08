package com.news.today.task.utils;

/**
 * Created by anson on 2018/4/7.
 */

public class TaskUtil {
    public static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    /**
     * 从trace中提取任务名
     *
     * @param traceElements
     * @param place
     * @return
     */
    public static String getTaskNameFromTrace(StackTraceElement[] traceElements, int place) {
        String taskName;
        if (traceElements != null && traceElements.length > place) {
            StackTraceElement traceElement = traceElements[place];
            taskName = traceElement.getClassName() + ":" + traceElement.getLineNumber();
        } else {
            taskName = "task-" + System.currentTimeMillis();
        }
        return taskName;
    }
}
