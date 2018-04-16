package com.news.today.http.exception;

/**
 * 数据内容为空的异常
 * Created by ye on 2017/6/20.
 */
public class NetWorkException extends LfException {
    public NetWorkException(Throwable throwable){
        super(throwable);
    }
}
