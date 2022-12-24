package com.koing.server.koing_server.domain.tour.repository;

import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.tour.TourApplication;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.koing.server.koing_server.domain.tour.QTour.tour;
import static com.koing.server.koing_server.domain.tour.QTourApplication.tourApplication;
import static com.koing.server.koing_server.domain.tour.QTourCategory.tourCategory;
import static com.koing.server.koing_server.domain.user.QUser.user;

@RequiredArgsConstructor
public class TourApplicationRepositoryImpl implements TourApplicationRepositoryCustom {

    private final JPQLQueryFactory jpqlQueryFactory;

    @Override
    public TourApplication findTourApplicationByTourIdAndTourDate(Long tourId, String tourDate) {
        return jpqlQueryFactory
                .selectFrom(tourApplication)
                .leftJoin(tourApplication.tour, tour)
                .fetchJoin()
                .leftJoin(tourApplication.tour.tourCategories, tourCategory)
                .fetchJoin()
                .leftJoin(tourApplication.participants, user)
                .fetchJoin()
                .distinct()
                .where(
                        tourApplication.tour.id.eq(tourId),
                        tourApplication.tourDate.eq(tourDate)
                )
                .fetchOne();
    }
}
