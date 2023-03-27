package com.koing.server.koing_server.common.exception;

import com.koing.server.koing_server.common.error.ErrorCode;

public class IOFailException extends BoilerplateException {

    public IOFailException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public IOFailException(String message) {
        super(message, ErrorCode.DB_FAIL_UPLOAD_IMAGE_FAIL_EXCEPTION);
    }
}
