package com.koing.server.koing_server.common.upperException;

public abstract class BusinessException extends RuntimeException {

    public BusinessException(final String message) {
        super(message);
    }
}
