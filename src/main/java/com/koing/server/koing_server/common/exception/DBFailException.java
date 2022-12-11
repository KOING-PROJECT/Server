package com.koing.server.koing_server.common.exception;

import com.koing.server.koing_server.common.error.ErrorCode;

public class DBFailException extends BoilerplateException {

    public DBFailException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public DBFailException(String message) {
        super(message, ErrorCode.DB_FAIL_EXCEPTION);
    }
}
