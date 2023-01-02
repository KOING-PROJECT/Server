package com.koing.server.koing_server.service.sign.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignUpSetCreateDto {

    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String birthDate;
    private String country;
    private String role;
    private String gender;
    private int age;
    private String imageUrl;
    private String description;
    private Set<String> languages;
    private Set<String> areas;
    private String job;
    private String universityEmail;
    private String company;

}
