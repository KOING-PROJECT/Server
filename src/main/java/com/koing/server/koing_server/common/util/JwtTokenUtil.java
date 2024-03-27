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
import java.util.*;


@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(JwtTokenUtil.class);
    private final UserService userService;

//    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000L * 60 * 60 * 6; // 1000 ms = 1초, 1시간 * 6
//    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000L; // 1000 ms = 1초, 1시간 * 24 = 1일, 1일 * 3정

    @Value("${springboot.jwt.expire-time}")
    private String accessTokenExpireTime = "1000";

    @Value("${springboot.jwt.secret}")
    private String secretKey = "BaseSecretKey"; // springboot.jwt.secret에서 key를 가져오지 못하면 기본키 적용

    private Key SECRET_KEY;

    @PostConstruct
    void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
        SECRET_KEY = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String createJwtToken(String email, Set<String> roles, Long userId) {
        LOGGER.info("[init] JwtTokenUtil access 토큰 생성 시작");

        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles", roles);
        claims.put("userId", userId);
        Date now = new Date();

//        Key SECRET_KEY = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        long tokenExpireTime = Long.parseLong(accessTokenExpireTime);

        String jwtToken = Jwts.builder()
                .setClaims(claims) // 데이터
                .setIssuedAt(now) // 토큰 발행일자
                .setExpiration(new Date(now.getTime() + tokenExpireTime)) // 토큰 만료시간
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // 암호화
                .compact();

        LOGGER.info("[init] JwtTokenUtil access 토큰 생성 완료");

        return jwtToken;
    }

//    public String createJwtRefreshToken() {
//        LOGGER.info("[init] JwtTokenUtil Refresh 토큰 생성 시작");
//
//        Date now = new Date();
//
////        Key SECRET_KEY = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
//
//        String jwtRefreshToken = Jwts.builder()
//                .setIssuedAt(now) // 토큰 발행일자
//                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_TIME)) // 토큰 만료시간
//                .signWith(SECRET_KEY, SignatureAlgorithm.HS256) // 암호화
//                .compact();
//
//        LOGGER.info("[init] JwtTokenUtil RefreshToken 토큰 생성 완료");
//
//        return jwtRefreshToken;
//    }

    public Authentication getAuthentication(String token) {
        LOGGER.info("[init] JwtTokenUtil getAuthentication 토큰 인증 정보 조회 시작");
//        User user = userService.loadUserByUserEmail(getUserEmail(token));
        User user = userService.loadUserByUserEmail(getUserEmailFromJwt(token));

//        LOGGER.info(String.format("[init] JwtTokenUtil getAuthentication 토큰 인증 정보 조회 완료, User email = %s", getUserEmail(token)));
        LOGGER.info(String.format("[init] JwtTokenUtil getAuthentication 토큰 인증 정보 조회 완료, User email = %s", getUserEmailFromJwt(token)));

        return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
    }

    public String getTokenInHeader(HttpServletRequest request) {
        String tokenHeader = request.getHeader("Authorization");

        if (tokenHeader == null) {
            return null;
        }

        String[] token = tokenHeader.split(" ");

        if (token.length > 1) {
            return token[1];
        }
        else {
            return token[0];
        }
    }

    public Boolean validationToken(String token, HttpServletRequest httpServletRequest) {
        LOGGER.info("[init] JwtTokenUtil validationToken 토큰 유효성 체크 시작");
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);

            return !claimsJws.getBody().getExpiration().before(new Date());
//            return JwtValidateEnum.ACCESS;
        } catch (ExpiredJwtException e) {
            LOGGER.info("[init] JwtTokenUtil validationToken 토큰 만료!");
            httpServletRequest.setAttribute("JwtTokenException", JwtValidateEnum.EXPIRE);
            return false;
        } catch (JwtException | IllegalArgumentException e) {
            LOGGER.info("[init] JwtTokenUtil validationToken 토큰 유효성 체크 예외 발생");
            httpServletRequest.setAttribute("JwtTokenException", JwtValidateEnum.DENIED);
            return false;
        }
    }

//    public String getUserEmail(String token) {
//        return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody().getSubject();
//    }

    // 추가
    public String getUserEmailFromJwt(String accessToken) {
        return parseClaims(accessToken).getSubject();
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

}
