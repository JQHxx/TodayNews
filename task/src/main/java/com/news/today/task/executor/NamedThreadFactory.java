package com.news.today.task.executor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by anson on 2018/4/6.
 */
public class NamedThreadFactory implements ThreadFactory {
    protected final AtomicInteger threadNumber = new AtomicInteger(1);
    protected final String namePrefix;

    public NamedThreadFactory(final String namePrefix) {
        this.namePrefix = namePrefix;
    }

    public Thread newThread(String name, Runnable r) {
        return new Thread(r, name);
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = newThread(namePrefix + threadNumber.getAndIncrement(), r);
        if (t.isDaemon())
            t.setDaemon(false);
        return t;
    }
}
