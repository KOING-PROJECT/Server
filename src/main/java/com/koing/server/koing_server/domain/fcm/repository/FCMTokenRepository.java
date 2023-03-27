package com.koing.server.koing_server.domain.fcm.repository;

import com.koing.server.koing_server.domain.fcm.FCMToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FCMTokenRepository extends JpaRepository<FCMToken, Long> {
}
