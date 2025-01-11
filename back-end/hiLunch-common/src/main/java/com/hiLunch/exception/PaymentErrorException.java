package com.hiLunch.exception;

import com.hiLunch.properties.PaypayProperties;

public class PaymentErrorException extends BaseException{
    public PaymentErrorException() {
    }

    public PaymentErrorException(String msg) {
        super(msg);
    }
}
