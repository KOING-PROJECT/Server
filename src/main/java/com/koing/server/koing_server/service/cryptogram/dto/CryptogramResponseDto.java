package com.koing.server.koing_server.service.cryptogram.dto;

import lombok.Data;

@Data
public class CryptogramResponseDto {

    private CryptogramDto cryptogram;

    public CryptogramResponseDto(CryptogramDto cryptogram) {
        this.cryptogram = cryptogram;
    }
}
