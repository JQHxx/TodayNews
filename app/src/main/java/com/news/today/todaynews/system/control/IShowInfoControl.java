package com.news.today.todaynews.system.control;


/**
 * 控制
 * Created by ye on 2017/4/21.
 */
public interface IShowInfoControl extends IControl {
    /**
     * 保存任务动作
     *
     * @param taskAction
     */
    void setTaskAction(ITaskAction taskAction);

    /**
     * 展示错误场景
     *
     * @param scene
     * @param data
     */
    boolean showScene(String scene, Object data);
}
