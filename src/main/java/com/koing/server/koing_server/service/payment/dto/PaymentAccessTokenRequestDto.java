package com.koing.server.koing_server.service.payment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentAccessTokenRequestDto {

    public PaymentAccessTokenRequestDto(String imp_key, String imp_secret) {
        this.imp_key = imp_key;
        this.imp_secret = imp_secret;
    }

    private String imp_key;
    private String imp_secret;

}
