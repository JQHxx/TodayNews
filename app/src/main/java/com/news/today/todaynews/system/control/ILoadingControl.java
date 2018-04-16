package com.news.today.todaynews.system.control;


/**
 * Created by yh on 2016/5/11.
 */
public interface ILoadingControl extends IControl {
    /**
     *
     */
    void showLoading();

    void dismissLoading();

    /**
     * 是否正在加载
     *
     * @return
     */
    boolean isLoading();

}
