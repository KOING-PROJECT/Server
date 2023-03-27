package com.koing.server.koing_server.service.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserPasswordChangeDto {

    private Long userId;
    private String previousPassword;
    private String newPassword;

}
