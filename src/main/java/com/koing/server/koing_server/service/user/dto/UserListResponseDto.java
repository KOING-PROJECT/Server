package com.koing.server.koing_server.service.user.dto;

import com.koing.server.koing_server.domain.user.User;
import lombok.*;

import java.util.List;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class UserListResponseDto {

    private List<User> users;

    public static UserListResponseDto of(List<User> users) {
        return UserListResponseDto.builder()
                .users(users)
                .build();
    }

}
