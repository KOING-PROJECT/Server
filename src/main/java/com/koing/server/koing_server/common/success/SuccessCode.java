package com.koing.server.koing_server.common.success;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.koing.server.koing_server.common.success.SuccessStatusCode.CREATED;
import static com.koing.server.koing_server.common.success.SuccessStatusCode.OK;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessCode {

    /**
     * 200 OK
     */
    SUCCESS(OK, "성공입니다."),

    // 인증
    LOGIN_SUCCESS(OK, "Sign-in : 로그인 성공입니다."),
//    REISSUE_TOKEN_SUCCESS(OK, "토큰 갱신 성공입니다."),

    GET_USER_SUCCESS(OK, "User : 유저 조회 성공입니다."),
    GET_USERS_SUCCESS(OK, "User : 유저 리스트 조회 성공입니다."),

    /**
     * 201 CREATED
     */
    SIGN_UP_SUCCESS(CREATED, "Sign-up : 회원가입 성공입니다.")


    /**
     * 202 ACCEPTED
     */

    /**
     * 204 NO_CONTENT
     */
    ;

    private final SuccessStatusCode statusCode;
    private final String message;

    public int getStatus() {
        return statusCode.getStatus();
    }
}
