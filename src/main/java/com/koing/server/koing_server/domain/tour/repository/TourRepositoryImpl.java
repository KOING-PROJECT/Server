package com.koing.server.koing_server.domain.tour.repository;

import com.koing.server.koing_server.common.enums.TourStatus;
import com.koing.server.koing_server.domain.tour.Tour;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.koing.server.koing_server.domain.tour.QTour.tour;

@RequiredArgsConstructor
public class TourRepositoryImpl implements TourRepositoryCustom {

    private final JPQLQueryFactory jpqlQueryFactory;

    @Override
    public Tour findTourByTourId(Long id) {
        return jpqlQueryFactory
                .selectFrom(tour)
                .where(
                        tour.id.eq(id)
                ).fetchOne();
    }

    @Override
    public List<Tour> findTourByStatusRecruitmentAndStandby() {
        return jpqlQueryFactory
                .selectFrom(tour)
                .where(
                        tour.tourStatus.eq(TourStatus.RECRUITMENT)
                                .or(tour.tourStatus.eq(TourStatus.STANDBY))
                ).fetch();
    }
}
