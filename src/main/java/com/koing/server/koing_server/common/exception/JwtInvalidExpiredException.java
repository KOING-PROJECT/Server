package com.koing.server.koing_server.common.exception;

import com.koing.server.koing_server.common.error.ErrorCode;

public class JwtInvalidExpiredException extends BoilerplateException {

    public JwtInvalidExpiredException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public JwtInvalidExpiredException(String message) {
        super(message, ErrorCode.UNAUTHORIZED_REFRESH_TOKEN_SERIALIZATION_EXCEPTION);
    }
}
