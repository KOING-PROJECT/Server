package com.koing.server.koing_server.common.dto;

import lombok.*;

@Getter
public class SuperResponse<T> {

    public int status;
    public boolean success;
    public String message;
    public T data;
//    T getDate();

}
