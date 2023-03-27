package com.koing.server.koing_server.domain.tour.repository.TourSurvey;

import com.koing.server.koing_server.domain.tour.TourSurvey;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.koing.server.koing_server.domain.tour.QTour.tour;
import static com.koing.server.koing_server.domain.tour.QTourSurvey.tourSurvey;

@RequiredArgsConstructor
public class TourSurveyRepositoryImpl implements TourSurveyRepositoryCustom {

    private final JPQLQueryFactory jpqlQueryFactory;

    @Override
    public TourSurvey findTourSurveyByTourId(Long tourId) {
        return jpqlQueryFactory
                .selectFrom(tourSurvey)
                .leftJoin(tourSurvey.tour, tour)
                .fetchJoin()
                .where(
                        tourSurvey.tour.id.eq(tourId)
                )
                .distinct()
                .fetchOne();
    }

}
