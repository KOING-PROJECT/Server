package com.koing.server.koing_server.service.account.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountResponseDto {

    public AccountResponseDto(String bankName, String accountNumber, String birthDate, String registrationNumber) {
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.birthDate = birthDate;
        this.registrationNumber = registrationNumber;
    }

    private String bankName;
    private String accountNumber;
    private String birthDate;
    private String registrationNumber;

}
