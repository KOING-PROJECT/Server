package com.koing.server.koing_server.controller.cryptogram;


import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.controller.mail.MailController;
import com.koing.server.koing_server.service.cryptogram.CryptogramService;
import com.koing.server.koing_server.service.cryptogram.dto.CryptogramVerifyDto;
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

@Tag(name = "Cryptogram", description = "Cryptogram API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/cryptogram")
public class CryptogramController {

    private final Logger LOGGER = LoggerFactory.getLogger(MailController.class);
    private final CryptogramService cryptogramService;

    @Operation(description = "Cryptogram : 인증번호를 확인합니다")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cryptogram : 인증번호 확인 성공"),
            @ApiResponse(responseCode = "401", description = "Cryptogram이 일치하지 않습니다."),
            @ApiResponse(responseCode = "401", description = "Cryptogram이 만료되었습니다. 이메일 인증을 다시 요청해주세요."),
            @ApiResponse(responseCode = "402", description = "Cryptogram 인증과정에서 오류가 발생했습니다."),
            @ApiResponse(responseCode = "404", description = "해당 이메일에 Cryptogram이 없습니다. 이메일 인증을 요청해주세요."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping("/verify")
    public SuperResponse verifyCryptogram(@RequestBody CryptogramVerifyDto cryptogramVerifyDto) {
        LOGGER.info("[CryptogramController] 인증번호 확인 시도");

        SuperResponse verifyCryptogramResponse;

        try {
            verifyCryptogramResponse = cryptogramService.verifyCryptogram(cryptogramVerifyDto);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[CryptogramController] 인증번호 확인 성공");

        return verifyCryptogramResponse;
    }

}
