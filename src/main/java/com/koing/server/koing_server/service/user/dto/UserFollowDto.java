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
        if (userOptionalInfo.getImageUrls() != null &&
                userOptionalInfo.getImageUrls().size() > 0) {
            this.followingUserThumbnail = userOptionalInfo.getImageUrls().get(0);
        }
    }

    public UserFollowDto(User followingUser) {
        this.followingUserId = followingUser.getId();
        this.followingUserName = followingUser.getName();
        if (followingUser.getUserOptionalInfo().getImageUrls() != null &&
                followingUser.getUserOptionalInfo().getImageUrls().size() > 0) {
            this.followingUserThumbnail = followingUser.getUserOptionalInfo().getImageUrls().get(0);
        }
    }

    private Long followingUserId;
    private String followingUserName;
    private String followingUserThumbnail;

}
