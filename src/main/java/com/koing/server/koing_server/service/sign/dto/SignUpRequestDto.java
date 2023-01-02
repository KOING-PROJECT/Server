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
        this.birthDate = signUpSetCreateDto.getBirthDate();
        this.country = signUpSetCreateDto.getCountry();
        this.role = signUpSetCreateDto.getRole();
        this.gender = signUpSetCreateDto.getGender();
        this.age = signUpSetCreateDto.getAge();
    }

    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String birthDate;
    private String country;
    private String role;
    private String gender;
    private int age;

}
