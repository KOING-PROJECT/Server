package com.koing.server.koing_server.domain.user.repository;

import com.koing.server.koing_server.domain.user.User;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.koing.server.koing_server.domain.user.QUser.user;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPQLQueryFactory jpqlQueryFactory;

    @Override
    public List<User> findAllUserByEnabled(boolean enabled) {
        return jpqlQueryFactory
                .selectFrom(user)
                .where(
                        user.enabled.eq(true)
                ).fetch();
    }

    @Override
    public User loadUserByUserEmail(String email, boolean enabled) {
        return jpqlQueryFactory
                .selectFrom(user)
                .where(
                        user.email.eq(email),
                        user.enabled.eq(enabled)
                )
                .fetchOne();
    }
}
