package com.koing.server.koing_server.service.Payment.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentSuccessDto {

    private int imp_uid;
    private int merchant_uid;

}
