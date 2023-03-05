package com.koing.server.koing_server.domain.image.repository;

import com.koing.server.koing_server.domain.image.Thumbnail;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.koing.server.koing_server.domain.image.QThumbnail.thumbnail;

@RequiredArgsConstructor
public class ThumbnailRepositoryImpl implements ThumbnailRepositoryCustom {

    private final JPQLQueryFactory jpqlQueryFactory;

    @Override
    public Thumbnail findThumbnailByFilePath(String filePath) {
        return jpqlQueryFactory
                .selectFrom(thumbnail)
                .where(
                        thumbnail.filePath.eq(filePath)
                )
                .fetchOne();
    }
}
