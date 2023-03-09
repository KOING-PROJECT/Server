package com.koing.server.koing_server.controller.fcm;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.service.fcm.FCMTokenService;
import com.koing.server.koing_server.service.fcm.dto.FCMTokenCreateDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@Api(tags = "FCM")
@RequestMapping("/fcm")
@RestController
@RequiredArgsConstructor
public class FCMTokenController {

    private final Logger LOGGER = LoggerFactory.getLogger(FCMTokenController.class);
    private final FCMTokenService fcmTokenService;

    @ApiOperation("FCM - FCM 토큰을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "FCM - FCM 토큰 생성 성공"),
            @ApiResponse(code = 402, message = "FCM 토큰 생성 과정에서 오류가 발생했습니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
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

    @ApiOperation("FCM - FCM 토큰을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "FCM - FCM 토큰 조회 성공"),
            @ApiResponse(code = 404, message = "해당 유저와 장치의 FCM 토큰을 찾을 수 없습니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
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


    @ApiOperation("FCM - FCM 토큰을 업데이트 합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "FCM - FCM 토큰 업데이트 성공"),
            @ApiResponse(code = 402, message = "FCM 토큰 업데이트 과정에서 오류가 발생했습니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
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
