package com.koing.server.koing_server.service.user.dto;

import com.koing.server.koing_server.service.sign.dto.SignUpSetCreateDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserOptionalInfoCreateDto {

    public UserOptionalInfoCreateDto(Long userId, SignUpSetCreateDto signUpSetCreateDto, List<MultipartFile> imageFiles) {
        System.out.println("실행 = " + imageFiles);
        this.userId = userId;
//        this.imageUrl = signUpSetCreateDto.getImageUrl();
        this.imageFiles = imageFiles;
        this.description = signUpSetCreateDto.getDescription();
        this.languages = signUpSetCreateDto.getLanguages();
        this.areas = signUpSetCreateDto.getAreas();
        this.job = signUpSetCreateDto.getJob();
        this.universityEmail = signUpSetCreateDto.getUniversityEmail();
        this.company = signUpSetCreateDto.getCompany();
    }

    private Long userId;
//    private String imageUrl;
    private List<MultipartFile> imageFiles;
    private String description;
    private Set<String> languages;
    private Set<String> areas;
    private String job;
    private String universityEmail;
    private String company;

}
