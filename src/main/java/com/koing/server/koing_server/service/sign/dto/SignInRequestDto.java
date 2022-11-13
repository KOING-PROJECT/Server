package com.koing.server.koing_server.service.sign.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SignInRequestDto {

    private String email;
    private String password;

    public SignInRequestDto newSignInRequestDto(String email, String password) {
        return builder()
                .email(email)
                .password(password)
                .build();
    }

}
