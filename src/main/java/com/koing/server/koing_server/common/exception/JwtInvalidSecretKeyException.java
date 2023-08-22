package com.koing.server.koing_server.common.exception;

import com.koing.server.koing_server.common.error.ErrorCode;

public class JwtInvalidSecretKeyException extends BoilerplateException {

    public JwtInvalidSecretKeyException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public JwtInvalidSecretKeyException(String message) {
        super(message, ErrorCode.UNAUTHORIZED_REFRESH_TOKEN_SERIALIZATION_EXCEPTION);
    }
}
