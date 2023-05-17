package com.koing.server.koing_server.service.admin.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminUserDetailResponseWrapperDto {

    public AdminUserDetailResponseWrapperDto(AdminUserDetailResponseDto userDetail) {
        this.userDetail = userDetail;
    }

    private AdminUserDetailResponseDto userDetail;

}
