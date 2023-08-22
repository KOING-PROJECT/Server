package com.koing.server.koing_server.common.exception;

import com.koing.server.koing_server.common.error.ErrorCode;

public class SerializingException extends BoilerplateException {

    public SerializingException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public SerializingException(String message) {
        super(message, ErrorCode.UNAUTHORIZED_REFRESH_TOKEN_SERIALIZATION_EXCEPTION);
    }
}
