package com.koing.server.koing_server.controller.sms;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.service.sms.SMSService;
import com.koing.server.koing_server.service.sms.dto.SMSRequestDto;
import com.koing.server.koing_server.service.sms.dto.SMSVerifyDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Tag(name = "SMS", description = "SMS API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/sms")
public class SMSController {

    private final Logger LOGGER = LoggerFactory.getLogger(SMSController.class);
    private final SMSService smsService;

    @Operation(description = "SMS : 회원가입 인증번호를 전송합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "SMS : 회원가입 인증번호 전송 성공"),
            @ApiResponse(responseCode = "416", description = "해당하는 인코딩을 지원하지 않습니다."),
            @ApiResponse(responseCode = "417", description = "해당하는 알고리즘이 없습니다."),
            @ApiResponse(responseCode = "418", description = "유효하지 않은 키 입니다."),
            @ApiResponse(responseCode = "419", description = "Json 파일 프로세싱 과정에서 오류가 발생했습니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping("")
    public SuperResponse sendSMS(@RequestBody SMSRequestDto smsRequestDto) {
        LOGGER.info("[SMSController] 회원가입 인증번호 전송 시도");

        SuperResponse smsSendResponse;

        try {
            smsSendResponse = smsService.sendSMS(smsRequestDto);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            return ErrorResponse.error(ErrorCode.UNSUPPORTED_ENCODING_EXCEPTION);
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            return ErrorResponse.error(ErrorCode.NO_SUCH_ALGORITHM_EXCEPTION);
        } catch (InvalidKeyException invalidKeyException) {
            return ErrorResponse.error(ErrorCode.INVALID_KEY_EXCEPTION);
        } catch (JsonProcessingException jsonProcessingException) {
            return ErrorResponse.error(ErrorCode.JSON_PROCESSING_EXCEPTION);
        } catch (Exception exception) {
            System.out.println(exception);
            System.out.println(exception.getMessage());
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[SMSController] 회원가입 인증번호 전송 성공");

        return smsSendResponse;
    }

    @Operation(description = "SMS : 인증번호를 확인합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SMS : 인증번호 확인 성공"),
            @ApiResponse(responseCode = "401", description = "인증번호가 일치하지 않습니다."),
            @ApiResponse(responseCode = "401", description = "인증번호가 만료되었습니다. 휴대폰 인증을 다시 요청해주세요."),
            @ApiResponse(responseCode = "402", description = "문자 인증 과정에서 오류가 발생했습니다."),
            @ApiResponse(responseCode = "404", description = "해당 번호의 문자인증을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping("/verify")
    public SuperResponse verifySMS(@RequestBody SMSVerifyDto smsVerifyDto) {
        LOGGER.info("[SMSController] 인증번호 인증 시도");

        SuperResponse verifySMSResponse;

        try {
            verifySMSResponse = smsService.verifySMS(smsVerifyDto);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[SMSController] 인증번호 인증 성공");

        return verifySMSResponse;
    }

}
