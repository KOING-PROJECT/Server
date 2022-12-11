package com.koing.server.koing_server.common.success;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.koing.server.koing_server.common.success.SuccessStatusCode.CREATED;
import static com.koing.server.koing_server.common.success.SuccessStatusCode.OK;
import static com.koing.server.koing_server.common.success.SuccessStatusCode.ACCEPTED;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessCode {

    /**
     * 200 OK
     */
    SUCCESS(OK, "성공입니다."),

    // 인증
    LOGIN_SUCCESS(OK, "signIn : 로그인 성공입니다."),
    GET_USER_SUCCESS(OK, "user : 유저 조회 성공입니다."),
    GET_USERS_SUCCESS(OK, "user : 유저 리스트 조회 성공입니다."),
    RE_ISSUE_TOKEN_SUCCESS(OK, "reIssue : 토큰 재발급 성공입니다."),
    CRYPTOGRAM_CERTIFICATION_SUCCESS(OK, "cryptogram : 이메일 인증 성공입니다."),

    /**
     * 201 CREATED
     */
    SIGN_UP_SUCCESS(CREATED, "sign-up : 회원가입 성공입니다."),
    CRYPTOGRAM_CREATE_SUCCESS(CREATED, "cryptogram-create : Cryptogram 생성 성공입니다."),
    CRYPTOGRAM_UPDATE_SUCCESS(CREATED, "cryptogram-update : Cryptogram 업데이트 성공입니다."),
    EMAIL_SEND_SUCCESS(CREATED, "eamil-send : Email send 성공입니다."),


    /**
     * 202 ACCEPTED
     */
    SIGN_UP_EMAIL_CHECK_SUCCESS(ACCEPTED, "sign-up/email-check : 사용가능한 이메일 입니다.")

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
