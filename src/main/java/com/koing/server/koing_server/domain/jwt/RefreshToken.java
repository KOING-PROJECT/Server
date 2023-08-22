package com.koing.server.koing_server.domain.jwt;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class RefreshToken {

    private String id;
    private String refreshToken;
    private Long userId;
    private ZonedDateTime expire;

    public RefreshToken(final String refreshToken, final Long userId, final ZonedDateTime expire) {
        this.id = UUID.randomUUID().toString();
        this.refreshToken = refreshToken;
        this.userId = userId;
        this.expire = expire;
    }

    public boolean isNotExpired() {
        return expire.isAfter(ZonedDateTime.now());
    }
}