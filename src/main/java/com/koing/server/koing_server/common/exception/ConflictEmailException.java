package com.koing.server.koing_server.common.exception;

import com.koing.server.koing_server.common.error.ErrorCode;

public class ConflictEmailException extends BoilerplateException {

    public ConflictEmailException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public ConflictEmailException(String message) {
        super(message, ErrorCode.CONFLICT_EMAIL_EXCEPTION);
    }
}
