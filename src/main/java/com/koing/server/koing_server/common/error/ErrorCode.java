package com.koing.server.koing_server.common.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.koing.server.koing_server.common.error.ErrorStatusCode.*;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

    /**
     * 400 Bad Request
     */
    VALIDATION_EXCEPTION(BAD_REQUEST, "잘못된 요청입니다."),
    VALIDATION_ENUM_VALUE_EXCEPTION(BAD_REQUEST, "잘못된 Enum 값 입니다."),
    VALIDATION_REQUEST_MISSING_EXCEPTION(BAD_REQUEST, "필수적인 요청 값이 입력되지 않았습니다."),
    VALIDATION_WRONG_TYPE_EXCEPTION(BAD_REQUEST, "잘못된 타입이 입력되었습니다."),
    VALIDATION_SOCIAL_TYPE_EXCEPTION(BAD_REQUEST, "잘못된 소셜 프로바이더 입니다."),
    VALIDATION_SORT_TYPE_EXCEPTION(BAD_REQUEST, "허용하지 않는 정렬기준을 입력했습니다."),

    /**
     * 401 UnAuthorized
     */
    UNAUTHORIZED_EXCEPTION(UNAUTHORIZED, "토큰이 존재하지 않습니다. 다시 로그인 해주세요."),
    UNAUTHORIZED_INVALID_TOKEN_EXCEPTION(UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    UNAUTHORIZED_TOKEN_NOT_MATCH_WITH_SERVER_EXCEPTION(UNAUTHORIZED, "Request의 token과 Server의 token이 일치하지 않습니다."),
    UNAUTHORIZED_EXPIRE_TOKEN_EXCEPTION(UNAUTHORIZED, "토큰이 만료되었습니다."),
    UNAUTHORIZED_CRYPTOGRAM_NOT_MATCH_EXCEPTION(UNAUTHORIZED, "Cryptogram이 일치하지 않습니다."),
    UNAUTHORIZED_CRYPTOGRAM_EXPIRE_EXCEPTION(UNAUTHORIZED, "Cryptogram이 만료되었습니다."),

    /**
     * 402 DB Fail
     */
    DB_FAIL_EXCEPTION(DB_HANDLE_FAIL, "DB처리 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
    DB_FAIL_SIGN_UP_FAIL_EXCEPTION(DB_HANDLE_FAIL, "회원가입 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
    DB_FAIL_CREATE_TOUR_CATEGORY_FAIL_EXCEPTION(DB_HANDLE_FAIL, "투어 카테고리 생성과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
    DB_FAIL_CREATE_TOUR_FAIL_EXCEPTION(DB_HANDLE_FAIL, "투어 생성과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
    DB_FAIL_UPDATE_TOUR_FAIL_EXCEPTION(DB_HANDLE_FAIL, "투어 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
    DB_FAIL_CREATE_TOUR_APPLICATION_FAIL_EXCEPTION(DB_HANDLE_FAIL, "투어 신청서 생성 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
    DB_FAIL_UPDATE_TOUR_APPLICATION_FAIL_EXCEPTION(DB_HANDLE_FAIL, "투어 신청서 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
    DB_FAIL_UPDATE_USER_FAIL_EXCEPTION(DB_HANDLE_FAIL, "유저 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
//    DB_FAIL_CRYPTOGRAM_CREATE_FAIL_EXCEPTION(DB_HANDLE_FAIL, "Cryptogram 생성 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
//    DB_FAIL_CRYPTOGRAM_UPDATE_FAIL_EXCEPTION(DB_HANDLE_FAIL, "Cryptogram 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),

    /**
     * 403 Forbidden
     */
    FORBIDDEN_EXCEPTION(FORBIDDEN, "허용하지 않는 요청입니다."),
    FORBIDDEN_FILE_TYPE_EXCEPTION(BAD_REQUEST, "허용되지 않은 파일 형식입니다."),
    FORBIDDEN_FILE_NAME_EXCEPTION(BAD_REQUEST, "허용되지 않은 파일 이름입니다."),

    /**
     * 404 Not Found
     */
    NOT_FOUND_EXCEPTION(NOT_FOUND, "존재하지 않습니다."),
    NOT_FOUND_USER_EXCEPTION(NOT_FOUND, "탈퇴했거나 존재하지 않는 유저입니다."),
    NOT_FOUND_REFRESH_TOKEN_EXCEPTION(NOT_FOUND, "만료된 리프레시 토큰입니다."),
    NOT_FOUND_WRONG_PASSWORD_EXCEPTION(NOT_FOUND, "잘못된 비밀번호 입니다."),
    NOT_FOUND_TOKEN_EXCEPTION(NOT_FOUND, "Server에 저장된 token이 없습니다. 회원가입을 먼저 진행해주세요."),
    NOT_FOUND_CRYPTOGRAM_EXCEPTION(NOT_FOUND, "요청된 이메일 요청이 없습니다. 이메일 인증을 요청해주세요."),
    NOT_FOUND_TOUR_EXCEPTION(NOT_FOUND, "해당 투어를 찾을 수 없습니다."),
    NOT_FOUND_TOUR_APPLICATION_EXCEPTION(NOT_FOUND, "해당 투어 신청서를 찾을 수 없습니다."),

    /**
     * 405 Method Not Allowed
     */
    METHOD_NOT_ALLOWED_EXCEPTION(METHOD_NOT_ALLOWED, "지원하지 않는 메소드 입니다."),

    /**
     * 406 Not Acceptable
     */
    NOT_ACCEPTABLE_EXCEPTION(NOT_ACCEPTABLE, "Not Acceptable"),

    /**
     * 409 Conflict
     */
    CONFLICT_EMAIL_EXCEPTION(CONFLICT, "이미 존재하는 이메일 입니다."),
    CONFLICT_USER_EXCEPTION(CONFLICT, "이미 해당 계정으로 회원가입하셨습니다.\n로그인 해주세요."),

    /**
     * 415 Unsupported Media Type
     */
    UNSUPPORTED_MEDIA_TYPE_EXCEPTION(UNSUPPORTED_MEDIA_TYPE, "해당하는 미디어 타입을 지원하지 않습니다."),

    /**
     * 500 Internal Server Exception
     */
    INTERNAL_SERVER_EXCEPTION(INTERNAL_SERVER, "예상치 못한 서버 에러가 발생하였습니다."),

    /**
     * 502 Bad Gateway
     */
    BAD_GATEWAY_EXCEPTION(BAD_GATEWAY, "일시적인 에러가 발생하였습니다.\n잠시 후 다시 시도해주세요!"),

    /**
     * 503 Service UnAvailable
     */
    SERVICE_UNAVAILABLE_EXCEPTION(SERVICE_UNAVAILABLE, "현재 점검 중입니다.\n잠시 후 다시 시도해주세요!"),
    ;

    private final ErrorStatusCode statusCode;
    private final String message;

    public int getStatus() {
        return statusCode.getStatus();
    }
}
