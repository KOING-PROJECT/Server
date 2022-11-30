package com.koing.server.koing_server.common.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum JwtValidateEnum {
    ACCESS("ACCESS"),
    EXPIRE("EXPIRE"),
    DENIED("DENIED"),
    NOT_MATCHED("NOT_MATCHED");

    private final String status;
}
