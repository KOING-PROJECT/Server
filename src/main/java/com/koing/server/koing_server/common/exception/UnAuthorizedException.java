package com.koing.server.koing_server.common.exception;

public class UnAuthorizedException extends BoilerplateException {

    public UnAuthorizedException(String message) {
        super(message, ErrorCode.UNAUTHORIZED_EXCEPTION);
    }
}
