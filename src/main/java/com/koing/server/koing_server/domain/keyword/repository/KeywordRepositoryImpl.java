package com.koing.server.koing_server.domain.keyword.repository;

import com.koing.server.koing_server.domain.keyword.Keyword;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.koing.server.koing_server.domain.keyword.QKeyword.keyword1;
@RequiredArgsConstructor
public class KeywordRepositoryImpl implements KeywordRepositoryCustom {

    private final JPQLQueryFactory jpqlQueryFactory;

    @Override
    public List<Keyword> findAllKeywords() {
        return jpqlQueryFactory
                .selectFrom(keyword1)
                .fetch();
    }

    @Override
    public Keyword findKeywordByKeyword(String keyword) {
        return jpqlQueryFactory
                .selectFrom(keyword1)
                .where(
                        keyword1.keyword.eq(keyword)
                )
                .fetchOne();
    }
}
