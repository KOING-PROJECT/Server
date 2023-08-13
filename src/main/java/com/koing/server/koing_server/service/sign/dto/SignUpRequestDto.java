package com.koing.server.koing_server.service.sign.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SignUpRequestDto {

    public SignUpRequestDto(SignUpSetCreateDto signUpSetCreateDto) {
        this.email = signUpSetCreateDto.getEmail();
        this.password = signUpSetCreateDto.getPassword();
        this.name = signUpSetCreateDto.getName();
        this.phoneNumber = signUpSetCreateDto.getPhoneNumber();
        this.country = signUpSetCreateDto.getCountry();
        this.role = signUpSetCreateDto.getRole();
    }

    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String country;
    private String role;
}
