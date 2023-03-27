package com.koing.server.koing_server.service.payment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentIamportResponseDto<T> {

    private int code;
    private String message;
    private T response;

}
