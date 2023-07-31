package com.koing.server.koing_server.controller.sign;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.service.sign.SignService;
import com.koing.server.koing_server.service.sign.dto.SignInRequestDto;
import com.koing.server.koing_server.service.sign.dto.SignUpEmailCheckDto;
import com.koing.server.koing_server.service.sign.dto.SignUpRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Sign", description = "Sign API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/sign")
public class SignController {

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(SignController.class);
    private final SignService signService;

    @Operation(description = "signUp : 회원가입을 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "sign-up : 회원가입 성공"),
            @ApiResponse(responseCode = "402", description = "회원가입 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 페이지 입니다."),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 이메일 입니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping("/sign-up")
    public SuperResponse signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        LOGGER.info("[SignController] 회원가입 시도");
        SuperResponse signUpResponse;
        try {
            signUpResponse = signService.signUp(signUpRequestDto);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info(String.format("[SignController] 회원가입 성공, Email = %s , Name = %s", signUpRequestDto.getEmail(), signUpRequestDto.getName()));

        return signUpResponse;
    }


    @Operation(description = "signUp/email-check : 이메일 중복검사를 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "sign-in : 이메일 중복없음 확인"),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 이메일 입니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping("/sign-up/email-check")
    public SuperResponse signUpEmailCheck(@RequestBody SignUpEmailCheckDto signUpEmailCheckDto) {
        LOGGER.info("[SignController] 이메일 중복 검사");

        SuperResponse signUpEmailCheckResponse = signService.signUpEmailCheck(signUpEmailCheckDto);

        if (signUpEmailCheckResponse.getStatus() == 202) {
            LOGGER.info(String.format("[SignController] 사용가능한 이메일"));
        }

        return signUpEmailCheckResponse;
    }


    @Operation(description = "signIn : 로그인을 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "sign-in : 로그인 성공"),
            @ApiResponse(responseCode = "404", description = "잘못된 비밀번호 입니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping("/sign-in")
    public SuperResponse signIn(@RequestBody SignInRequestDto signInRequestDto) {
        LOGGER.info("[SignController] 로그인 시도");

        SuperResponse signInResponse = signService.signIn(signInRequestDto);

        if (signInResponse.getStatus() == 200) {
            LOGGER.info(String.format("[SignController] 로그인 성공, token = %s", signInResponse.getData().toString()));
        }

        return signInResponse;
    }
}
