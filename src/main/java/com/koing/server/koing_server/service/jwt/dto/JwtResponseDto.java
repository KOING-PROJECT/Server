package com.koing.server.koing_server.service.jwt.dto;

import com.koing.server.koing_server.domain.Jwt.JwtToken;
import lombok.Data;

@Data
public class JwtResponseDto {

    private JwtToken jwt;

    public JwtResponseDto(JwtToken jwt) {
        this.jwt = jwt;
    }
}
