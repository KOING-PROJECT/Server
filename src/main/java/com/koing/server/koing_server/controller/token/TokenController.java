//package com.koing.server.koing_server.controller.token;
//
//import com.koing.server.koing_server.common.dto.SuperResponse;
//import com.koing.server.koing_server.controller.sign.SignController;
//import com.koing.server.koing_server.service.sign.dto.SignUpRequestDto;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@Api(tags = "Token")
//@RestController
//@RequiredArgsConstructor
//public class TokenController {
//
//    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(TokenController.class);
//
//    @ApiOperation("sign-up : 회원가입을 합니다.")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "sign-up : 회원가입 성공"),
//            @ApiResponse(code = 402, message = "회원가입 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
//            @ApiResponse(code = 404, message = "존재하지 않는 페이지 입니다."),
//            @ApiResponse(code = 409, message = "이미 존재하는 이메일 입니다."),
//            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
//    })
//    @PostMapping("/sign-up")
//    public SuperResponse signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
//        LOGGER.info("[SignController] 회원가입 시도");
//        SuperResponse signUpResponse = signService.signUp(signUpRequestDto);
//        LOGGER.info(String.format("[SignController] 회원가입 성공, Email = %s , Name = %s", signUpRequestDto.getEmail(), signUpRequestDto.getName()));
//
//        return signUpResponse;
//    }
//
//
//}
