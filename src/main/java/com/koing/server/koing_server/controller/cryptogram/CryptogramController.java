package com.koing.server.koing_server.controller.cryptogram;


import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.controller.mail.MailController;
import com.koing.server.koing_server.service.cryptogram.CryptogramService;
import com.koing.server.koing_server.service.cryptogram.dto.CryptogramVerifyDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Cryptogram")
@RequestMapping("/cryptogram")
@RestController
@RequiredArgsConstructor
public class CryptogramController {

    private final Logger LOGGER = LoggerFactory.getLogger(MailController.class);
    private final CryptogramService cryptogramService;

    @ApiOperation("Cryptogram : 인증번호를 확인합니다")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cryptogram : 인증번호 확인 성공"),
            @ApiResponse(code = 401, message = "Cryptogram이 일치하지 않습니다."),
            @ApiResponse(code = 401, message = "Cryptogram이 만료되었습니다. 이메일 인증을 다시 요청해주세요."),
            @ApiResponse(code = 402, message = "Cryptogram 인증과정에서 오류가 발생했습니다."),
            @ApiResponse(code = 404, message = "해당 이메일에 Cryptogram이 없습니다. 이메일 인증을 요청해주세요."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
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
