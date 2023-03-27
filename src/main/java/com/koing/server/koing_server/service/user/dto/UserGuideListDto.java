package com.koing.server.koing_server.service.user.dto;

import com.koing.server.koing_server.domain.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserGuideListDto {

    public UserGuideListDto(User user) {
        this.id = user.getId();
        this.name = user.getName();

        if (user.getUserOptionalInfo() != null) {
            if (user.getUserOptionalInfo().getImageUrls() != null && user.getUserOptionalInfo().getImageUrls().size() > 0) {
                this.imageUrl = user.getUserOptionalInfo().getImageUrls().get(0);
            }
        }
        this.guideGrade = user.getGuideGrade().getGrade();
    }

    private Long id;
    private String name;
    private String imageUrl;
    private String guideGrade;

}
