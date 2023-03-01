package com.koing.server.koing_server.service.sms.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SMSResponseDto {

    private String requestId;
    private String requestTime;
    private String statusCode;
    private String statusName;

}
