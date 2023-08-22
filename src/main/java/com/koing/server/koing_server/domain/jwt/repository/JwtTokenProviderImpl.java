package com.koing.server.koing_server.domain.jwt.repository;

import com.koing.server.koing_server.common.enums.UserRole;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.JwtInvalidExpiredException;
import com.koing.server.koing_server.common.exception.JwtInvalidFormException;
import com.koing.server.koing_server.common.exception.JwtInvalidSecretKeyException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenProviderImpl implements JwtTokenProvider {

//    private static final String ACCESS_TOKEN_SUBJECT = "koing access token";
    private static final String USER_ID_KEY_NAME = "userId";
//    private static final String ROLE_KEY_NAME = "role";
    private static final String REFRESH_TOKEN_SUBJECT = "koing refresh token";
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000L * 60 * 60 * 24 * 7; // 1시간 * 24 * 7 = 7일 / refresh token

    private final SecretKey secretKey;

    public JwtTokenProviderImpl(@Value("${springboot.jwt.secret}") final String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

//    @Override
//    public String createAccessToken(final Long memberId, final UserRole userRole, final ZonedDateTime accessTokenExpire) {
//        final Date now = new Date();
//        final Date expiration = Date.from(accessTokenExpire.toInstant());
//
//        final Claims claims = Jwts.claims(
//                Map.of(
//                        MEMBER_ID_KEY_NAME, memberId,
//                        ROLE_KEY_NAME, role.name()
//                )
//        );
//
//        return Jwts.builder()
//                .setSubject(ACCESS_TOKEN_SUBJECT)
//                .setClaims(claims)
//                .setIssuedAt(now)
//                .setExpiration(expiration)
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact();
//    }

    @Override
    public String createRefreshToken(final Long userId) {
        Date now = new Date();

        Claims claims = Jwts.claims(Map.of(USER_ID_KEY_NAME, userId));

        return Jwts.builder()
                .setSubject(REFRESH_TOKEN_SUBJECT)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

//    @Override
//    public LoginMemberInfo getLoginMemberByAccessToken(final String accessToken) {
//        final Claims payload = tokenToJws(accessToken).getBody();
//        final Long memberId = payload.get(MEMBER_ID_KEY_NAME, Long.class);
//        final String role = payload.get(ROLE_KEY_NAME, String.class);
//        return new LoginMemberInfo(memberId, role);
//    }

    @Override
    public Long getUserIdByRefreshToken(final String refreshToken) {
        final Claims payload = tokenToJws(refreshToken).getBody();
        return payload.get(USER_ID_KEY_NAME, Long.class);
    }

    private Jws<Claims> tokenToJws(final String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
        } catch (final IllegalArgumentException | MalformedJwtException e) {
            throw new JwtInvalidFormException("유효하지 않은 form 입니다.", ErrorCode.UNAUTHORIZED_INVALID_FORM_EXCEPTION);
        } catch (final SignatureException e) {
            throw new JwtInvalidSecretKeyException("유효하지 않은 secret key 입니다.", ErrorCode.UNAUTHORIZED_INVALID_SECRET_KEY_EXCEPTION);
        } catch (final ExpiredJwtException e) {
            throw new JwtInvalidExpiredException("유효기간이 만료된 토큰 입니다.", ErrorCode.UNAUTHORIZED_INVALID_EXPIRED_EXCEPTION);
        }
    }
}
