package com.koing.server.koing_server.service.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentAccessTokenDto {

    @JsonProperty("access_token")
    private String accessToken;

    private Long now;

    @JsonProperty("expired_at")
    private Long expiredAt;

}
