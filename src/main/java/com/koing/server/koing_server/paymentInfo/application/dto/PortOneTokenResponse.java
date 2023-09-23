package com.koing.server.koing_server.paymentInfo.application.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PortOneTokenResponse {

    private String accessToken;
    private int now;
    private int expired_at;

}
