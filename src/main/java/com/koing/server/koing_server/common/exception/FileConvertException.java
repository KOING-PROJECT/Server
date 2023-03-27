package com.koing.server.koing_server.common.exception;

import com.koing.server.koing_server.common.error.ErrorCode;

public class FileConvertException extends BoilerplateException {

    public FileConvertException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public FileConvertException(String message) {
        super(message, ErrorCode.DB_FAIL_EXCEPTION);
    }
}
