package com.koing.server.koing_server.domain.Jwt.repository;

import com.koing.server.koing_server.domain.Jwt.JwtToken;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.koing.server.koing_server.domain.Jwt.QJwtToken.jwtToken;

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
