package com.koing.server.koing_server.service.sign.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SignUpRequestDto {

    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String birthDate;
    private String country;
    private String role;
    private String gender;
    private int age;
    private boolean enabled;

    public SignUpRequestDto newSignUpRequestDto(
            String email, String password, String name, String birthDate,
            String phoneNumber, String country, String role, String gender,
            int age ,boolean enabled) {

        return SignUpRequestDto.builder()
                .email(email)
                .password(password)
                .name(name)
                .phoneNumber(phoneNumber)
                .birthDate(birthDate)
                .country(country)
                .role(role)
                .gender(gender)
                .age(age)
                .enabled(enabled)
                .build();
    }
}
