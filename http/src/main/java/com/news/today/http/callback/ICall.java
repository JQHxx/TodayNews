package com.news.today.http.callback;


import com.news.today.http.api.IApi;
import com.news.today.http.exception.NetWorkException;

/**
 * Created by anson on 2018/4/15.
 */
public interface ICall  {
    int STATUS_NEW = 0;//初始化
    int STATUS_READY = 1;//准备好可以进入running
    int STATUS_RUNNING = 3;//执行中
    int STATUS_OVER = 4;//结束

    IResponse execute() throws NetWorkException;

    String getUrl();

    IApi getRequest();
}
