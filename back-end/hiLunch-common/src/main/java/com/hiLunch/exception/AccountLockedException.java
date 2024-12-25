package com.hiLunch.exception;

/**
 * アカウントがロックされている
 */
public class AccountLockedException extends BaseException {

    public AccountLockedException() {
    }

    public AccountLockedException(String msg) {
        super(msg);
    }

}
