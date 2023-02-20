package com.koing.server.koing_server.service.cryptogram.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CryptogramVerifyDto {

    private String userEmail;
    private String cryptogram;

}
