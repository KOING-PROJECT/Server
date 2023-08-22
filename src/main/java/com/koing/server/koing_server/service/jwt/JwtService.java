package com.koing.server.koing_server.service.jwt;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.NotFoundException;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.common.util.JwtTokenUtil;
import com.koing.server.koing_server.domain.jwt.JwtToken;
import com.koing.server.koing_server.domain.jwt.RefreshToken;
import com.koing.server.koing_server.domain.jwt.repository.JwtTokenRepository;
import com.koing.server.koing_server.domain.jwt.repository.JwtTokenRepositoryImpl;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.domain.user.repository.UserRepositoryImpl;
import com.koing.server.koing_server.service.jwt.dto.JwtDto;
import com.koing.server.koing_server.service.jwt.dto.JwtResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(JwtService.class);

    private final JwtTokenRepositoryImpl jwtTokenRepositoryImpl;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepositoryImpl userRepositoryImpl;
    private final JwtTokenRepository jwtTokenRepository;
    private final RefreshTokenManager refreshTokenManager;

    public JwtToken findJwtTokenByUserEmail(String userEmail) {
        JwtToken jwtToken = jwtTokenRepositoryImpl.findJwtTokenByUserEmail(userEmail);

        return jwtToken;
    }

    public SuperResponse reIssueAccessToken(JwtDto jwtDto) {
        // 특정 메시지 오류코드, 메시지(UNAUTHORIZED_EXPIRE_TOKEN_EXCEPTION(UNAUTHORIZED, "토큰이 만료되었습니다."))
        // 일경우만 이쪽으로 요청을 보냄

        LOGGER.info("[JwtService] 토큰 재발급 시작");

        String accessToken = jwtDto.getAccessToken();
        String refreshToken = jwtDto.getRefreshToken();
        LOGGER.info("[JwtService] accessToken = " + accessToken);
        LOGGER.info("[JwtService] refreshToken = " + refreshToken);

        String userEmail = jwtTokenUtil.getUserEmailFromJwt(accessToken);

        User user = userRepositoryImpl.loadUserByUserEmail(userEmail, true);

        if (user == null) {
            throw new NotFoundException("탈퇴했거나 존재하지 않는 유저입니다.", ErrorCode.NOT_FOUND_USER_EXCEPTION);
        }

        JwtToken serverJwtToken = findJwtTokenByUserEmail(userEmail);

        if (serverJwtToken == null) {
            LOGGER.info("[JwtService] 서버에 토큰이 없습니다. 회원가입을 먼저 진행해주세요.");
            return ErrorResponse.error(ErrorCode.NOT_FOUND_TOKEN_EXCEPTION);
        }

        LOGGER.info("[JwtService] 서버 토큰 가져오기 성공");
        String serverAccessToken = serverJwtToken.getAccessToken();

        LOGGER.info("[JwtService] serverAccessToken = " + serverAccessToken);

        LOGGER.info("[JwtService] 서버 토큰과 비교 시작");
        if (!serverAccessToken.equals(accessToken) || refreshTokenManager.checkExistRefreshToken(refreshToken, user.getId())) {
            LOGGER.info("[JwtService] 서버 토큰과 불일치!");
            return ErrorResponse.error(ErrorCode.UNAUTHORIZED_TOKEN_NOT_MATCH_WITH_SERVER_EXCEPTION);
        }

        LOGGER.info("[JwtService] 서버 토큰과 일치!");
        String newAccessToken = jwtTokenUtil.createJwtToken(user.getEmail(), user.getRoles(), user.getId());

        LOGGER.info("[JwtService] newAccessToken = " + newAccessToken);

        serverJwtToken.setAccessToken(newAccessToken);
        JwtToken updatedJwtToken = jwtTokenRepository.save(serverJwtToken);
        String newRefreshToken = refreshTokenManager.rotation(refreshToken).getRefreshToken();
        LOGGER.info("[JwtService] 토큰 재발급 성공");

        return SuccessResponse.success(SuccessCode.RE_ISSUE_TOKEN_SUCCESS,
                new JwtResponseDto(
                        JwtDto.builder()
                        .accessToken(updatedJwtToken.getAccessToken())
                        .refreshToken(newRefreshToken).build()
                ));
    }

}
