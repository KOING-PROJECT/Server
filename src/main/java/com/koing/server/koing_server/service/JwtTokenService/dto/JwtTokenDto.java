package com.koing.server.koing_server.service.JwtTokenService.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class JwtTokenDto {

    private String accessToken;
    private String refreshToken;

}
