package com.base.rxjava.exception;

/**
 * Created by WangHao
 *
 * @ 创建时间 2018/4/9  16:43
 */
public class ApiException extends BaseException {
    public ApiException(int code, String displayMessage) {
        super(code, displayMessage);
    }
}
