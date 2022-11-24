package com.koing.server.koing_server.common.exception;

public class ConflictEmailException extends BoilerplateException {

    public ConflictEmailException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public ConflictEmailException(String message) {
        super(message, ErrorCode.CONFLICT_EMAIL_EXCEPTION);
    }
}
