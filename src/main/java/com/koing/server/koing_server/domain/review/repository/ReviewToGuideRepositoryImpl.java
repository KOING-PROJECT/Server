package com.koing.server.koing_server.domain.review.repository;

import com.koing.server.koing_server.domain.review.ReviewToGuide;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.koing.server.koing_server.domain.review.QReviewToGuide.reviewToGuide;
import static com.koing.server.koing_server.domain.tour.QTour.tour;
import static com.koing.server.koing_server.domain.tour.QTourApplication.tourApplication;
import static com.koing.server.koing_server.domain.user.QUser.user;

@RequiredArgsConstructor
public class ReviewToGuideRepositoryImpl implements ReviewToGuideRepositoryCustom {

    private final JPQLQueryFactory jpqlQueryFactory;
    @Override
    public List<ReviewToGuide> findReviewToGuideByTourId(Long tourId) {
        return jpqlQueryFactory
                .selectFrom(reviewToGuide)
                .leftJoin(reviewToGuide.writeTourist, user)
                .fetchJoin()
                .leftJoin(reviewToGuide.relatedTourApplication, tourApplication)
                .fetchJoin()
                .where(
                        reviewToGuide.relatedTourApplication.tour.id.eq(tourId)
                )
                .distinct()
                .fetch();
    }
}
