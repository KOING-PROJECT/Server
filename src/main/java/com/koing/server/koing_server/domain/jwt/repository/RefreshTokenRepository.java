package com.koing.server.koing_server.domain.jwt.repository;

import com.koing.server.koing_server.domain.jwt.RefreshToken;

import java.util.List;

public interface RefreshTokenRepository {

    RefreshToken save(RefreshToken refreshToken);

    List<RefreshToken> findByUserId(Long userId);

    void removeOldRefreshToken(Long userId);

    void removeRefreshToken(Long userId, String deletedRefreshTokenId);
}
