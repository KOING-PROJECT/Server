package com.koing.server.koing_server.common.exception;

import com.koing.server.koing_server.common.error.ErrorCode;

public class NotAcceptableException extends BoilerplateException {

    public NotAcceptableException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public NotAcceptableException(String message) {
        super(message, ErrorCode.NOT_ACCEPTABLE_EXCEPTION);
    }
}
