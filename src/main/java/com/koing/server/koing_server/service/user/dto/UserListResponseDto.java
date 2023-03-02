package com.koing.server.koing_server.service.user.dto;

import com.koing.server.koing_server.domain.user.User;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
public class UserListResponseDto {

    public UserListResponseDto(List<UserListDto> users) {
        this.users = users;
    }

    private List<UserListDto> users;

}
