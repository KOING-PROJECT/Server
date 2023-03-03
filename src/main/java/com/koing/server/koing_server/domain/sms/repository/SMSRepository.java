package com.koing.server.koing_server.domain.sms.repository;

import com.koing.server.koing_server.domain.sms.SMS;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SMSRepository extends JpaRepository<SMS, Long> {
}
