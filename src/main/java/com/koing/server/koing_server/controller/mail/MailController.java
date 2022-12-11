package com.koing.server.koing_server.controller.mail;

import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.controller.sign.SignController;
import com.koing.server.koing_server.service.mail.MailService;
import com.koing.server.koing_server.service.mail.dto.MailSendDto;
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
        LOGGER.info("[MailController] 인증 이메일 전송");
        SuperResponse sendMailResponse = mailService.sendMail(mailSendDto);

        if (sendMailResponse.getStatus() == 200) {
            LOGGER.info("[MailController] 인증 이메일 실패");
        }

        return sendMailResponse;
    }

}
