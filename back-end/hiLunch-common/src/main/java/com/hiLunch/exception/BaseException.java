package com.hiLunch.exception;

/**
 * 業務上には異常あり
 */
public class BaseException extends RuntimeException {

    public BaseException() {
    }
    public BaseException(String msg) {
        super(msg);
    }

}
