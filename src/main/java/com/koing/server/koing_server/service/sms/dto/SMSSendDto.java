package com.koing.server.koing_server.service.sms.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class SMSSendDto {

    public SMSSendDto(String countryCode, String fromPhoneNumber, String content, String receivePhoneNumber) {
        this.type = "SMS";
        this.contentType = "COMM";
        this.countryCode = countryCode;
        this.from = fromPhoneNumber;
        this.content = content;
        setMessage(receivePhoneNumber);
    }

    private String type;
    private String contentType;
    private String countryCode;
    private String from;
    private String content;
    private List<SMSMessageDto> messages;

    private void setMessage(String receivePhoneNumber) {
        this.messages = new ArrayList<>();

        SMSMessageDto smsMessageDto = new SMSMessageDto(receivePhoneNumber);
        messages.add(smsMessageDto);
    }

}
