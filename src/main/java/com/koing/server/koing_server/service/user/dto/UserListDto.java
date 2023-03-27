package com.koing.server.koing_server.service.user.dto;

import com.koing.server.koing_server.common.enums.UserRole;
import com.koing.server.koing_server.domain.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserListDto {

    public UserListDto(User user) {
        this.id = user.getId();

        if (user.getRoles().contains(UserRole.ROLE_GUIDE.getRole())) {
            this.role = UserRole.ROLE_GUIDE.getRole();
            this.guideGrade = user.getGuideGrade().getGrade();
        }
        else {
            this.role = UserRole.ROLE_TOURIST.getRole();
            this.touristGrade = user.getTouristGrade().getGrade();
        }

        this.name = user.getName();

        if (user.getUserOptionalInfo() != null) {
            if (user.getUserOptionalInfo().getImageUrls() != null && user.getUserOptionalInfo().getImageUrls().size() > 0) {
                this.imageUrl = user.getUserOptionalInfo().getImageUrls().get(0);
            }
        }
    }

    private Long id;
    private String role;
    private String name;
    private String imageUrl;
    private String guideGrade;
    private String touristGrade;

}
