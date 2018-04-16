package com.news.today.http.exception;

/**
 * Created by ye on 2017/7/11.
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
