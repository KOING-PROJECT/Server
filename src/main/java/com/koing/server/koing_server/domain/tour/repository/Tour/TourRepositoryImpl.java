package com.koing.server.koing_server.domain.tour.repository.Tour;

import com.koing.server.koing_server.common.enums.CreateStatus;
import com.koing.server.koing_server.common.enums.TourStatus;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.user.User;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.koing.server.koing_server.domain.tour.QTour.tour;
import static com.koing.server.koing_server.domain.tour.QTourApplication.tourApplication;
import static com.koing.server.koing_server.domain.tour.QTourCategory.tourCategory;
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
                .leftJoin(tour.tourCategories, tourCategory)
                .fetchJoin()
                .leftJoin(tour.additionalPrice)
                .fetchJoin()
                .leftJoin(tour.tourApplications, tourApplication)
                .fetchJoin()
                .leftJoin(tour.pressLikeUsers, user)
                .fetchJoin()
                .leftJoin(tour.tourSchedule, tourSchedule)
                .fetchJoin()
                .where(
                        tour.id.eq(id)
                )
                .distinct()
                .fetchOne();
    }

    @Override
    public List<Tour> findTourByStatusRecruitmentAndStandby() {
        return jpqlQueryFactory
                .selectFrom(tour)
                .leftJoin(tour.createUser, user)
                .fetchJoin()
                .leftJoin(tour.tourCategories, tourCategory)
                .fetchJoin()
                .leftJoin(tour.additionalPrice)
                .fetchJoin()
                .leftJoin(tour.tourApplications, tourApplication)
                .fetchJoin()
                .leftJoin(tour.pressLikeUsers, user)
                .fetchJoin()
                .leftJoin(tour.tourSchedule, tourSchedule)
                .fetchJoin()
                .where(
                        tour.tourStatus.eq(TourStatus.RECRUITMENT)
                                .or(tour.tourStatus.eq(TourStatus.STANDBY)),
                        tour.createStatus.eq(CreateStatus.COMPLETE)
                )
                .distinct()
                .fetch();
    }

    @Override
    public List<Tour> findLikeTourByUser(Long userId) {
        return jpqlQueryFactory
                .selectFrom(tour)
                .leftJoin(tour.createUser, user)
                .fetchJoin()
                .leftJoin(tour.tourCategories, tourCategory)
                .fetchJoin()
                .leftJoin(tour.additionalPrice)
                .fetchJoin()
                .leftJoin(tour.tourApplications, tourApplication)
                .fetchJoin()
                .leftJoin(tour.pressLikeUsers, user)
                .fetchJoin()
                .leftJoin(tour.tourSchedule, tourSchedule)
                .fetchJoin()
                .where(
                        tour.createUser.id.eq(userId)
                )
                .distinct()
                .fetch();
    }
}
