package com.koing.server.koing_server.domain.jwt.repository;

import com.koing.server.koing_server.domain.jwt.JwtToken;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.koing.server.koing_server.domain.jwt.QJwtToken.jwtToken;

@RequiredArgsConstructor
public class JwtTokenRepositoryImpl implements JwtTokenRepositoryCustom {

    private final JPQLQueryFactory jpqlQueryFactory;


    @Override
    public JwtToken findJwtTokenByUserEmail(String userEmail) {
        return jpqlQueryFactory
                .selectFrom(jwtToken)
                .where(
                        jwtToken.userEmail.eq(userEmail)
                )
                .fetchOne();
    }
}
