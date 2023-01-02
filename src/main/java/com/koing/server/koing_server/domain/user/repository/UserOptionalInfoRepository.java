package com.koing.server.koing_server.domain.user.repository;

import com.koing.server.koing_server.domain.user.UserOptionalInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOptionalInfoRepository extends JpaRepository<UserOptionalInfo, Long> {
}
