package com.koing.server.koing_server.service.user.dto;

import com.koing.server.koing_server.domain.user.UserOptionalInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class UserProfileDto {

    public UserProfileDto(UserOptionalInfo userOptionalInfo) {
        if (userOptionalInfo.getImageUrls() != null && userOptionalInfo.getImageUrls().size() > 0) {
            this.imageUrl = userOptionalInfo.getImageUrls().get(0);
        }
        this.description = userOptionalInfo.getDescription();
        this.languages = userOptionalInfo.getLanguages();
        this.areas = userOptionalInfo.getAreas();
        this.job = userOptionalInfo.getJob();
        this.universityEmail = userOptionalInfo.getUniversityEmail();
        this.company = userOptionalInfo.getCompany();
        this.isCertified = userOptionalInfo.isCertified();
    }

    private String imageUrl;
    private String description;
    private Set<String> languages;
    private Set<String> areas;
    private String job;
    private String universityEmail;
    private String company;
    private boolean isCertified;

}
