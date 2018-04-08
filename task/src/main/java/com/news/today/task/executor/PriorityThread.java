package com.news.today.task.executor;

import static android.os.Process.setThreadPriority;

/**
 * Created by anson on 2018/4/6.
 */
public final class PriorityThread extends Thread {
    private final int priority;

    public PriorityThread(int priority, String name, Runnable runnable) {
        super(runnable, name);
        this.priority = priority;
    }

    @Override
    public void run() {
        if (priority != NORM_PRIORITY) setThreadPriority(priority);
        super.run();
    }
}
