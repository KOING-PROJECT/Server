package com.koing.server.koing_server.domain.tour.repository.TourApplication;

import com.koing.server.koing_server.domain.tour.TourApplication;
import com.koing.server.koing_server.domain.user.User;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.koing.server.koing_server.domain.review.QReviewToGuide.reviewToGuide;
import static com.koing.server.koing_server.domain.tour.QTour.tour;
import static com.koing.server.koing_server.domain.tour.QTourApplication.tourApplication;
import static com.koing.server.koing_server.domain.tour.QTourParticipant.tourParticipant;
import static com.koing.server.koing_server.domain.user.QUser.user;

@RequiredArgsConstructor
public class TourApplicationRepositoryImpl implements TourApplicationRepositoryCustom {

    private final JPQLQueryFactory jpqlQueryFactory;

    @Override
    public List<TourApplication> findTourApplicationsByTourId(Long tourId) {
        return jpqlQueryFactory
                .selectFrom(tourApplication)
                .leftJoin(tourApplication.tour, tour)
                .fetchJoin()
                .leftJoin(tourApplication.tourParticipants, tourParticipant)
                .fetchJoin()
                .leftJoin(tourApplication.reviewsToGuide, reviewToGuide)
                .fetchJoin()
                .distinct()
                .where(
                        tourApplication.tour.id.eq(tourId)
                )
                .fetch();
    }

    @Override
    public TourApplication findTourApplicationByTourIdAndTourDate(Long tourId, String tourDate) {
        return jpqlQueryFactory
                .selectFrom(tourApplication)
                .leftJoin(tourApplication.tour, tour)
                .fetchJoin()
                .leftJoin(tourApplication.tourParticipants, tourParticipant)
                .fetchJoin()
                .leftJoin(tourApplication.reviewsToGuide, reviewToGuide)
                .fetchJoin()
                .distinct()
                .where(
                        tourApplication.tour.id.eq(tourId),
                        tourApplication.tourDate.eq(tourDate)
                )
                .fetchOne();
    }
}
