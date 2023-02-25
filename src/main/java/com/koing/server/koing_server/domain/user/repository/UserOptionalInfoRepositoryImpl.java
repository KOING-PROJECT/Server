package com.koing.server.koing_server.domain.user.repository;

import com.koing.server.koing_server.domain.user.UserOptionalInfo;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.koing.server.koing_server.domain.user.QUser.user;
import static com.koing.server.koing_server.domain.user.QUserOptionalInfo.userOptionalInfo;

@RequiredArgsConstructor
public class UserOptionalInfoRepositoryImpl implements UserOptionalInfoRepositoryCustom {

    private final JPQLQueryFactory jpqlQueryFactory;

    @Override
    public UserOptionalInfo findUserOptionalInfoByUserId(Long userId) {
        return jpqlQueryFactory
                .selectFrom(userOptionalInfo)
                .leftJoin(userOptionalInfo.user, user)
                .fetchJoin()
                .where(
                        userOptionalInfo.user.id.eq(userId)
                )
                .distinct()
                .fetchOne();
    }
}
