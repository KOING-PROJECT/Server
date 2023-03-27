package com.koing.server.koing_server.domain.account.repository;

import com.koing.server.koing_server.domain.account.Account;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.koing.server.koing_server.domain.account.QAccount.account;
import static com.koing.server.koing_server.domain.user.QUser.user;

@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepositoryCustom {

    private final JPQLQueryFactory jpqlQueryFactory;

    @Override
    public Account findAccountByUserId(Long userId) {
        return jpqlQueryFactory
                .selectFrom(account)
                .leftJoin(account.accountOwner, user)
                .fetchJoin()
                .distinct()
                .where(
                        account.accountOwner.id.eq(userId)
                )
                .fetchOne();
    }
}
