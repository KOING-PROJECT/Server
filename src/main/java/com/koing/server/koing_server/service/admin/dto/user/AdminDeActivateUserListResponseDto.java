package com.koing.server.koing_server.service.admin.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AdminDeActivateUserListResponseDto {

    public AdminDeActivateUserListResponseDto(List<AdminDeActivateUserResponseDto> deActivateUsers) {
        this.deActivateUsers = deActivateUsers;
    }

    private List<AdminDeActivateUserResponseDto> deActivateUsers;

}
