package com.koing.server.koing_server.service.user.dto;

import com.koing.server.koing_server.domain.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserFollowListResponseDto {

    public UserFollowListResponseDto(List<UserFollowDto> followingUsers) {
        this.following = followingUsers;
    }

    private List<UserFollowDto> following;

}
