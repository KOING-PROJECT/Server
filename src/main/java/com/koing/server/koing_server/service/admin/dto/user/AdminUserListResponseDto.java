package com.koing.server.koing_server.service.admin.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AdminUserListResponseDto {

    public AdminUserListResponseDto(List<AdminUserResponseDto> users) {
        this.users = users;
    }

    private List<AdminUserResponseDto> users;

}
