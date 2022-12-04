package com.koing.server.koing_server.domain.Jwt.repository;

import com.koing.server.koing_server.domain.Jwt.JwtToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtTokenRepository extends JpaRepository<JwtToken, Long> {
}
