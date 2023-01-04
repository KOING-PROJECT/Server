package com.koing.server.koing_server.domain.user.repository;

import com.koing.server.koing_server.domain.user.User;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.koing.server.koing_server.domain.tour.QTour.tour;
import static com.koing.server.koing_server.domain.tour.QTourApplication.tourApplication;
import static com.koing.server.koing_server.domain.user.QUser.user;
import static com.koing.server.koing_server.domain.user.QUserOptionalInfo.userOptionalInfo;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPQLQueryFactory jpqlQueryFactory;

    @Override
    public List<User> findAllUserByEnabled(boolean enabled) {
        return jpqlQueryFactory
                .selectFrom(user)
                .leftJoin(user.roles)
                .fetchJoin()
                .leftJoin(user.createTours, tour)
                .fetchJoin()
                .leftJoin(user.tourApplication, tourApplication)
                .fetchJoin()
                .leftJoin(user.userOptionalInfo, userOptionalInfo)
                .fetchJoin()
                .leftJoin(user.pressLikeTours, tour)
                .fetchJoin()
                .where(
                        user.enabled.eq(true)
                )
                .distinct()
                .fetch();
    }

    @Override
    public User loadUserByUserEmail(String email, boolean enabled) {
        return jpqlQueryFactory
                .selectFrom(user)
                .leftJoin(user.roles)
                .fetchJoin()
                .leftJoin(user.createTours, tour)
                .fetchJoin()
                .leftJoin(user.tourApplication, tourApplication)
                .fetchJoin()
                .leftJoin(user.userOptionalInfo, userOptionalInfo)
                .fetchJoin()
                .leftJoin(user.pressLikeTours, tour)
                .fetchJoin()
                .where(
                        user.email.eq(email),
                        user.enabled.eq(enabled)
                )
                .distinct()
                .fetchOne();
    }

    @Override
    public Boolean isExistUserByUserEmail(String email) {
        User existUser = jpqlQueryFactory.selectFrom(user)
                .where(
                        user.email.eq(email)
                )
                .fetchOne();

        if (existUser != null) {
            return true;
        }

        return false;
    }

    @Override
    public Boolean isExistUserByUserId(Long userId) {
        User existUser = jpqlQueryFactory.selectFrom(user)
                .where(
                        user.id.eq(userId)
                )
                .fetchOne();

        if (existUser != null) {
            return true;
        }

        return false;
    }

    @Override
    public User loadUserByUserId(Long userId, boolean enabled) {
        return jpqlQueryFactory
                .selectFrom(user)
                .leftJoin(user.roles)
                .fetchJoin()
                .leftJoin(user.createTours, tour)
                .fetchJoin()
                .leftJoin(user.tourApplication, tourApplication)
                .fetchJoin()
                .leftJoin(user.userOptionalInfo, userOptionalInfo)
                .fetchJoin()
                .leftJoin(user.pressLikeTours, tour)
                .fetchJoin()
                .where(
                        user.id.eq(userId)
                )
                .distinct()
                .fetchOne();
    }

}
