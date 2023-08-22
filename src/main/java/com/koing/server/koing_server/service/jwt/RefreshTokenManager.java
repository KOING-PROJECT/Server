package com.koing.server.koing_server.service.jwt;

import com.koing.server.koing_server.common.exception.NotFoundException;
import com.koing.server.koing_server.domain.jwt.RefreshToken;
import com.koing.server.koing_server.domain.jwt.repository.JwtTokenProvider;
import com.koing.server.koing_server.domain.jwt.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RefreshTokenManager {

    private static final int MAX_REFRESH_TOKEN_COUNT_PER_MEMBER = 3;

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000L * 60 * 60 * 24 * 7; // 1시간 * 24 * 7 = 7일 / refresh token

    public RefreshTokenManager(
            final JwtTokenProvider jwtTokenProvider,
            final RefreshTokenRepository refreshTokenRepository
    ) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken create(final Long userId) {
        final ZonedDateTime expireDateTime = ZonedDateTime.now()
                .plus(REFRESH_TOKEN_EXPIRE_TIME, ChronoUnit.MILLIS);
        final RefreshToken refreshToken = createRefreshToken(userId, expireDateTime);

        final List<RefreshToken> refreshTokens = refreshTokenRepository.findByUserId(userId);
        if (isMaxLoginCountPerMember(refreshTokens)) {
            refreshTokenRepository.removeOldRefreshToken(userId);
        }
        refreshTokenRepository.save(refreshToken);

        return refreshToken;
    }

    private boolean isMaxLoginCountPerMember(final List<RefreshToken> refreshTokens) {
        return refreshTokens.size() >= MAX_REFRESH_TOKEN_COUNT_PER_MEMBER;
    }

    public RefreshToken rotation(final String refreshTokenValue) {
        final ZonedDateTime expireDateTime = ZonedDateTime.now()
                .plus(REFRESH_TOKEN_EXPIRE_TIME, ChronoUnit.MILLIS);
        final Long userId = jwtTokenProvider.getUserIdByRefreshToken(refreshTokenValue);
        final RefreshToken refreshToken = findRefreshToken(refreshTokenValue, userId);
        refreshTokenRepository.removeRefreshToken(userId, refreshToken.getId());

        return createNewRefreshToken(userId, expireDateTime);
    }

    private RefreshToken findRefreshToken(final String refreshTokenValue, final Long userId) {
        final List<RefreshToken> memberRefreshTokens = refreshTokenRepository.findByUserId(userId);
        return memberRefreshTokens.stream()
                .filter(it -> it.getRefreshToken().equals(refreshTokenValue))
                .findAny()
//                .orElseThrow(() -> new NotLoginMemberException("무효화된 리프레시 토큰입니다. 다시 로그인 해주세요."));
                .orElseThrow(() -> new NotFoundException("무효화된 리프레시 토큰입니다. 다시 로그인 해주세요."));
    }

    public boolean checkExistRefreshToken(final String userRefreshTokenValue, final Long userId) {
        final List<RefreshToken> memberRefreshTokens = refreshTokenRepository.findByUserId(userId);
        return memberRefreshTokens.stream()
                .filter(it -> it.isNotExpired() && it.getRefreshToken().equals(userRefreshTokenValue))
                .findAny()
                .isEmpty();
    }

    private RefreshToken createNewRefreshToken(final Long userId, final ZonedDateTime expire) {
        final RefreshToken newRefreshToken = createRefreshToken(userId, expire);

        return refreshTokenRepository.save(newRefreshToken);
    }

    private RefreshToken createRefreshToken(final Long userId, final ZonedDateTime expire) {
        final String refreshTokenValue = jwtTokenProvider.createRefreshToken(userId);
        return new RefreshToken(refreshTokenValue, userId, expire);
    }
}
