package com.koing.server.koing_server.common.exception;

import com.koing.server.koing_server.common.error.ErrorCode;

public class JwtInvalidFormException extends BoilerplateException {

    public JwtInvalidFormException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public JwtInvalidFormException(String message) {
        super(message, ErrorCode.UNAUTHORIZED_REFRESH_TOKEN_SERIALIZATION_EXCEPTION);
    }
}
