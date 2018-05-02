package com.news.today.http.parser;

/**
 */
public interface IResult<T extends Object> {
    String code();

    T data();

    String msg();

    boolean success();

    String json();
}
