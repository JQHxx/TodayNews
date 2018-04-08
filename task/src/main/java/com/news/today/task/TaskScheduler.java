package com.news.today.task;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Process;
import android.util.Log;

import com.news.today.task.executor.PriorityThreadPoolExecutor;
import com.news.today.task.executor.TaskPriorityManager;
import com.news.today.task.lf.IGroup;
import com.news.today.task.lf.ITaskInstance;
import com.news.today.task.utils.ITaskConstant;
import com.news.today.task.utils.TaskUtil;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

import static com.news.today.task.lf.IGroup.DEFAULT_GROUP_NAME;


/**
 * Created by anson on 2018/4/6.
 */
public final class TaskScheduler {
    private String TAG = TaskScheduler.class.getSimpleName();
    private static final int MSG_TYPE_SUBMIT_TASK = 0;//任务提交事件
    private static final int MSG_TYPE_TASK_OVER = 1;//任务结束事件
    private static final int MSG_TYPE_CHANGE_PRIORITY = 2;//调整任务优先级事件

    private final int CORE_POOL_SIZE = TaskUtil.CPU_COUNT * 2 + 1;//常驻最大线程数
    private final int MAXIMUM_POOL_SIZE = TaskUtil.CPU_COUNT * 16 + 1;//最大线程数
    private final int QUEUE_SIZE = CORE_POOL_SIZE + 1;//队列大小
    private final int KEEP_ALIVE = 1;//空闲线程存活超时时间
    private final Handler handler;
    private final PriorityThreadPoolExecutor executor;
    private final TaskPriorityManager taskPriorityManager;
    private static TaskScheduler instance = null;

