package com.koing.server.koing_server.domain.fcm.repository;

import com.koing.server.koing_server.domain.fcm.FCMToken;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.koing.server.koing_server.domain.fcm.QFCMToken.fCMToken;
@RequiredArgsConstructor
public class FCMTokenRepositoryImpl implements FCMTokenRepositoryCustom {

    private final JPQLQueryFactory jpqlQueryFactory;

    @Override
    public FCMToken findFCMTokenByUserIdAndDeviceId(Long userId) {
        return jpqlQueryFactory
                .selectFrom(fCMToken)
                .where(
                        fCMToken.userId.eq(userId)
                )
                .fetchOne();
    }
}
