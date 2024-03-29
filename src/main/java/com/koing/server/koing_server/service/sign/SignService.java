package com.koing.server.koing_server.service.sign;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.enums.GuideGrade;
import com.koing.server.koing_server.common.enums.TouristGrade;
import com.koing.server.koing_server.common.enums.UserRole;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.ConflictEmailException;
import com.koing.server.koing_server.common.exception.DBFailException;
import com.koing.server.koing_server.common.exception.NotFoundException;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.common.util.JwtTokenUtil;
import com.koing.server.koing_server.domain.jwt.JwtToken;
import com.koing.server.koing_server.domain.jwt.RefreshToken;
import com.koing.server.koing_server.domain.jwt.repository.JwtTokenProvider;
import com.koing.server.koing_server.domain.jwt.repository.JwtTokenRepository;
import com.koing.server.koing_server.domain.jwt.repository.JwtTokenRepositoryImpl;
import com.koing.server.koing_server.domain.jwt.repository.RefreshTokenRepository;
import com.koing.server.koing_server.domain.user.GenderType;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.domain.user.repository.UserRepository;
import com.koing.server.koing_server.domain.user.repository.UserRepositoryImpl;
import com.koing.server.koing_server.service.jwt.RefreshTokenManager;
import com.koing.server.koing_server.service.jwt.dto.JwtDto;
import com.koing.server.koing_server.service.jwt.dto.JwtResponseDto;
import com.koing.server.koing_server.service.sign.dto.SignInRequestDto;
import com.koing.server.koing_server.service.sign.dto.SignUpEmailCheckDto;
import com.koing.server.koing_server.service.sign.dto.SignUpRequestDto;
import com.koing.server.koing_server.service.sign.dto.SignInTemporaryPasswordDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    private final RefreshTokenManager refreshTokenManager;

    @Transactional
    public SuperResponse signUp(SignUpRequestDto signUpRequestDto) {

        LOGGER.info("[signUp] 회원가입 요청");

        String role = "ROLE_" + signUpRequestDto.getRole().toUpperCase(Locale.ROOT);
        String email = signUpRequestDto.getEmail();

        Set<String> roles = new HashSet<>();
        roles.add(role);

        if (userRepositoryImpl.isExistUserByUserEmail(email)) {
            // 서버쪽에서 email 중복 검사를 한번 더 실행
            throw new ConflictEmailException("이미 존재하는 이메일 입니다.", ErrorCode.CONFLICT_EMAIL_EXCEPTION);
        }

        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .name(signUpRequestDto.getName())
                .phoneNumber(signUpRequestDto.getPhoneNumber())
                .country(signUpRequestDto.getCountry())
                .roles(roles)
                .enabled(true)
                .attachment(37.0)
                .userOptionalInfo(null)
                .tourParticipants(new HashSet<>())
                .createTours(new HashSet<>())
                .pressLikeTours(new HashSet<>())
                .following(new HashSet<>())
                .follower(new HashSet<>())
                .categoryIndexes(new HashSet<>())
                .createPosts(new HashSet<>())
                .likePosts(new HashSet<>())
                .createComments(new HashSet<>())
                .build();

        if (role.equalsIgnoreCase(UserRole.ROLE_GUIDE.getRole())) {
            user.setGuideGrade(GuideGrade.BEGINNER);
        } else {
            user.setTouristGrade(TouristGrade.BEGINNER);
        }

        User savedUser = userRepository.save(user);
        LOGGER.info("[signUp] 회원가입 완료");

        JwtToken jwtToken = JwtToken.InitBuilder()
                .userEmail(email)
                .build();

        JwtToken savedJwtToken = jwtTokenRepository.save(jwtToken);
        LOGGER.info("[signUp] JwtToken init 완료");

        if (savedUser == null || savedJwtToken.getUserEmail().isEmpty()) {
            LOGGER.info("[signUp] 회원가입 실패");
            throw new DBFailException("회원가입에 실패하였습니다.", ErrorCode.DB_FAIL_SIGN_UP_FAIL_EXCEPTION);
        }

        LOGGER.info("[signUp] 회원가입 정상 처리완료");
        return SuccessResponse.success(SuccessCode.SIGN_UP_SUCCESS, savedUser.getId());
    }

    public SuperResponse signUpEmailCheck(SignUpEmailCheckDto signUpEmailCheckDto) {
        if (userRepositoryImpl.isExistUserByUserEmail(signUpEmailCheckDto.getEmail())) {
            return ErrorResponse.error(ErrorCode.CONFLICT_EMAIL_EXCEPTION);
        }

        return SuccessResponse.success(SuccessCode.SIGN_UP_EMAIL_CHECK_SUCCESS, null);
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

        String accessToken = jwtTokenUtil.createJwtToken(userEmail, user.getRoles(), user.getId());
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

        JwtToken savedJwtToken = jwtTokenRepository.save(jwtToken);
        RefreshToken refreshToken = refreshTokenManager.create(user.getId());
        LOGGER.info(String.format("[signIn] JWT 토큰 업데이트 성공, jwtToken = %s", savedJwtToken));

        JwtDto jwtDto = JwtDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();

        return SuccessResponse.success(SuccessCode.LOGIN_SUCCESS, new JwtResponseDto(jwtDto));
    }

    @Transactional
    public String createTemporaryPassword(SignInTemporaryPasswordDto signInTemporaryPasswordDto) {
        LOGGER.info("[UserService] 임시 비밀번호 발급 시도");

        User user = getUserByUserEmail(signInTemporaryPasswordDto.getUserEmail());

        String newPassword = newPassword();

        user.setPassword(passwordEncoder.encode(newPassword));

        User updatedUser = userRepository.save(user);

        if (!passwordEncoder.matches(newPassword, updatedUser.getPassword())) {
            throw new DBFailException("임시 비밀번호 발급 과정에서 오류가 발생했습니다.", ErrorCode.DB_FAIL_UPDATE_TEMPORARY_PASSWORD_EXCEPTION);
        }

        LOGGER.info("[UserService] 임시 비밀번호 발급 성공");

        return newPassword;
    }

    private String newPassword() {
        // 8자리 문자
        int leftLimit = 33; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 8;
        Random random = new Random();

        String newPassword = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString().toUpperCase();

        return newPassword;
    }

    private User getUserByUserEmail(String userEmail) {
        User user = userRepositoryImpl.loadUserByUserEmail(userEmail, true);
        if (user == null) {
            throw new NotFoundException("해당 유저를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_USER_EXCEPTION);
        }

        return user;
    }

}
