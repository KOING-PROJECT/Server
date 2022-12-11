package com.koing.server.koing_server.domain.cryptogram.repository;

import com.koing.server.koing_server.domain.cryptogram.Cryptogram;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.koing.server.koing_server.domain.cryptogram.QCryptogram.cryptogram1;

@RequiredArgsConstructor
public class CryptogramRepositoryImpl implements CryptogramRepositoryCustom {

    private final JPQLQueryFactory jpqlQueryFactory;

    @Override
    public boolean hasCryptogramByUserEmail(String userEmail) {
        return jpqlQueryFactory
                .selectFrom(cryptogram1)
                .where(
                        cryptogram1.targetEmail.eq(userEmail)
                ).fetchFirst() != null; // 존재하면 true, 존재하지 않으면 false
    }

    @Override
    public Cryptogram findCryptogramByUserEmail(String userEmail) {
        return jpqlQueryFactory
                .selectFrom(cryptogram1)
                .where(
                        cryptogram1.targetEmail.eq(userEmail)
                )
                .fetchOne();
    }
}
