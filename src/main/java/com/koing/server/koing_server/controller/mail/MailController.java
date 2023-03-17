package com.koing.server.koing_server.controller.mail;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.service.mail.MailService;
import com.koing.server.koing_server.service.mail.dto.MailSendDto;
import com.koing.server.koing_server.service.sign.dto.SignInTemporaryPasswordDto;
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

@Api(tags = "Mail")
@RequestMapping("/mail")
@RestController
@RequiredArgsConstructor
public class MailController {

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(MailController.class);
    private final MailService mailService;

    @ApiOperation("MailSend : 인증메일을 보냅니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "mail-send : 인증메일 전송 성공"),
            @ApiResponse(code = 401, message = "DB처리 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping("")
    public SuperResponse sendMail(@RequestBody MailSendDto mailSendDto) {
        LOGGER.info("[MailController] 인증 이메일 전송 시도");
        SuperResponse sendMailResponse;

        try {
            sendMailResponse = mailService.sendMail(mailSendDto);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[MailController] 이메일 전송 성공");

        return sendMailResponse;
    }


    @ApiOperation("MailSend : 임시 비밀번호를 발급합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "mail-send : 임시 비밀번호 발급성공"),
            @ApiResponse(code = 401, message = "DB처리 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping("/temporary-password")
    public SuperResponse createTemporaryPasswordMail(@RequestBody SignInTemporaryPasswordDto signInTemporaryPasswordDto) {
        LOGGER.info("[MailController] 임시 비밀번호 발급 시도");
        SuperResponse createTemporaryPasswordResponse;

        try {
            createTemporaryPasswordResponse = mailService.sendTemporaryPassword(signInTemporaryPasswordDto);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[MailController] 임시 비밀번호 발급 성공");

        return createTemporaryPasswordResponse;
    }

}
