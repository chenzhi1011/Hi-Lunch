package com.hiLunch.exception;

/**
 * 業務上には例外あり
 */
public class BaseException extends RuntimeException {

    public BaseException() {
    }
    public BaseException(String msg) {
        super(msg);
    }

}
