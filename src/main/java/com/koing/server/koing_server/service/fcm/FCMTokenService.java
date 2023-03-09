package com.koing.server.koing_server.service.fcm;

import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.DBFailException;
import com.koing.server.koing_server.common.exception.NotFoundException;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.domain.fcm.FCMToken;
import com.koing.server.koing_server.domain.fcm.repository.FCMTokenRepository;
import com.koing.server.koing_server.domain.fcm.repository.FCMTokenRepositoryImpl;
import com.koing.server.koing_server.service.fcm.dto.FCMTokenCreateDto;
import com.koing.server.koing_server.service.fcm.dto.FCMTokenRequestDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FCMTokenService {

    private final Logger LOGGER = LoggerFactory.getLogger(FCMTokenService.class);
    private final FCMTokenRepository fcmTokenRepository;
    private final FCMTokenRepositoryImpl fcmTokenRepositoryImpl;

    public SuperResponse createFCMToken(FCMTokenCreateDto fcmTokenCreateDto) {
        LOGGER.info("[FCMTokenService] FCM Token 생성 시도");

        FCMToken fcmToken = FCMToken.builder()
                .userId(fcmTokenCreateDto.getUserId())
                .fcmToken(fcmTokenCreateDto.getFcmToken())
                .build();

        FCMToken savedFCMToken = fcmTokenRepository.save(fcmToken);

        if (savedFCMToken == null) {
            throw new DBFailException("FCM 토큰 생성 과정에서 오류가 발생했습니다.", ErrorCode.DB_FAIL_CREATE_FCM_TOKEN_EXCEPTION);
        }
        LOGGER.info("[FCMTokenService] FCM Token 생성 성공");

        return SuccessResponse.success(SuccessCode.FCM_TOKEN_CREATE_SUCCESS, fcmToken.getFcmToken());
    }

    public SuperResponse getFCMToken(Long userId) {
        LOGGER.info("[FCMTokenService] FCM Token 조회 시도");

        FCMToken fcmToken = getFCMTokenAtDB(userId);

        LOGGER.info("[FCMTokenService] FCM Token 조회 성공");

        return SuccessResponse.success(SuccessCode.GET_FCM_TOKEN_SUCCESS, fcmToken);
    }

    public SuperResponse updateFCMToken(FCMTokenCreateDto fcmTokenCreateDto) {
        LOGGER.info("[FCMTokenService] FCM Token 업데이트 시도");

        FCMToken fcmToken = getFCMTokenAtDB(fcmTokenCreateDto.getUserId());

        LOGGER.info("[FCMTokenService] FCM Token 조회 성공");

        String beforeFCMToken = fcmToken.getFcmToken();

        fcmToken.setFcmToken(fcmTokenCreateDto.getFcmToken());

        FCMToken updatedFCMToken = fcmTokenRepository.save(fcmToken);

        if (beforeFCMToken.equals(updatedFCMToken.getFcmToken())) {
            throw new DBFailException("FCM 토큰 업데이트 과정에서 오류가 발생했습니다.", ErrorCode.DB_FAIL_UPDATE_FCM_TOKEN_EXCEPTION);
        }

        return SuccessResponse.success(SuccessCode.FCM_TOKEN_UPDATE_SUCCESS, updatedFCMToken.getFcmToken());
    }

    private FCMToken getFCMTokenAtDB(Long userId) {
        FCMToken fcmToken = fcmTokenRepositoryImpl
                .findFCMTokenByUserIdAndDeviceId(
                        userId
                );

        if (fcmToken == null) {
            throw new NotFoundException("해당 유저와 장치의 FCM 토큰을 찾을 수 없습니다.", ErrorCode.NOT_FOUND_FCM_TOKEN_EXCEPTION);
        }

        return fcmToken;
    }

}
