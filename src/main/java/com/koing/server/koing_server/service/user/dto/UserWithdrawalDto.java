package com.koing.server.koing_server.service.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserWithdrawalDto {

    private Long userId;
    private String withDrawalReason;
    private String password;

}
