package com.koing.server.koing_server.service.account.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountCreateDto {

    private Long userId;
    private String bankName;
    private String accountNumber;
    private String birthDate;
    private String registrationNumber;

}
