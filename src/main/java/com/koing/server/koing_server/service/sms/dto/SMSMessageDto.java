package com.koing.server.koing_server.service.sms.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SMSMessageDto {

    public SMSMessageDto(String receivePhoneNumber) {
        this.receivePhoneNumber = receivePhoneNumber;
    }

    private String receivePhoneNumber;

}
