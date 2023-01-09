package com.koing.server.koing_server.service.Payment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentIamportResponseDto<T> {

    private int code;
    private String message;
    private T response;

}
