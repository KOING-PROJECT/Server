package com.koing.server.koing_server.service.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserProfileUpdateDto {

    private List<String> uploadedImageFiles;
    private String description;
    private Set<String> languages;
    private Set<String> areas;
    private String job;
    private String universityEmail;
    private String company;
    private boolean isCertified;

}
