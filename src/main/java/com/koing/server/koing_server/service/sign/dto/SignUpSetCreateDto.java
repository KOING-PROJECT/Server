package com.koing.server.koing_server.service.sign.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignUpSetCreateDto {

    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String ageRange;
    private String country;
    private String role;
    private String gender;
    private List<MultipartFile> imageFiles;
    private String description;
    private Set<String> languages;
    private Set<String> areas;
    private String job;
    private String universityEmail;
    private String company;

}
