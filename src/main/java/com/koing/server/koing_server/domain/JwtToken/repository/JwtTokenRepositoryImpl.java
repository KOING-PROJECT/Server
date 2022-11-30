package com.koing.server.koing_server.domain.JwtToken.repository;

import com.koing.server.koing_server.common.exception.NotFoundException;
import com.koing.server.koing_server.domain.JwtToken.JwtToken;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.koing.server.koing_server.domain.JwtToken.QJwtToken.jwtToken;
import static com.koing.server.koing_server.domain.user.QUser.user;

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
