package com.cn.autotest.exceptions;

public class ErrorException extends Exception {
    public ErrorException() {
        super();
    }
    public ErrorException(Object object) {
        super(object.toString());
    }
}
