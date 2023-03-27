package com.koing.server.koing_server.domain.user.repository;

import com.koing.server.koing_server.domain.user.UserOptionalInfo;

public interface UserOptionalInfoRepositoryCustom {

    UserOptionalInfo findUserOptionalInfoByUserId(Long userId);

}
