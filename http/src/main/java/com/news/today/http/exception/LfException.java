package com.news.today.http.exception;

/**
 * Created by anson on 2018/4/8.
 */

public abstract class LfException extends RuntimeException {

    public LfException() {

    }

    public LfException(String msg) {
        super(msg);
    }

    public LfException(Throwable throwable) {
        super(throwable);
    }
}
