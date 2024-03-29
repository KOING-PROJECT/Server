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
    UNAUTHORIZED_CRYPTOGRAM_EXPIRE_EXCEPTION(UNAUTHORIZED, "Cryptogram이 만료되었습니다. 이메일 인증을 다시 요청해주세요."),
    UNAUTHORIZED_CERTIFICATION_NUMBER_EXCEPTION(UNAUTHORIZED, "인증번호가 일치하지 않습니다."),
    UNAUTHORIZED_CERTIFICATION_NUMBER_EXPIRE_EXCEPTION(UNAUTHORIZED, "인증번호가 만료되었습니다. 휴대폰 인증을 다시 요청해주세요."),
    UNAUTHORIZED_REFRESH_TOKEN_SERIALIZATION_EXCEPTION(UNAUTHORIZED, "RefreshToken을 json으로 직렬화하는 데에 에러가 발생했습니다."),
    UNAUTHORIZED_INVALID_SECRET_KEY_EXCEPTION(UNAUTHORIZED, "유효하지 않은 secret key 입니다."),
    UNAUTHORIZED_INVALID_FORM_EXCEPTION(UNAUTHORIZED, "유효하지 않은 form 입니다."),
    UNAUTHORIZED_INVALID_EXPIRED_EXCEPTION(UNAUTHORIZED, "유효기간이 만료된 토큰 입니다."),

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
    DB_FAIL_CREATE_TOUR_DETAIL_SCHEDULE_FAIL_EXCEPTION(DB_HANDLE_FAIL, "투어 세부 스케줄 생성 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
    DB_FAIL_CREATE_TOUR_SCHEDULE_FAIL_EXCEPTION(DB_HANDLE_FAIL, "투어 스케줄 생성 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
    DB_FAIL_UPDATE_TOUR_SCHEDULE_FAIL_EXCEPTION(DB_HANDLE_FAIL, "투어 스케줄 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
    DB_FAIL_DELETE_TOUR_APPLICATION_FAIL_EXCEPTION(DB_HANDLE_FAIL, "투어 신청서 삭제 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
    DB_FAIL_CREATE_USER_OPTIONAL_INFO_FAIL_EXCEPTION(DB_HANDLE_FAIL, "유저 선택정보 생성 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
    DB_FAIL_UPDATE_USER_OPTIONAL_INFO_FAIL_EXCEPTION(DB_HANDLE_FAIL, "유저 선택정보 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
    DB_FAIL_CREATE_TOUR_SURVEY_FAIL_EXCEPTION(DB_HANDLE_FAIL, "투어 설문 생성 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
    DB_FAIL_UPDATE_TOUR_SURVEY_FAIL_EXCEPTION(DB_HANDLE_FAIL, "투어 설문 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
    DB_FAIL_PRESS_LIKE_TOUR_FAIL_EXCEPTION(DB_HANDLE_FAIL, "좋아요 처리과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
    DB_FAIL_PRESS_FOLLOW_USER_FAIL_EXCEPTION(DB_HANDLE_FAIL, "팔로우 처리과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
    DB_FAIL_CREATE_CHAT_ROOM_FAIL_EXCEPTION(DB_HANDLE_FAIL, "채팅방 생성 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
    DB_FAIL_CREATE_CHAT_MESSAGE_FAIL_EXCEPTION(DB_HANDLE_FAIL, "메시지 저장 과정에서 오류가 발생했습니다."),
    DB_FAIL_UPLOAD_IMAGE_FAIL_EXCEPTION(DB_HANDLE_FAIL, "이미지 저장 과정에서 오류가 발생했습니다."),
    DB_FAIL_CREATE_REVIEW_TO_GUIDE_FAIL_EXCEPTION(DB_HANDLE_FAIL, "ReviewToGuide 저장과정에서 오류가 발생했습니다."),
    DB_FAIL_CREATE_REVIEW_TO_TOURIST_FAIL_EXCEPTION(DB_HANDLE_FAIL, "ReviewToTourist 저장과정에서 오류가 발생했습니다."),
    DB_FAIL_COMPLETE_TOUR_FAIL_EXCEPTION(DB_HANDLE_FAIL, "투어 완성과정에서 오류가 발생했습니다."),
    DB_FAIL_COMPLETE_TOUR_SCHEDULE_FAIL_EXCEPTION(DB_HANDLE_FAIL, "투어 스케줄 완성과정에서 오류가 발생했습니다."),
    DB_FAIL_COMPLETE_TOUR_SURVEY_FAIL_EXCEPTION(DB_HANDLE_FAIL, "투어 설문 완성과정에서 오류가 발생했습니다."),
    DB_FAIL_VERIFY_CRYPTOGRAM_FAIL_EXCEPTION(DB_HANDLE_FAIL, "Cryptogram 인증과정에서 오류가 발생했습니다."),
    DB_FAIL_UPDATE_TOUR_PARTICIPANT_FAIL_EXCEPTION(DB_HANDLE_FAIL, "투어 신청 내용 업데이트 과정에서 오류가 발생했습니다."),
    DB_FAIL_CREATE_SMS_FAIL_EXCEPTION(DB_HANDLE_FAIL, "문자 인증을 생성하는 과정에서 오류가 발생했습니다."),
    DB_FAIL_UPDATE_SMS_FAIL_EXCEPTION(DB_HANDLE_FAIL, "문자 인증을 업데이트하는 과정에서 오류가 발생했습니다."),
    DB_FAIL_VERIFY_SMS_FAIL_EXCEPTION(DB_HANDLE_FAIL, "문자 인증 과정에서 오류가 발생했습니다."),
    DB_FAIL_CREATE_THUMBNAIL_EXCEPTION(DB_HANDLE_FAIL, "썸네일 저장 과정에서 오류가 발생했습니다."),
    DB_FAIL_PRESS_START_EXCEPTION(DB_HANDLE_FAIL, "투어 시작 처리 과정에서 오류가 발생했습니다."),
    DB_FAIL_START_TOUR_EXCEPTION(DB_HANDLE_FAIL, "투어 시작 과정에서 오류가 발생했습니다."),
    DB_FAIL_END_TOUR_EXCEPTION(DB_HANDLE_FAIL, "투어 종료 과정에서 오류가 발생했습니다."),
    DB_FAIL_PRESS_END_EXCEPTION(DB_HANDLE_FAIL, "투어 종료 처리 과정에서 오류가 발생했습니다."),
    DB_FAIL_CREATE_FCM_TOKEN_EXCEPTION(DB_HANDLE_FAIL, "FCM 토큰 생성 과정에서 오류가 발생했습니다."),
    DB_FAIL_UPDATE_FCM_TOKEN_EXCEPTION(DB_HANDLE_FAIL, "FCM 토큰 업데이트 과정에서 오류가 발생했습니다."),
    DB_FAIL_UPDATE_USER_CATEGORY_INDEXES_EXCEPTION(DB_HANDLE_FAIL, "유저 선호 카테고리 업데이트 과정에서 오류가 발생했습니다."),
    DB_FAIL_APPROVAL_TOUR_EXCEPTION(DB_HANDLE_FAIL, "투어 승인 과정에서 오류가 발생했습니다."),
    DB_FAIL_REJECTION_TOUR_EXCEPTION(DB_HANDLE_FAIL, "투어 승인 거절 과정에서 오류가 발생했습니다."),
    DB_FAIL_RECRUITMENT_TOUR_EXCEPTION(DB_HANDLE_FAIL, "투어 모집 시작 과정에서 오류가 발생했습니다."),
    DB_FAIL_DE_ACTIVATE_TOUR_EXCEPTION(DB_HANDLE_FAIL, "투어 비활성화 과정에서 오류가 발생했습니다."),
    DB_FAIL_ACTIVATE_TOUR_EXCEPTION(DB_HANDLE_FAIL, "투어 활성화 과정에서 오류가 발생했습니다."),
    DB_FAIL_DE_ACTIVATE_TOUR_APPLICATION_EXCEPTION(DB_HANDLE_FAIL, "투어 신청서 비활성화 과정에서 오류가 발생했습니다."),
    DB_FAIL_ACTIVATE_TOUR_APPLICATION_EXCEPTION(DB_HANDLE_FAIL, "투어 신청서 활성화 과정에서 오류가 발생했습니다."),
    DB_FAIL_CREATE_TOUR_PARTICIPANT_EXCEPTION(DB_HANDLE_FAIL, "투어 신청 내역 생성 과정에서 오류가 발생했습니다."),
    DB_FAIL_WITHDRAWAL_USER_EXCEPTION(DB_HANDLE_FAIL, "유저 탈퇴 과정에서 오류가 발생했습니다."),
    DB_FAIL_REQUEST_WITHDRAWAL_USER_EXCEPTION(DB_HANDLE_FAIL, "유저 탈퇴 요청 과정에서 오류가 발생했습니다."),
    DB_FAIL_UPDATE_TEMPORARY_PASSWORD_EXCEPTION(DB_HANDLE_FAIL, "임시 비밀번호 발급 과정에서 오류가 발생했습니다."),
    DB_FAIL_UPDATE_PASSWORD_EXCEPTION(DB_HANDLE_FAIL, "비밀번호 변경 과정에서 오류가 발생했습니다."),
    DB_FAIL_CREATE_ACCOUNT_EXCEPTION(DB_HANDLE_FAIL, "계좌 정보 생성 과정에서 오류가 발생했습니다."),
    DB_FAIL_UPDATE_ACCOUNT_EXCEPTION(DB_HANDLE_FAIL, "계좌 정보 업데이트 과정에서 오류가 발생했습니다."),
    DB_FAIL_FINISH_TOUR_EXCEPTION(DB_HANDLE_FAIL, "투어 종료 과정에서 오류가 발생했습니다."),
    DB_FAIL_CREATE_PAYMENT_EXCEPTION(DB_HANDLE_FAIL, "결제 정보 생성 과정에서 오류가 발생했습니다."),
    DB_FAIL_CREATE_KEYWORD_EXCEPTION(DB_HANDLE_FAIL, "키워드 생성 과정에서 오류가 발생했습니다."),
    DB_FAIL_UPDATE_KEYWORD_EXCEPTION(DB_HANDLE_FAIL, "키워드 업데이트 과정에서 오류가 발생했습니다."),
    DB_FAIL_CREATE_SURVEY_EXCEPTION(DB_HANDLE_FAIL, "설문 생성 과정에서 오류가 발생했습니다."),
    DB_FAIL_CREATE_POST_PHOTO_EXCEPTION(DB_HANDLE_FAIL, "게시글 사진 저장 과정에서 오류가 발생했습니다."),
    DB_FAIL_CREATE_POST_EXCEPTION(DB_HANDLE_FAIL, "게시글 저장 과정에서 오류가 발생했습니다."),
    DB_FAIL_UPDATE_POST_EXCEPTION(DB_HANDLE_FAIL, "게시글 업데이트 과정에서 오류가 발생했습니다."),
    DB_FAIL_CREATE_COMMENT_EXCEPTION(DB_HANDLE_FAIL, "댓글 저장 과정에서 오류가 발생했습니다."),

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
    NOT_FOUND_CRYPTOGRAM_EXCEPTION(NOT_FOUND, "해당 이메일에 Cryptogram이 없습니다. 이메일 인증을 요청해주세요."),
    NOT_FOUND_TOUR_EXCEPTION(NOT_FOUND, "해당 투어를 찾을 수 없습니다."),
    NOT_FOUND_TOUR_APPLICATION_EXCEPTION(NOT_FOUND, "해당 투어 신청서를 찾을 수 없습니다."),
    NOT_FOUND_TOUR_SCHEDULE_EXCEPTION(NOT_FOUND, "해당 투어 스케줄을 찾을 수 없습니다."),
    NOT_FOUND_TOUR_SURVEY_EXCEPTION(NOT_FOUND, "해당 투어 설문을 찾을 수 없습니다."),
    NOT_FOUND_CHAT_ROOM_EXCEPTION(NOT_FOUND, "해당 채팅방을 찾을 수 없습니다."),
    NOT_FOUND_TOUR_PARTICIPANT_EXCEPTION(NOT_FOUND, "해당 투어 신청 내용을 찾을 수 없습니다."),
    NOT_FOUND_TEMPORARY_TOUR_EXCEPTION(NOT_FOUND, "이어서 만들 투어를 찾을 수 없습니다."),
    NOT_FOUND_USER_OPTIONAL_INFO_EXCEPTION(NOT_FOUND, "해당 유저의 선택사항을 찾을 수 없습니다."),
    NOT_FOUND_SMS_EXCEPTION(NOT_FOUND, "해당 번호의 문자인증을 찾을 수 없습니다."),
    NOT_FOUND_THUMBNAIL_EXCEPTION(NOT_FOUND, "해당 썸네일을 찾을 수 없습니다."),
    NOT_FOUND_TOUR_CATEGORY_EXCEPTION(NOT_FOUND, "존재하지 않는 투어 카테고리 입니다."),
    NOT_FOUND_FCM_TOKEN_EXCEPTION(NOT_FOUND, "해당 유저와 장치의 FCM 토큰을 찾을 수 없습니다."),
    NOT_FOUND_USER_CATEGORY_INDEXES_EXCEPTION(NOT_FOUND, "해당 유저의 선호 카테고리를 찾을 수 없습니다."),
    NOT_FOUND_USER_ACCOUNT_EXCEPTION(NOT_FOUND, "해당 유저의 계좌 정보를 찾을 수 없습니다."),
    NOT_FOUND_KEYWORD_EXCEPTION(NOT_FOUND, "존재하지 않는 키워드 입니다."),
    NOT_FOUND_POST_PHOTO_EXCEPTION(NOT_FOUND, "해당 게시글 사진을 찾을 수 없습니다."),
    NOT_FOUND_POST_EXCEPTION(NOT_FOUND, "해당 게시글을 찾을 수 없습니다."),
    NOT_FOUND_COMMENT_EXCEPTION(NOT_FOUND, "해당 댓글을 찾을 수 없습니다."),
    NOT_FOUND_PAYMENT_INFO_EXCEPTION(NOT_FOUND, "해당 결제 정보를 찾을 수 없습니다."),


    /**
     * 405 Method Not Allowed
     */
    METHOD_NOT_ALLOWED_EXCEPTION(METHOD_NOT_ALLOWED, "지원하지 않는 메소드 입니다."),

    /**
     * 406 Not Acceptable
     */
    NOT_ACCEPTABLE_EXCEPTION(NOT_ACCEPTABLE, "Not Acceptable"),
    NOT_ACCEPTABLE_ALREADY_SOLD_OUT_EXCEPTION(NOT_ACCEPTABLE, "이미 매진 된 투어 입니다."),
    NOT_ACCEPTABLE_DURING_PAYMENT_EXCEPTION(NOT_ACCEPTABLE, "다른 이용자가 결제 중인 투어 입니다."),
    NOT_ACCEPTABLE_OVER_MAX_PARTICIPANTS_EXCEPTION(NOT_ACCEPTABLE, "해당 투어의 정원을 초과했습니다."),
    NOT_ACCEPTABLE_NOT_TOUR_CREATOR_EXCEPTION(NOT_ACCEPTABLE, "해당 투어의 생성자가 아닙니다."),
    NOT_ACCEPTABLE_TOUR_APPLICATION_HAVE_PARTICIPANT_EXCEPTION(NOT_ACCEPTABLE, "신청자가 있는 투어는 날짜 변경이 불가능 합니다."),
    NOT_ACCEPTABLE_USER_NOT_HAVE_ROLE_EXCEPTION(NOT_ACCEPTABLE, "해당 유저의 ROLE이 없습니다."),
    NOT_ACCEPTABLE_ALREADY_PARTICIPATED_TOUR_EXCEPTION(NOT_ACCEPTABLE, "이미 신청한 투어입니다."),
    NOT_ACCEPTABLE_NOT_GUIDE_EXCEPTION(NOT_ACCEPTABLE, "해당 유저는 가이드가 아니므로 팔로우 할 수 없습니다."),
    NOT_ACCEPTABLE_CAN_NOT_COGNITION_ORDER_EXCEPTION(NOT_ACCEPTABLE, "해당 사진의 순서를 알 수 없습니다."),
    NOT_ACCEPTABLE_ALREADY_PRESS_START_EXCEPTION(NOT_ACCEPTABLE, "이미 시작을 누르신 투어입니다."),
    NOT_ACCEPTABLE_ALREADY_EXCEED_TOUR_APPLICATION_EXCEPTION(NOT_ACCEPTABLE, "이미 신청한 팀이 있는 투어입니다."),
    NOT_ACCEPTABLE_ALREADY_PRESS_END_EXCEPTION(NOT_ACCEPTABLE, "이미 종료를 누르신 투어입니다."),
    NOT_ACCEPTABLE_TOUR_DATE_CLOSE_EXCEPTION(NOT_ACCEPTABLE, "투어 취소는 투어 당일 기준 이틀 전까지 가능합니다."),
//    NOT_ACCEPTABLE_WRONG_PASSWORD_EXCEPTION(NOT_ACCEPTABLE, "비밀번호가 틀렸습니다."),
    NOT_ACCEPTABLE_WRONG_PASSWORD_EXCEPTION(NOT_ACCEPTABLE, "Wrong password"),

    NOT_ACCEPTABLE_SAME_ACCOUNT_EXCEPTION(NOT_ACCEPTABLE, "수정할 계좌 정보가 없습니다."),
    NOT_ACCEPTABLE_ACCOUNT_ALREADY_EXIST_EXCEPTION(NOT_ACCEPTABLE, "해당 유저의 계좌 정보가 이미 존재합니다."),
    NOT_ACCEPTABLE_ALREADY_EXIST_KEYWORD_EXCEPTION(NOT_ACCEPTABLE, "이미 존재하는 키워드 입니다."),
    NOT_ACCEPTABLE_TOUR_NOT_APPROVAL_EXCEPTION(NOT_ACCEPTABLE, "승인되지 않은 투어는 시작할 수 없습니다."),
    NOT_ACCEPTABLE_NOT_POSTED_USER_EXCEPTION(NOT_ACCEPTABLE, "게시글을 작성한 유저가 아닙니다."),
    NOT_ACCEPTABLE_NOT_COMMENTED_USER_EXCEPTION(NOT_ACCEPTABLE, "댓글을 단 유저가 아닙니다."),

    /**
     * 407 PAYMENT_SERVER_ERROR
     */
    PAYMENT_SERVER_CONNECT_ERROR(PAYMENT_SERVER_ERROR, "결제 서버와의 통신에서 오류가 발생했습니다."),
    PAYMENT_CANCEL_WHILE_PAYMENT_ERROR(PAYMENT_SERVER_ERROR, "결제 도중에 결제가 취소되었습니다."),

    /**
     * 408 FILE_CONVERT_ERROR
     */
    FILE_CONVERT_ERROR(FILE_CONVERTED_ERROR, "파일 변환과정에서 오류가 발생했습니다."),

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
     * 416 Unsupported Encoding
     */
    UNSUPPORTED_ENCODING_EXCEPTION(UNSUPPORTED_ENCODING, "해당하는 인코딩을 지원하지 않습니다."),

    /**
     * 417 No Such Algorithm
     */
    NO_SUCH_ALGORITHM_EXCEPTION(NO_SUCH_ALGORITHM, "해당하는 알고리즘이 없습니다."),

    /**
     * 418 Invalid Key
     */
    INVALID_KEY_EXCEPTION(INVALID_KEY, "유효하지 않은 키 입니다."),

    /**
     * 419 Json processing
     */
    JSON_PROCESSING_EXCEPTION(JSON_PROCESSING, "Json 파일 프로세싱 과정에서 오류가 발생했습니다."),

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
