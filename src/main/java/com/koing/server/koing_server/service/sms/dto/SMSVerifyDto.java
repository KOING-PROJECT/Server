package com.koing.server.koing_server.service.sms.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SMSVerifyDto {

    private String countryCode;
    private String receivePhoneNumber;
    private String certificationNumber;

}
