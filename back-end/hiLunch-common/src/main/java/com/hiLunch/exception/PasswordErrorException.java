package com.hiLunch.exception;

/**
 * pwdが違うときの異常
 */
public class PasswordErrorException extends BaseException {

    public PasswordErrorException() {
    }

    public PasswordErrorException(String msg) {
        super(msg);
    }

}
