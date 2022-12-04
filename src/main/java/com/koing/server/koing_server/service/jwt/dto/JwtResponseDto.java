package com.koing.server.koing_server.service.jwt.dto;

import lombok.Data;

@Data
public class JwtResponseDto {

    private JwtDto jwt;

    public JwtResponseDto(JwtDto jwtDto) {
        this.jwt = jwtDto;
    }
}
