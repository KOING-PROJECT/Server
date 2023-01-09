package com.koing.server.koing_server.common.exception;

import com.koing.server.koing_server.common.error.ErrorCode;

public class PaymentServerException extends BoilerplateException {

    public PaymentServerException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public PaymentServerException(String message) {
        super(message, ErrorCode.PAYMENT_SERVER_CONNECT_ERROR);
    }
}
