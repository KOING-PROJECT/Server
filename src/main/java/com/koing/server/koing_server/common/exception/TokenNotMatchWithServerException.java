package com.koing.server.koing_server.common.exception;

public class TokenNotMatchWithServerException extends BoilerplateException {

    public TokenNotMatchWithServerException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public TokenNotMatchWithServerException(String message) {
        super(message, ErrorCode.UNAUTHORIZED_TOKEN_NOT_MATCH_WITH_SERVER_EXCEPTION);
    }
}
