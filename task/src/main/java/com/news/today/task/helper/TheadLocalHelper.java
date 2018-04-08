package com.news.today.task.helper;

/**
 * Created by anson on 2018/4/6.
 */
public class TheadLocalHelper {
    private static ThreadLocal<TaskInfo> threadLocal = new ThreadLocal<>();

    public static TaskInfo getTaskInfo() {
        return threadLocal.get();
    }

    public static void setTaskInfo(String groupName, String taskName) {
        threadLocal.set(new TaskInfo(groupName, taskName));
    }
    public static void remove() {
        threadLocal.remove();
    }
    public static class TaskInfo {
        public String taskName;
        public String groupName;

        public TaskInfo(String groupName, String taskName) {
            this.taskName = taskName;
            this.groupName = groupName;
        }
    }
}
