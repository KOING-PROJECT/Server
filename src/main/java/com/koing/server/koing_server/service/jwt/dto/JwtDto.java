package com.koing.server.koing_server.service.jwt.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class JwtDto {

    private String accessToken;
    private String refreshToken;

}
