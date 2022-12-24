package com.koing.server.koing_server.domain.tour.repository;

import com.koing.server.koing_server.common.enums.TourStatus;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.service.tour.dto.TourListDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

import static com.koing.server.koing_server.domain.tour.QTour.tour;
import static com.koing.server.koing_server.domain.tour.QTourApplication.tourApplication;
import static com.koing.server.koing_server.domain.tour.QTourSchedule.tourSchedule;
import static com.koing.server.koing_server.domain.user.QUser.user;

@RequiredArgsConstructor
public class TourRepositoryImpl implements TourRepositoryCustom {

    private final JPQLQueryFactory jpqlQueryFactory;

    @Override
    public Tour findTourByTourId(Long id) {
        return jpqlQueryFactory
                .selectFrom(tour)
                .leftJoin(tour.createUser, user)
                .fetchJoin()
                .leftJoin(tour.additionalPrice)
                .fetchJoin()
                .leftJoin(tour.tourApplications, tourApplication)
                .fetchJoin()
                .leftJoin(tour.tourSchedule, tourSchedule)
                .fetchJoin()
                .where(
                        tour.id.eq(id)
                ).fetchOne();
    }

    @Override
    public List<TourListDto> findTourByStatusRecruitmentAndStandby() {
        return jpqlQueryFactory
                .select(Projections.constructor(
                        TourListDto.class,
                        tour.title,
                        tour.participant,
                        tour.thumbnail,
                        tour.createUser.name,
                        tour.createUser.userOptionalInfo.imageUrl,
                        tour.tourSchedule.tourDates
                ))
                .from(tour)
                .leftJoin(tour.createUser, user)
                .fetchJoin()
                .leftJoin(tour.additionalPrice)
                .fetchJoin()
                .leftJoin(tour.tourApplications, tourApplication)
                .fetchJoin()
                .leftJoin(tour.tourSchedule, tourSchedule)
                .where(
                        tour.tourStatus.eq(TourStatus.RECRUITMENT)
                                .or(tour.tourStatus.eq(TourStatus.STANDBY))
                )
                .distinct()
                .fetch();
    }
}