    private TaskScheduler() {
        /**
         * 用于消息调度的线程
         */
        HandlerThread handlerThread = new HandlerThread("task-handler-thread");
        handlerThread.start();
        this.handler = new Handler(handlerThread.getLooper(), new HandlerCallback());

        /**
         * task必须实现Comparable,否则要使用构造函数PriorityBlockingQueue(int, Comparator)
         * @see AbstractTaskInstance#compareTo(IPriorityTask)
         */
        BlockingQueue<Runnable> poolWorkQueue = new PriorityBlockingQueue<>(QUEUE_SIZE);
        this.executor = new PriorityThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAXIMUM_POOL_SIZE,
                KEEP_ALIVE, TimeUnit.SECONDS,
                poolWorkQueue,
                Process.THREAD_PRIORITY_BACKGROUND,
                new ExecuteInterceptor());
        this.taskPriorityManager = new TaskPriorityManager(this, poolWorkQueue);
    }

    public static TaskScheduler instance() {
        if (instance == null) {
            synchronized (TaskScheduler.class) {
                if (instance == null) {
                    instance = new TaskScheduler();
                }
            }
        }
        return instance;
    }


    /**
     * 提交新任务
     *
     * @param task
     */
    public void submit(AbstractTaskInstance<?> task) {
        handler.sendMessage(handler.obtainMessage(MSG_TYPE_SUBMIT_TASK, task));
    }


    private void doSubmitTask(AbstractTaskInstance<?> task) {
        task.onSubmit();
        //1.默认分组的任务直接提交到线程池，判断groupName
        if (task.groupName() == IGroup.DEFAULT_GROUP_NAME) {
            submitReadyTask(task);
        } else {
            //2.任务去重
            AbstractTaskInstance<?> oldTask = taskPriorityManager.getOldTask(task);
            if (oldTask != null) {
                int dualPolicy = task.dualPolicy();
                if (dualPolicy == ITaskConstant.DISCARD_NEW) {
                    Log.d(TAG, String.format("discard new submit task（%s）", task.taskName()));
                    return;
                }
                //3.首先添加任务到任务分组队列
                taskPriorityManager.addTask(task);
                //4.同时检测下任务是否可提交，不可提交则设置任务为等待状态
                if (canSubmit(task)) {
                    submitReadyTask(task);
                } else {
                    task.setStatus(ITaskInstance.STATUS_WAIT);
                }
            }
        }
    }

    private void stopTask(AbstractTaskInstance task) {
        if (!task.isCancelled()) {
            task.cancel(true);//手动取消也会会触发一次done()
            task.setStatus(ITaskInstance.STATUS_CANCEL);
        }
        removeEndTask(task);
    }

    /**
     * 直接提交任务到线程池
     *
     * @param task
     */
    public void submitReadyTask(AbstractTaskInstance<?> task) {
        task.setStatus(ITaskInstance.STATUS_READY);
        executor.submit(task);
    }

    /**
     * 判断当前任务是否可提交到线程池
     *
     * @param task
     * @return
     */
    boolean canSubmit(AbstractTaskInstance<?> task) {
        //1.线程池队列已满，则禁止新任务提交
        if (executor.getQueue().size() >= QUEUE_SIZE) {
            return false;
        }
        //2.如果是并行任务则可以直接提交
        if (!task.serialExecute()) return true;
        //3.如果是串行任务，则要先判断下当前组里是否有正在执行的任务，有则禁止提交
        List<AbstractTaskInstance<?>> tasks = taskPriorityManager.getByGroup(task.groupName());
        if (tasks.isEmpty()) return true;
        for (AbstractTaskInstance t : tasks) {
            if (t.status == ITaskInstance.STATUS_RUNNING || t.status == ITaskInstance.STATUS_READY) {
                Log.e(TAG, String.format("task（%s） need wait,has other task in status:", task.taskName(), t.status));
                return false;
            }
        }
        return true;
    }

    /**
     * 移除执行结束的任务
     *
     * @param task
     */
    void removeEndTask(AbstractTaskInstance<?> task) {
        taskPriorityManager.removeTask(task);
    }

    /**
     * 提交等待中的任务
     */
    private void submitWaitTasks(AbstractTaskInstance<?> task) {
        List<AbstractTaskInstance<?>> tasks = taskPriorityManager.getNextTaskGroup(task);
        if (tasks == null) return;
        for (AbstractTaskInstance<?> t : tasks) {
            if (t.status == ITaskInstance.STATUS_WAIT) {
                if (canSubmit(t)) submitReadyTask(t);
                break;
            }
        }
    }

    private void doTaskOver(AbstractTaskInstance<?> task) {
        removeEndTask(task);//从分组删除执行完毕的任务
        submitWaitTasks(task);//提交下一个等待中的任务
    }

    public void scheduleTask(int status, String groupName, String taskName) {
        handler.sendMessage(handler.obtainMessage(MSG_TYPE_CHANGE_PRIORITY, status, 0, new String[]{groupName, taskName}));
    }

    public void cancelGroup(String groupName) {
        scheduleTask(ITaskConstant.STATUS_DESTROY, groupName, null);
    }

    public void onPause(String groupName) {
        scheduleTask(ITaskConstant.STATUS_STOP, groupName, null);
    }

    public void onResume(String groupName) {
        scheduleTask(ITaskConstant.STATUS_START, groupName, null);
    }

    public void cancelTask(String groupName, String taskName) {
        scheduleTask(ITaskConstant.STATUS_DESTROY, groupName, taskName);
    }


    public void stopTaskOuter(AbstractTaskInstance r) {
        r.cancel(true);
        handler.sendMessage(handler.obtainMessage(MSG_TYPE_TASK_OVER, r));
    }


    private final class ExecuteInterceptor implements PriorityThreadPoolExecutor.Interceptor {

        @Override
        public void before(Runnable r) {
            ((AbstractTaskInstance<?>) r).setStatus(ITaskInstance.STATUS_RUNNING);
        }

        @Override
        public void after(Runnable r, Throwable t) {
            ((AbstractTaskInstance) r).setStatus(ITaskInstance.STATUS_OVER);
            handler.sendMessage(handler.obtainMessage(MSG_TYPE_TASK_OVER, r));
        }
    }

    private final class HandlerCallback implements Handler.Callback {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_TYPE_SUBMIT_TASK:
                    doSubmitTask((AbstractTaskInstance<?>) msg.obj);
                    break;
                case MSG_TYPE_TASK_OVER:
                    doTaskOver((AbstractTaskInstance<?>) msg.obj);
                    break;
                case MSG_TYPE_CHANGE_PRIORITY:
                    String[] msgs = (String[]) msg.obj;
                    String groupName = null;
                    String taskName = null;
                    if (msgs != null) {
                        switch (msgs.length) {
                            case 1:
                                groupName = msgs[0];
                                break;
                            case 2:
                                groupName = msgs[0];
                                taskName = msgs[1];
                                break;
                        }
                    }
                    if (groupName != null) {
                        taskPriorityManager.changeTaskPriority(msg.arg1, groupName, taskName);
                    }
                    break;
            }
            return true;
        }
    }
}
