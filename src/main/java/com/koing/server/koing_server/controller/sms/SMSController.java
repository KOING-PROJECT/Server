package com.koing.server.koing_server.controller.sms;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.service.cryptogram.dto.CryptogramVerifyDto;
import com.koing.server.koing_server.service.sign.dto.SignUpSetCreateDto;
import com.koing.server.koing_server.service.sms.SMSService;
import com.koing.server.koing_server.service.sms.dto.SMSRequestDto;
import com.koing.server.koing_server.service.sms.dto.SMSVerifyDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Api(tags = "SMS")
@RequestMapping("/sms")
@RestController
@RequiredArgsConstructor
public class SMSController {

    private final Logger LOGGER = LoggerFactory.getLogger(SMSController.class);
    private final SMSService smsService;

    @ApiOperation("SMS : 회원가입 인증번호를 전송합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "SMS : 회원가입 인증번호 전송 성공"),
            @ApiResponse(code = 416, message = "해당하는 인코딩을 지원하지 않습니다."),
            @ApiResponse(code = 417, message = "해당하는 알고리즘이 없습니다."),
            @ApiResponse(code = 418, message = "유효하지 않은 키 입니다."),
            @ApiResponse(code = 419, message = "Json 파일 프로세싱 과정에서 오류가 발생했습니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
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
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[SMSController] 회원가입 인증번호 전송 성공");

        return smsSendResponse;
    }

    @ApiOperation("SMS : 인증번호를 확인합니다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SMS : 인증번호 확인 성공"),
            @ApiResponse(code = 401, message = "인증번호가 일치하지 않습니다."),
            @ApiResponse(code = 401, message = "인증번호가 만료되었습니다. 휴대폰 인증을 다시 요청해주세요."),
            @ApiResponse(code = 402, message = "문자 인증 과정에서 오류가 발생했습니다."),
            @ApiResponse(code = 404, message = "해당 번호의 문자인증을 찾을 수 없습니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
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
