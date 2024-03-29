package com.koing.server.koing_server.domain.tour.repository.TourParticipant;

import com.koing.server.koing_server.domain.tour.TourParticipant;
import com.koing.server.koing_server.domain.user.User;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.koing.server.koing_server.domain.review.QReviewToTourist.reviewToTourist;
import static com.koing.server.koing_server.domain.tour.QTourApplication.tourApplication;
import static com.koing.server.koing_server.domain.tour.QTourParticipant.tourParticipant;
import static com.koing.server.koing_server.domain.user.QUser.user;

@RequiredArgsConstructor
public class TourParticipantRepositoryImpl implements TourParticipantRepositoryCustom {

    private final JPQLQueryFactory jpqlQueryFactory;

    @Override
    public List<TourParticipant> findTourParticipantByUser(User participant) {
        return jpqlQueryFactory
                .selectFrom(tourParticipant)
                .leftJoin(tourParticipant.tourApplication, tourApplication)
                .fetchJoin()
                .leftJoin(tourParticipant.participant, user)
                .fetchJoin()
                .leftJoin(tourParticipant.reviewToTourist, reviewToTourist)
                .fetchJoin()
                .distinct()
                .where(
                        tourParticipant.participant.eq(user)
                )
                .fetch();
    }

    @Override
    public TourParticipant findTourParticipantByTourApplicationId(Long tourApplicationId) {
        return jpqlQueryFactory
                .selectFrom(tourParticipant)
                .leftJoin(tourParticipant.tourApplication, tourApplication)
                .fetchJoin()
                .leftJoin(tourParticipant.participant, user)
                .fetchJoin()
                .leftJoin(tourParticipant.reviewToTourist, reviewToTourist)
                .fetchJoin()
                .distinct()
                .where(
                        tourParticipant.tourApplication.id.eq(tourApplicationId)
                )
                .fetchOne();
    }

    @Override
    public TourParticipant findTourParticipantByTourIdAndTourDateAndTourParticipantId(
            Long tourId,
            String tourDate,
            Long tourParticipantId
    ) {
        return jpqlQueryFactory
                .selectFrom(tourParticipant)
                .leftJoin(tourParticipant.tourApplication, tourApplication)
                .fetchJoin()
                .leftJoin(tourParticipant.participant, user)
                .fetchJoin()
                .leftJoin(tourParticipant.reviewToTourist, reviewToTourist)
                .fetchJoin()
                .distinct()
                .where(
                        tourParticipant.tourApplication.tour.id.eq(tourId),
                        tourParticipant.tourApplication.tourDate.eq(tourDate),
                        tourParticipant.participant.id.eq(tourParticipantId)
                )
                .fetchOne();
    }
}
