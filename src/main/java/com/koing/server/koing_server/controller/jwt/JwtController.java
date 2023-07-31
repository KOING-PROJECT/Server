package com.koing.server.koing_server.controller.jwt;

import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.service.jwt.JwtService;
import com.koing.server.koing_server.service.jwt.dto.JwtDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Jwt", description = "Jwt API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/jwt")
public class JwtController {

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(JwtController.class);
    private final JwtService jwtService;

    @Operation(description = "Jwt : Jwt 토큰을 관리합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ReIssue : Access token 재발급 성공"),
            @ApiResponse(responseCode = "400", description = "Server에 저장된 token이 없습니다. 회원가입을 먼저 진행해주세요."),
            @ApiResponse(responseCode = "401", description = "Request의 token과 Server의 token이 일치하지 않습니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping("/reIssue")
    public SuperResponse reIssueAccessToken(@RequestBody JwtDto jwtDto) {
        LOGGER.info("[JwtController] token 재발급 시도");
        SuperResponse reIssueResponse = jwtService.reIssueAccessToken(jwtDto);
        LOGGER.info("[JwtController] token 재발급 성공");

        return reIssueResponse;
    }

}

