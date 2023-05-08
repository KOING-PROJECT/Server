package com.koing.server.koing_server.common.success;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.koing.server.koing_server.common.success.SuccessStatusCode.*;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SuccessCode {

    /**
     * 200 OK
     */
    SUCCESS(OK, "성공입니다."),
    GET_USER_SUCCESS(OK, "user : 유저 조회 성공입니다."),
    GET_USERS_SUCCESS(OK, "user : 유저 리스트 조회 성공입니다."),
    GET_GUIDES_SUCCESS(OK, "user : 가이드 리스트 조회 성공입니다."),
    GET_TOURS_SUCCESS(OK, "tour : 투어 리스트 조회 성공입니다."),
    DELETE_TOUR_SUCCESS(OK, "tour : 투어 삭제 성공입니다."),
    GET_TOUR_CATEGORIES_SUCCESS(OK, "tour : 투어 카테고리 리스트 조회 성공입니다."),
    GET_TOUR_APPLICATIONS_SUCCESS(OK, "tour-application : 투어 신청서 리스트 조회 성공입니다."),
    GET_TOUR_DETAIL_INFO_SUCCESS(OK, "tour-detail-info : 투어 세부 정보 조회 성공입니다."),
    GET_TOUR_GUIDE_DETAIL_INFO_SUCCESS(OK, "tour-detail-info : 투어 Guide 세부 정보 조회 성공입니다."),
    GET_USER_DETAIL_INFO_FROM_MY_PAGE_SUCCESS(OK, "tour-detail-info : 마이페이지에서 User 세부 정보 조회 성공입니다."),
    GET_LIKE_TOURS_SUCCESS(OK, "like-tours : 좋아요 누른 투어 조회 성공입니다."),
    GET_TOURIST_INFO_SUCCESS(OK, "my-page : Tourist 마이페이지 정보 조회 성공입니다."),
    GET_GUIDE_INFO_SUCCESS(OK, "my-page : Guide 마이페이지 정보 조회 성공입니다."),
    GET_IAMPORT_ACCESS_TOKEN_SUCCESS(OK, "Payment : Iamport AccessToken 가져오기 성공입니다."),
    GET_CHAT_ROOM_SUCCESS(OK, "chat-room : 채팅방 조회 성공입니다."),
    UPLOAD_S3_SUCCESS(OK, "s3 : 파일 업로드 성공입니다."),
    GET_TEMPORARY_TOUR_SUCCESS(OK, "tour : 이어서 만들 투어 조회 성공입니다."),
    GET_TOUR_PARTICIPANTS_SUCCESS(OK, "tour-application : 투어에 참가하는 투어리스트 리스트 조회 성공입니다."),
    GET_GUIDE_MY_PAGE_REVIEW_LIST_SUCCESS(OK, "review : 가이드의 리뷰 작성 리스트 조회 성공입니다."),
    GET_GUIDE_REVIEWS_SUCCESS(OK, "review : 가이드가 보낸 리뷰, 받은 리뷰 조회 성공입니다."),
    GET_TOURIST_REVIEWS_SUCCESS(OK, "review : 투어리스트가 보낸 리뷰, 받은 리뷰 조회 성공입니다."),
    GET_USER_OPTIONAL_INFO_SUCCESS(OK, "user : 유저 선택정보 조회 성공입니다."),
    SMS_SEND_SUCCESS(OK, "SMS : 회원가입 인증번호 전송 성공입니다."),
    PRESS_START_SUCCESS(OK, "tour-application : 투어 시작 처리 성공입니다."),
    PRESS_END_SUCCESS(OK, "tour-application : 투어 종료 처리 성공입니다."),
    START_TOUR_SUCCESS(OK, "tour-application : 투어 시작 성공입니다."),
    END_TOUR_SUCCESS(OK, "tour-application : 투어 종료 성공입니다."),
    GET_FCM_TOKEN_SUCCESS(OK, "fcm : FCM 토큰 조회 성공입니다."),
    GET_USER_CATEGORY_INDEXES_SUCCESS(OK, "user : 유저 선호 카테고리 조회 성공입니다."),
    CANCEL_TOUR_APPLICATION_SUCCESS(OK, "tour-application : 투어 신청 취소 성공입니다."),
    REQUEST_WITHDRAWAL_USER_SUCCESS(OK, "user : 유저 탈퇴 요청 성공입니다."),
    WITHDRAWAL_USER_SUCCESS(OK, "user : 유저 탈퇴 성공입니다."),
    CREATE_TEMPORARY_PASSWORD_SUCCESS(OK, "user : 임시 비밀번호 발급 성공입니다."),
    UPDATE_PASSWORD_SUCCESS(OK, "user : 비밀번호 변경 성공입니다."),
    GET_ACCOUNT_SUCCESS(OK, "account : 계좌 정보 조회 성공입니다."),
    GET_KEYWORD_SUCCESS(OK, "keyword : 키워드 조회 성공입니다."),
    DELETE_KEYWORD_SUCCESS(OK, "keyword : 키워드 삭제 성공입니다."),
    RECOMMEND_TOUR_SUCCESS(OK, "survey : 설문기반 투어 추천 성공입니다."),

    // 인증
//    LOGIN_SUCCESS(OK, "signIn : 로그인 성공입니다."),
    LOGIN_SUCCESS(OK, "Login Success"),
    RE_ISSUE_TOKEN_SUCCESS(OK, "reIssue : 토큰 재발급 성공입니다."),
    CRYPTOGRAM_CERTIFICATION_SUCCESS(OK, "cryptogram : 이메일 인증 성공입니다."),
    EMAIL_SEND_SUCCESS(OK, "eamil-send : Email send 성공입니다."),
    SMS_CERTIFICATION_SUCCESS(OK, "sms : 휴대폰 인증 성공입니다."),

    /**
     * 201 CREATED
     */
    SIGN_UP_SUCCESS(CREATED, "sign-up : 회원가입 성공입니다."),
    SIGN_UP_SET_SUCCESS(CREATED, "sign-up : 회원가입, 선택정보 생성 성공입니다."),
    CRYPTOGRAM_CREATE_SUCCESS(CREATED, "cryptogram-create : Cryptogram 생성 성공입니다."),
    CRYPTOGRAM_UPDATE_SUCCESS(CREATED, "cryptogram-update : Cryptogram 업데이트 성공입니다."),
    TOUR_CATEGORY_CREATE_SUCCESS(CREATED, "tour-category : 투어 카테고리 생성 성공입니다."),
    TOUR_CREATE_SUCCESS(CREATED, "tour : 투어 생성 성공입니다."),
    TOUR_UPDATE_SUCCESS(CREATED, "tour : 투어 update 성공입니다."),
    TOUR_APPROVAL_SUCCESS(CREATED, "tour : 투어 승인 성공입니다."),
    TOUR_REJECTION_SUCCESS(CREATED, "tour : 투어 승인 거절 성공입니다."),
    TOUR_RECRUITMENT_SUCCESS(CREATED, "tour : 투어 모집 시작 성공입니다."),
    TOUR_DE_ACTIVATE_SUCCESS(CREATED, "tour : 투어 비활성화 성공입니다."),
    TOUR_ACTIVATE_SUCCESS(CREATED, "tour : 투어 활성화 성공입니다."),
    TOUR_FINISH_SUCCESS(CREATED, "tour : 투어 종료 성공입니다."),
    TOUR_APPLICATION_CREATE_SUCCESS(CREATED, "tour-application : 투어 신청서 생성 성공입니다."),
    TOUR_APPLICATION_UPDATE_SUCCESS(CREATED, "tour-application : 투어 신청서 업데이트 성공입니다."),
    TOUR_SCHEDULE_CREATE_SUCCESS(CREATED, "tour-schedule : 투어 스케줄 생성 성공입니다."),
    TOUR_SCHEDULE_UPDATE_SUCCESS(CREATED, "tour-schedule : 투어 스케줄 update 성공입니다."),
    TOUR_SET_CREATE_SUCCESS(CREATED, "tour-set : 투어 set 생성 성공입니다."),
    TOUR_SET_UPDATE_SUCCESS(CREATED, "tour-set : 투어 set 업데이트 성공입니다."),
    TOUR_SET_COMPLETE_SUCCESS(CREATED, "tour-set : 투어 set 완성 성공입니다."),
    TEMPORARY_TOUR_SET_COMPLETE_SUCCESS(CREATED, "tour-set : 임시 저장한 투어 set complete 성공입니다."),
    TEMPORARY_TOUR_TOUR_SCHEDULE_TOUR_SURVEY_CREATE_SUCCESS(CREATED, "tour-set : 투어, 투어 스케줄, 투어 설문 임시 저장 성공입니다."),
    TEMPORARY_TOUR_TOUR_SCHEDULE_TOUR_SURVEY_UPDATE_SUCCESS(CREATED, "tour-set : 임시 저장한 투어, 투어 스케줄, 투어 설문 update 성공입니다."),
    TEMPORARY_TOUR_TOUR_SCHEDULE_CREATE_SUCCESS(CREATED, "tour-set : 투어, 투어 스케줄 임시 저장 성공입니다."),
    TEMPORARY_TOUR_TOUR_SCHEDULE_UPDATE_SUCCESS(CREATED, "tour-set : 임시 저장한 투어, 투어 스케줄 update 성공입니다."),
    USER_OPTIONAL_INFO_CREATE_SUCCESS(CREATED, "user-optional-info : 유저 선택정보 생성 성공입니다."),
    USER_OPTIONAL_INFO_UPDATE_SUCCESS(CREATED, "user-optional-info : 유저 선택정보 업데이트 성공입니다."),
    TOUR_SURVEY_CREATE_SUCCESS(CREATED, "tour-survey : 투어 설문 생성 성공입니다."),
    TOUR_SURVEY_UPDATE_SUCCESS(CREATED, "tour-survey : 투어 설문 업데이트 성공입니다."),
    PRESS_LIKE_TOUR_UPDATE_SUCCESS(CREATED, "follow : 유저 팔로우 업데이트 성공입니다."),
    CHAT_ROOM_CREATE_SUCCESS(CREATED, "chat-room : 채팅방 생성 성공입니다."),
    CHAT_MESSAGE_CREATE_SUCCESS(CREATED, "chat-message : 메시지 저장 성공입니다."),
    REVIEW_TO_GUIDE_CREATE_SUCCESS(CREATED, "review : ReviewToGuide 생성 성공입니다."),
    REVIEW_TO_TOURIST_CREATE_SUCCESS(CREATED, "review : ReviewToTourist 생성 성공입니다."),
    TOUR_COMPLETE_SUCCESS(OK, "tour : 투어 완성 성공입니다."),
    TOUR_SCHEDULE_COMPLETE_SUCCESS(OK, "tour-schedule : 투어 스케줄 완성 성공입니다."),
    TOUR_SURVEY_COMPLETE_SUCCESS(OK, "tour-survey : 투어 설문 완성 성공입니다."),
    FCM_TOKEN_CREATE_SUCCESS(OK, "fcm : FCM 토큰 생성 성공입니다."),
    FCM_TOKEN_UPDATE_SUCCESS(OK, "fcm : FCM 토큰 업데이트 성공입니다."),
    USER_CATEGORY_INDEXES_UPDATE_SUCCESS(OK, "user : 유저 선호 카테고리 업데이트 성공입니다."),
    ACCOUNT_CREATE_SUCCESS(OK, "account : 계좌 정보 생성 성공입니다."),
    ACCOUNT_UPDATE_SUCCESS(OK, "account : 계좌 정보 업데이트 성공입니다."),
    PAYMENT_CREATE_SUCCESS(OK, "payment : 결제 정보 생성 성공입니다."),
    KEYWORD_CREATE_SUCCESS(OK, "keyword : 키워드 생성 성공입니다."),
    KEYWORD_UPDATE_SUCCESS(OK, "keyword : 키워드 업데이트 성공입니다."),

    /**
     * 202 ACCEPTED
     */
    SIGN_UP_EMAIL_CHECK_SUCCESS(ACCEPTED, "sign-up/email-check : 사용가능한 이메일 입니다."),

    /**
     * 204 NO_CONTENT
     */

    /**
     * 205 SIMILAR_TOUR
     */
    SIMILAR_TOUR_RECOMMEND_SUCCESS(SIMILAR_TOUR, "survey : 비슷한 투어 추천 성공입니다.")
    ;

    private final SuccessStatusCode statusCode;
    private final String message;

    public int getStatus() {
        return statusCode.getStatus();
    }
}
