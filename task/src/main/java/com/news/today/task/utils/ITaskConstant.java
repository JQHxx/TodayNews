package com.news.today.task.utils;

/**
 * Created by anson on 2018/4/6.
 */

public interface ITaskConstant {
    String DEFAULT_TASK_NAME = "at";//anonymous-task

    /**
     * 丢弃新任务
     */
    int DISCARD_NEW = 0;

    /**
     * 取消老任务
     */
    int CANCEL_OLD = 1;

    /**
     * 强制提交，任务可重复
     */
    int FORCE_SUBMIT = 2;

    /**
     * 高优先级
     */
    int PRIOR_HIGH = 256;
    int PRIOR_UI = 32;
    /**
     * 一般优先级
     */
    int PRIOR_NORMAL = 1;

    /**
     * 开始
     * UI启动恢复被降级的任务
     */
    int STATUS_START = 0;
    /**
     * 结束
     * UI停止时降级其下的任务
     */
    int STATUS_STOP = 1;
    /**
     * 销毁
     * UI销毁时取消由其发起的所有任务
     */
    int STATUS_DESTROY = 2;
}
