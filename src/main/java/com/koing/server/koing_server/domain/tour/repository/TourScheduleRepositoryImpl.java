package com.koing.server.koing_server.domain.tour.repository;

import com.koing.server.koing_server.domain.tour.TourSchedule;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.koing.server.koing_server.domain.tour.QTour.tour;
import static com.koing.server.koing_server.domain.tour.QTourDetailSchedule.tourDetailSchedule;
import static com.koing.server.koing_server.domain.tour.QTourSchedule.tourSchedule;

@RequiredArgsConstructor
public class TourScheduleRepositoryImpl implements TourScheduleRepositoryCustom {

    private final JPQLQueryFactory jpqlQueryFactory;

    @Override
    public TourSchedule findTourScheduleByTourId(Long tourId) {
        return jpqlQueryFactory
                .selectFrom(tourSchedule)
                .leftJoin(tourSchedule.tour, tour)
                .fetchJoin()
                .leftJoin(tourSchedule.tourDetailScheduleList, tourDetailSchedule)
                .fetchJoin()
                .distinct()
                .where(
                        tourSchedule.tour.id.eq(tourId)
                )
                .distinct()
                .fetchOne();
    }
}
