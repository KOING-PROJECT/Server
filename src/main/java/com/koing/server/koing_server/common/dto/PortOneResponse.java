package com.koing.server.koing_server.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PortOneResponse<T> {

    private int code;
    private String message;
    private T response;

}
