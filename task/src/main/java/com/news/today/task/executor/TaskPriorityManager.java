package com.news.today.task.executor;

import com.news.today.task.AbstractTaskInstance;
import com.news.today.task.utils.ITaskConstant;
import com.news.today.task.TaskScheduler;
import com.news.today.task.lf.ITaskInstance;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by anson on 2018/4/6.
 */
public final class TaskPriorityManager {
    final Map<String, List<AbstractTaskInstance<?>>> taskGroups;
    final BlockingQueue<Runnable> poolWorkQueue;
    private TaskScheduler taskScheduler;

    public TaskPriorityManager(TaskScheduler taskScheduler, BlockingQueue<Runnable> poolWorkQueue) {
        this.taskScheduler = taskScheduler;
        this.taskGroups = new ConcurrentHashMap<>();
        this.poolWorkQueue = poolWorkQueue;
    }

    public void addTask(AbstractTaskInstance<?> task) {
        getByGroup(task.groupName()).add(task);
    }

    public List<AbstractTaskInstance<?>> getByGroup(String groupName) {
        List<AbstractTaskInstance<?>> group = taskGroups.get(groupName);
        if (group == null) {
            group = new CopyOnWriteArrayList<>();
            taskGroups.put(groupName, group);
        }
        return group;
    }

    public AbstractTaskInstance getOldTask(AbstractTaskInstance<?> task) {
        List<AbstractTaskInstance<?>> tasks = taskGroups.get(task.groupName());
        if (tasks == null || tasks.isEmpty()) return null;
        for (AbstractTaskInstance<?> t : tasks) {
            if (t.equals(task)) return t;
        }
        return null;
    }

    public void removeTask(AbstractTaskInstance<?> task) {
        List<AbstractTaskInstance<?>> group = taskGroups.get(task.groupName());
        if (group == null || group.isEmpty()) return;
        group.remove(task);
        if (group.isEmpty()) {
            taskGroups.remove(task.groupName());
        }
    }

    /**
     * 选取策略：首先从所有分组中随机选取一个分组
     * 如果该组中的任务为空，那么找出第一个不为空的组返回
     * 这里有点问题：任务是随机执行的？ 先是找本组是否还有任务可以执行，再去寻找其他的组
     * @return 下一个可用的任务分组
     */
    public List<AbstractTaskInstance<?>> getNextTaskGroup(AbstractTaskInstance<?> task) {
        //第一步：先去备忘录中查找本组是否还有任务执行
        List<AbstractTaskInstance<?>> byGroup = getByGroup(task.groupName());
        if (byGroup != null && byGroup.size() > 0) {
            return byGroup;
        }
        //第一步：再去备忘录中随机找个组去执行
        Collection<List<AbstractTaskInstance<?>>> allGroups = taskGroups.values();//所有的任务分组
        if (allGroups.isEmpty() || allGroups.size() == 0) {
            return null;
        }
        List<AbstractTaskInstance<?>> next = null;
        for (List<AbstractTaskInstance<?>> group : allGroups) {
            if (next == null && group.size() > 0) {
                next = group;
                if (next != null) {
                    break;
                }
            }
        }
        return next;
    }

    public void changePriority(AbstractTaskInstance<?> task, int priority) {
        if (poolWorkQueue.isEmpty()) return;
        if (task.status != ITaskInstance.STATUS_READY) return;
        if (poolWorkQueue.remove(task)) {
            task.priority = priority;
            taskScheduler.submitReadyTask(task);
        }
    }



    /**
     * 更改某一个任务的优先级
     *
     * @param status
     * @param groupName
     * @param taskName
     */
    public void changeTaskPriority(int status, String groupName, String taskName) {
        List<AbstractTaskInstance<?>> tasks = taskGroups.get(groupName);
        if (tasks == null || tasks.isEmpty()) {
            return;
        }
        switch (status) {
            case ITaskConstant.STATUS_START://UI启动恢复被降级的任务
                for (AbstractTaskInstance<?> task : tasks) {
                    if (taskName != null && !taskName.equals(task.taskName())) {
                        continue;
                    }
                    if (task.priority != ITaskConstant.PRIOR_NORMAL) {//只恢复被onStop事件降级的任务
                        changePriority(task, task.priority + 1);
                    }
                }
                break;
            case ITaskConstant.STATUS_STOP://UI停止时降级其下的任务
                for (AbstractTaskInstance<?> task : tasks) {
                    if (taskName != null && !taskName.equals(task.taskName())) {
                        continue;
                    }
                    changePriority(task, task.priority - 1);
                }
                break;
            case ITaskConstant.STATUS_DESTROY://UI销毁时取消由其发起的所有任务
                for (AbstractTaskInstance<?> task : tasks) {
                    if (taskName != null && !taskName.equals(task.taskName())) {
                        continue;
                    }
                    taskScheduler.stopTaskOuter(task);
                }
                break;
        }
    }
}
