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
import static com.koing.server.koing_server.domain.tour.QTourSurvey.tourSurvey;
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
    public List<Tour> findTourByStatusRecruitment() {
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
                .leftJoin(tour.tourSurvey, tourSurvey)
                .where(
                        tour.tourStatus.eq(TourStatus.RECRUITMENT),
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

    @Override
    public Tour findTemporaryTourByTourId(Long tourId) {
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
                        tour.createStatus.eq(CreateStatus.CREATING),
                        tour.id.eq(tourId)
                )
                .distinct()
                .fetchOne();
    }

    @Override
    public boolean checkExistByTourId(Long tourId) {
        return jpqlQueryFactory
                .selectFrom(tour)
                .where(
                        tour.id.eq(tourId)
                )
                .distinct()
                .fetchOne() != null;
    }

    @Override
    public List<Tour> findNotFinishTour() {
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
                .leftJoin(tour.tourSurvey, tourSurvey)
                .where(
                        tour.createStatus.eq(CreateStatus.COMPLETE),
                        tour.tourStatus.ne(TourStatus.FINISH)
                )
                .distinct()
                .fetch();
    }

    @Override
    public List<Tour> findFinishedTour() {
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
                .leftJoin(tour.tourSurvey, tourSurvey)
                .where(
                        tour.createStatus.eq(CreateStatus.COMPLETE),
                        tour.tourStatus.eq(TourStatus.FINISH)
                )
                .distinct()
                .fetch();
    }
}
