package com.news.today.http.parser;

/**
 * Created by anson on 2018/4/8.
 */

public class Result<T> implements IResult<T> {

    protected boolean success = true;
    protected T data;
    protected String msg;
    protected String json;
    protected String code;

    @Override
    public String code() {
        return code;
    }

    @Override
    public T data() {
        return data;
    }

    @Override
    public String msg() {
        return msg;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public String json() {
        return json;
    }

    public static Result failed(String code) {
        Result result = new Result();
        result.success = false;
        result.code = code;
        return result;
    }

    public static <T> Result<T> success(String code, T data) {
        Result<T> simpleResult = new Result<>();
        simpleResult.data = data;
        simpleResult.success = true;
        simpleResult.code = code;
        return simpleResult;
    }
}
