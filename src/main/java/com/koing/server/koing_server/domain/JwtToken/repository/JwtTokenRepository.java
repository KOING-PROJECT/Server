package com.koing.server.koing_server.domain.JwtToken.repository;

import com.koing.server.koing_server.domain.JwtToken.JwtToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtTokenRepository extends JpaRepository<JwtToken, Long> {
}
