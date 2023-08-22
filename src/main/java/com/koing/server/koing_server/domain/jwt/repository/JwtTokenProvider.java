package com.koing.server.koing_server.domain.jwt.repository;

import java.time.ZonedDateTime;

public interface JwtTokenProvider {

//    String createAccessToken(Long memberId, Role role, ZonedDateTime accessTokenExpire);

    String createRefreshToken(Long userId);

//    LoginMemberInfo getLoginMemberByAccessToken(String accessToken);

    Long getUserIdByRefreshToken(String refreshToken);
}
