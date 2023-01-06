package com.koing.server.koing_server.service.user.dto;

import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.domain.user.UserOptionalInfo;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserFollowDto {

    public UserFollowDto(User followingUser, UserOptionalInfo userOptionalInfo) {
        this.followingUserId = followingUser.getId();
        this.followingUserName = followingUser.getName();
        this.followingUserThumbnail = userOptionalInfo.getImageUrl();
    }

    public UserFollowDto(User followingUser) {
        this.followingUserId = followingUser.getId();
        this.followingUserName = followingUser.getName();
        this.followingUserThumbnail = followingUser.getUserOptionalInfo().getImageUrl();
    }

    private Long followingUserId;
    private String followingUserName;
    private String followingUserThumbnail;

}
