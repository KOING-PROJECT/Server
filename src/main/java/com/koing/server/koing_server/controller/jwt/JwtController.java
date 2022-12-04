package com.koing.server.koing_server.controller.jwt;

import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.service.jwt.JwtService;
import com.koing.server.koing_server.service.jwt.dto.JwtDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Jwt")
@RequestMapping("/jwt")
@RestController
@RequiredArgsConstructor

public class JwtController {

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(JwtController.class);
    private final JwtService jwtService;

    @ApiOperation("Jwt : Jwt 토큰을 관리합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ReIssue : Access token 재발급 성공"),
            @ApiResponse(code = 400, message = "Server에 저장된 token이 없습니다. 회원가입을 먼저 진행해주세요."),
            @ApiResponse(code = 401, message = "Request의 token과 Server의 token이 일치하지 않습니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping("/reIssue")
    public SuperResponse signUp(@RequestBody JwtDto jwtDto) {
        LOGGER.info("[JwtController] token 재발급 시도");
        SuperResponse reIssueResponse = jwtService.reIssueAccessToken(jwtDto);
        LOGGER.info("[JwtController] token 재발급 성공");

        return reIssueResponse;
    }

}

