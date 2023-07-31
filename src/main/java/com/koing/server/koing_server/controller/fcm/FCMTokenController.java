package com.koing.server.koing_server.controller.fcm;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.service.fcm.FCMTokenService;
import com.koing.server.koing_server.service.fcm.dto.FCMTokenCreateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@Tag(name = "FCM", description = "FCM API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/fcm")
public class FCMTokenController {

    private final Logger LOGGER = LoggerFactory.getLogger(FCMTokenController.class);
    private final FCMTokenService fcmTokenService;

    @Operation(description = "FCM - FCM 토큰을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "FCM - FCM 토큰 생성 성공"),
            @ApiResponse(responseCode = "402", description = "FCM 토큰 생성 과정에서 오류가 발생했습니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping("")
    public SuperResponse createFCMToken(@RequestBody FCMTokenCreateDto fcmTokenCreateDto) {
        LOGGER.info("[FCMTokenController] FCM 토큰 생성 시도");

        SuperResponse fcmTokenCreateResponse;
        try {
            fcmTokenCreateResponse = fcmTokenService.createFCMToken(fcmTokenCreateDto);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }

        LOGGER.info("[FCMTokenController] FCM 토큰 생성 성공");

        return fcmTokenCreateResponse;
    }

    @Operation(description = "FCM - FCM 토큰을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "FCM - FCM 토큰 조회 성공"),
            @ApiResponse(responseCode = "404", description = "해당 유저와 장치의 FCM 토큰을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @GetMapping("/{userId}")
    public SuperResponse getFCMToken(
            @PathVariable("userId") Long userId
    ) {
        LOGGER.info("[FCMTokenController] FCM 토큰 조회 시도");

        SuperResponse fcmTokenGetResponse;
        try {
            fcmTokenGetResponse = fcmTokenService.getFCMToken(userId);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }

        LOGGER.info("[FCMTokenController] FCM 토큰 조회 성공");

        return fcmTokenGetResponse;
    }


    @Operation(description = "FCM - FCM 토큰을 업데이트 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "FCM - FCM 토큰 업데이트 성공"),
            @ApiResponse(responseCode = "402", description = "FCM 토큰 업데이트 과정에서 오류가 발생했습니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PatchMapping("/{userId}")
    public SuperResponse updateFCMToken(
            @RequestBody FCMTokenCreateDto fcmTokenCreateDto
    ) {
        LOGGER.info("[FCMTokenController] FCM 토큰 업데이트 시도");

        SuperResponse fcmTokenUpdateResponse;
        try {
            fcmTokenUpdateResponse = fcmTokenService.updateFCMToken(fcmTokenCreateDto);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }

        LOGGER.info("[FCMTokenController] FCM 토큰 업데이트 성공");

        return fcmTokenUpdateResponse;
    }

}
