package com.koing.server.koing_server.domain.jwt.repository;

import com.koing.server.koing_server.domain.jwt.JwtToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtTokenRepository extends JpaRepository<JwtToken, Long> {
}
