package com.koing.server.koing_server.common.util;

import com.koing.server.koing_server.common.model.JwtValidateEnum;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.service.user.UserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtRefreshTokenUtil {

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(JwtRefreshTokenUtil.class);
    private final UserService userService;

//    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000L * 60 * 60 * 6; // 1000 ms = 1초, 1시간 * 6
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000L * 60 * 60 * 48; // 1시간 * 48 = 2일 / refresh token


    @Value("${springboot.jwt.secret}")
    private String secretKey = "BaseSecretKey"; // springboot.jwt.secret에서 key를 가져오지 못하면 기본키 적용

    @PostConstruct
    void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String createJwtRefreshToken(String email, List<String> roles) {
        LOGGER.info("[init] JwtRefreshTokenUtil createJwtRefreshToken Refresh토큰 생성 시작");

        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles", roles);
        Date now = new Date();

        Key SECRET_KEY = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        String jwtToken = Jwts.builder()
                .setClaims(claims) // 데이터
                .setIssuedAt(now) // 토큰 발행일자
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_TIME)) // 토큰 만료시간
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // 암호화
                .compact();

        LOGGER.info("[init] JwtTokenUtil createJwtToken 토큰 생성 완료");

        return jwtToken;
    }

    public Authentication getAuthentication(String token) {
        LOGGER.info("[init] JwtTokenUtil getAuthentication 토큰 인증 정보 조회 시작");
        User user = userService.loadUserByUserEmail(getUserEmail(token));

        LOGGER.info(String.format("[init] JwtTokenUtil getAuthentication 토큰 인증 정보 조회 완료, User email = %s", getUserEmail(token)));

        return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
    }

    public String getTokenInHeader(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public JwtValidateEnum validationToken(String token, HttpServletRequest httpServletRequest) {
        LOGGER.info("[init] JwtTokenUtil validationToken 토큰 유효성 체크 시작");
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);

//            return !claimsJws.getBody().getExpiration().before(new Date());
            return JwtValidateEnum.ACCESS;
        } catch (ExpiredJwtException e) {
            LOGGER.info("[init] JwtTokenUtil validationToken 토큰 만료!");
            return JwtValidateEnum.EXPIRE;
        } catch (JwtException | IllegalArgumentException e) {
            LOGGER.info("[init] JwtTokenUtil validationToken 토큰 유효성 체크 예외 발생");
            return JwtValidateEnum.DENIED;
        }
    }

    public String getUserEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

}
