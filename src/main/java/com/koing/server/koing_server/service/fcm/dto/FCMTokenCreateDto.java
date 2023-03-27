package com.koing.server.koing_server.service.fcm.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FCMTokenCreateDto {

    private Long userId;
    private String fcmToken;

}
