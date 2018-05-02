package com.news.today.http.exception;

/**
 * Created by anson on 2018/4/8.
 */
public class NetworkResultParserException extends LfException {

    public NetworkResultParserException(Throwable throwable) {
        super(throwable);
    }

    public NetworkResultParserException() {
        super("解析错误");
    }

}
