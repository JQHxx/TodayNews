package com.news.today.http.exception;

/**
 * Created by anson on 2018/4/8.
 */
public class NetworkNotAvailableException extends LfException {
    public NetworkNotAvailableException() {
    }

    public NetworkNotAvailableException(String msg) {
        super(msg);
    }

    public NetworkNotAvailableException(Throwable throwable) {
        super(throwable);
    }
}
