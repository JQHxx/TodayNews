package com.news.today.http.callback;


import com.news.today.http.builder.IRequest;

/**
 * Created by yh on 2016/4/15.
 */
public interface ICall extends ICancelable {
    int STATUS_NEW = 0;//初始化
    int STATUS_READY = 1;//准备好可以进入running
    int STATUS_RUNNING = 3;//执行中
    int STATUS_OVER = 4;//结束

    /**
     * 状态只允许往上升
     *
     * @return
     */
    int getStatus();

    IResponse execute() throws NetWorkException;

    /**
     * 地址
     *
     * @return
     */
    String getUrl();

    IRequest getRequest();
}
