package com.koing.server.koing_server.service.sign;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.exception.ErrorCode;
import com.koing.server.koing_server.common.exception.NotFoundException;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.common.util.JwtTokenUtil;
import com.koing.server.koing_server.domain.jwt.JwtToken;
import com.koing.server.koing_server.domain.jwt.repository.JwtTokenRepository;
import com.koing.server.koing_server.domain.jwt.repository.JwtTokenRepositoryImpl;
import com.koing.server.koing_server.domain.user.GenderType;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.domain.user.repository.UserRepository;
import com.koing.server.koing_server.domain.user.repository.UserRepositoryImpl;
import com.koing.server.koing_server.service.jwt.dto.JwtDto;
import com.koing.server.koing_server.service.jwt.dto.JwtResponseDto;
import com.koing.server.koing_server.service.sign.dto.SignInRequestDto;
import com.koing.server.koing_server.service.sign.dto.SignUpRequestDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class SignService {

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(SignService.class);

    private final UserRepository userRepository;
    private final UserRepositoryImpl userRepositoryImpl;
    private final JwtTokenRepository jwtTokenRepository;
    private final JwtTokenRepositoryImpl jwtTokenRepositoryImpl;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    public SuperResponse signUp(SignUpRequestDto signUpRequestDto) {

        LOGGER.info("[signUp] 회원가입 요청");

        String role = "ROLE_" + signUpRequestDto.getRole().toUpperCase(Locale.ROOT);
        GenderType genderType = GenderType.UNKNOWN;
        String email = signUpRequestDto.getEmail();

        if (userRepositoryImpl.isExistUserByUserEmail(email)) {
            // 서버쪽에서 email 중복 검사를 한번 더 실행
            return ErrorResponse.error(ErrorCode.CONFLICT_EMAIL_EXCEPTION);
        }

        if (signUpRequestDto.getGender().equalsIgnoreCase("MAN")) {
            genderType = GenderType.Man;
        }
        else if (signUpRequestDto.getGender().equalsIgnoreCase("WOMAN")) {
            genderType = GenderType.WOMAN;
        }

        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .name(signUpRequestDto.getName())
                .phoneNumber(signUpRequestDto.getPhoneNumber())
                .birthDate(signUpRequestDto.getBirthDate())
                .country(signUpRequestDto.getCountry())
                .roles(Collections.singletonList(role))
                .gender(genderType)
                .age(signUpRequestDto.getAge())
                .enabled(true)
                .userOptionalInfo(null)
                .build();

        User savedUser = userRepository.save(user);
        LOGGER.info("[signUp] 회원가입 완료");

        JwtToken jwtToken = JwtToken.InitBuilder()
                .userEmail(email)
                .build();

        JwtToken savedJwtToken = jwtTokenRepository.save(jwtToken);
        LOGGER.info("[signUp] JwtToken init 완료");

        if (!savedUser.getEmail().isEmpty() && !savedJwtToken.getUserEmail().isEmpty()) {
            LOGGER.info("[signUp] 회원가입 정상 처리완료");
            return SuccessResponse.success(SuccessCode.SIGN_UP_SUCCESS, null);
        }

        LOGGER.info("[signUp] 회원가입 실패");
        return ErrorResponse.error(ErrorCode.SIGN_UP_FAIL_EXCEPTION);
    }

    public SuperResponse signUpEmailCheck(String email) {
        if (userRepositoryImpl.isExistUserByUserEmail(email)) {
            return SuccessResponse.success(SuccessCode.SIGN_UP_EMAIL_CHECK_SUCCESS, null);
        }

        return ErrorResponse.error(ErrorCode.CONFLICT_EMAIL_EXCEPTION);
    }

    public SuperResponse signIn(SignInRequestDto signInRequestDto) {

        String userEmail = signInRequestDto.getEmail();

        LOGGER.info("[signIn] 로그인 요청");

        User user = userRepositoryImpl.loadUserByUserEmail(userEmail, true);

        if (user == null) {
            return ErrorResponse.error(ErrorCode.NOT_FOUND_EXCEPTION);
        }
        LOGGER.info(String.format("[signIn] Email = %s", user.getEmail()));

        if (!passwordEncoder.matches(signInRequestDto.getPassword(), user.getPassword())) {
            LOGGER.info("[signIn] Password 오류");
            return ErrorResponse.error(ErrorCode.NOT_FOUND_WRONG_PASSWORD_EXCEPTION);
        }
        LOGGER.info("[signIn] Password 일치");

        String accessToken = jwtTokenUtil.createJwtToken(userEmail, user.getRoles());
        String refreshToken = jwtTokenUtil.createJwtRefreshToken();
        LOGGER.info(String.format("[signIn] JWT 토큰 생성 성공, Token = %s", accessToken));

        JwtToken jwtToken;
        try {
            // 재로그인 시
            jwtToken = jwtTokenRepositoryImpl.findJwtTokenByUserEmail(userEmail);
        } catch (NotFoundException e) {
            // 처음 로그인 할 때, signUp하면서 만들긴 하지만 한번 더 예외처리
            JwtToken initJwtToken = JwtToken.InitBuilder().userEmail(userEmail).build();
            jwtToken = jwtTokenRepository.save(initJwtToken);
        }
        jwtToken.setAccessToken(accessToken);
        jwtToken.setRefreshToken(refreshToken);

        JwtToken savedJwtToken = jwtTokenRepository.save(jwtToken);
        LOGGER.info(String.format("[signIn] JWT 토큰 업데이트 성공, jwtToken = %s", savedJwtToken));

        JwtDto jwtDto = JwtDto.builder().accessToken(accessToken).refreshToken(refreshToken).build();

        return SuccessResponse.success(SuccessCode.LOGIN_SUCCESS, new JwtResponseDto(jwtDto));
    }



}
