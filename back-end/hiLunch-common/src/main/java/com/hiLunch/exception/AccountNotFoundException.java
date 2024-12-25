package com.hiLunch.exception;
/**
 * アカウントが存在しないエラー
 */
public class AccountNotFoundException extends BaseException {

    public AccountNotFoundException() {
    }

    public AccountNotFoundException(String msg) {
        super(msg);
    }

}