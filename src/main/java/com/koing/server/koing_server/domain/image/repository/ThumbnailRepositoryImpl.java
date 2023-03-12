package com.koing.server.koing_server.domain.image.repository;

import com.koing.server.koing_server.domain.image.Thumbnail;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.koing.server.koing_server.domain.image.QThumbnail.thumbnail;
import static com.koing.server.koing_server.domain.tour.QTour.tour;

@RequiredArgsConstructor
public class ThumbnailRepositoryImpl implements ThumbnailRepositoryCustom {

    private final JPQLQueryFactory jpqlQueryFactory;

    @Override
    public List<Thumbnail> findThumbnailByTourId(Long tourId) {
        return jpqlQueryFactory
                .selectFrom(thumbnail)
                .leftJoin(thumbnail.ownedTour, tour)
                .fetchJoin()
                .where(
                        thumbnail.ownedTour.id.eq(tourId)
                )
                .fetch();
    }

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
