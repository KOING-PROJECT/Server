package com.koing.server.koing_server.domain.fcm.repository;

import com.koing.server.koing_server.domain.fcm.FCMToken;

public interface FCMTokenRepositoryCustom {

    FCMToken findFCMTokenByUserIdAndDeviceId(Long userId);

}
