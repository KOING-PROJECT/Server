package com.koing.server.koing_server.service.sms.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SMSRequestDto {

    public SMSRequestDto(String countryCode, String fromPhoneNumber, String content, String receivePhoneNumber) {
        this.type = "SMS";
        this.contentType = "COMM";
        this.countryCode = countryCode;
        this.fromPhoneNumber = fromPhoneNumber;
        this.content = content;
        this.messages = new SMSMessageDto(receivePhoneNumber);
    }

    private String type;
    private String contentType;
    private String countryCode;
    private String fromPhoneNumber;
    private String content;
    private SMSMessageDto messages;

}
