package com.koing.server.koing_server.common.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum JwtValidateEnum {
    ACCESS("ACCESS"),
    EXPIRE("EXPIRE"),
    DENIED("DENIED");

    private final String status;
}
