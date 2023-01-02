//package com.koing.server.koing_server.service.sign;
//
//import com.koing.server.koing_server.common.dto.SuperResponse;
//import com.koing.server.koing_server.service.sign.dto.SignUpRequestDto;
//import com.koing.server.koing_server.service.sign.dto.SignUpSetCreateDto;
//import com.koing.server.koing_server.service.user.UserOptionalInfoService;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@RequiredArgsConstructor
//public class SignUpSetService {
//
//    private final Logger LOGGER = LoggerFactory.getLogger(SignUpSetService.class);
//    private final SignService signService;
//    private final UserOptionalInfoService userOptionalInfoService;
//
//    @Transactional
//    public SuperResponse signUpSet(SignUpSetCreateDto signUpSetCreateDto) {
//
//        LOGGER.info("[signUp] 회원가입 set 요청");
//
//        SignUpRequestDto signUpRequestDto = new SignUpRequestDto(signUpSetCreateDto);
//        LOGGER.info("[signUp] singUpDto 생성 성공");
//
//        signUpRequestDto.
//
//    }
//
//}
