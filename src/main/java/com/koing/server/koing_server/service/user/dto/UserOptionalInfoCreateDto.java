package com.koing.server.koing_server.service.user.dto;

import com.koing.server.koing_server.service.sign.dto.SignUpSetCreateDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserOptionalInfoCreateDto {

    public UserOptionalInfoCreateDto(Long userId, SignUpSetCreateDto signUpSetCreateDto) {
        this.userId = userId;
        this.imageUrl = signUpSetCreateDto.getImageUrl();
        this.description = signUpSetCreateDto.getDescription();
        this.languages = signUpSetCreateDto.getLanguages();
        this.areas = signUpSetCreateDto.getAreas();
        this.job = signUpSetCreateDto.getJob();
        this.universityEmail = signUpSetCreateDto.getUniversityEmail();
        this.company = signUpSetCreateDto.getCompany();
    }

    private Long userId;
    private String imageUrl;
    private String description;
    private Set<String> languages;
    private Set<String> areas;
    private String job;
    private String universityEmail;
    private String company;

}
