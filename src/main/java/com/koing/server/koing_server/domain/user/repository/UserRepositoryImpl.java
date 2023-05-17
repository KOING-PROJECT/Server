package com.koing.server.koing_server.domain.user.repository;

import com.koing.server.koing_server.common.enums.UserRole;
import com.koing.server.koing_server.common.enums.UserStatus;
import com.koing.server.koing_server.domain.user.User;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.koing.server.koing_server.domain.payment.QPayment.payment;
import static com.koing.server.koing_server.domain.tour.QTour.tour;
import static com.koing.server.koing_server.domain.tour.QTourApplication.tourApplication;
import static com.koing.server.koing_server.domain.tour.QTourParticipant.tourParticipant;
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
                .leftJoin(user.tourParticipants, tourParticipant)
                .fetchJoin()
                .leftJoin(user.userOptionalInfo, userOptionalInfo)
                .fetchJoin()
                .leftJoin(user.pressLikeTours, tour)
                .fetchJoin()
                .leftJoin(user.earnPayments, payment)
                .fetchJoin()
                .leftJoin(user.buyPayments, payment)
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
                .leftJoin(user.tourParticipants, tourParticipant)
                .fetchJoin()
                .leftJoin(user.userOptionalInfo, userOptionalInfo)
                .fetchJoin()
                .leftJoin(user.pressLikeTours, tour)
                .fetchJoin()
                .leftJoin(user.earnPayments, payment)
                .fetchJoin()
                .leftJoin(user.buyPayments, payment)
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
        System.out.println(email);
        User existUser = jpqlQueryFactory
                .selectFrom(user)
                .where(
                        user.email.eq(email)
                )
                .fetchOne();

        if (existUser != null) {
            System.out.println("존재");
            return true;
        }
//        System.out.println(existUser.getEmail());
//        System.out.println(existUser.getName());
        System.out.println("존재하지않음");
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
                .leftJoin(user.tourParticipants, tourParticipant)
                .fetchJoin()
                .leftJoin(user.userOptionalInfo, userOptionalInfo)
                .fetchJoin()
                .leftJoin(user.pressLikeTours, tour)
                .fetchJoin()
                .leftJoin(user.earnPayments, payment)
                .fetchJoin()
                .leftJoin(user.buyPayments, payment)
                .fetchJoin()
                .where(
                        user.id.eq(userId)
                )
                .distinct()
                .fetchOne();
    }

    @Override
    public List<User> findAllGuideByEnabled(boolean enabled) {
        return jpqlQueryFactory
                .selectFrom(user)
                .leftJoin(user.roles)
                .fetchJoin()
                .leftJoin(user.createTours, tour)
                .fetchJoin()
                .leftJoin(user.tourParticipants, tourParticipant)
                .fetchJoin()
                .leftJoin(user.userOptionalInfo, userOptionalInfo)
                .fetchJoin()
                .leftJoin(user.pressLikeTours, tour)
                .fetchJoin()
                .leftJoin(user.earnPayments, payment)
                .fetchJoin()
                .leftJoin(user.buyPayments, payment)
                .fetchJoin()
                .where(
                        user.userStatus.eq(UserStatus.ACTIVATE),
                        user.roles.contains(UserRole.ROLE_GUIDE.getRole())
                )
                .distinct()
                .fetch();
    }

    @Override
    public List<User> findAllUsers() {
        return jpqlQueryFactory
                .selectFrom(user)
                .leftJoin(user.roles)
                .fetchJoin()
                .leftJoin(user.createTours, tour)
                .fetchJoin()
                .leftJoin(user.userOptionalInfo, userOptionalInfo)
                .fetchJoin()
                .distinct()
                .fetch();
    }

    @Override
    public List<User> findWithdrawalUsers() {
        return jpqlQueryFactory
                .selectFrom(user)
                .leftJoin(user.roles)
                .fetchJoin()
                .leftJoin(user.createTours, tour)
                .fetchJoin()
                .leftJoin(user.tourParticipants, tourParticipant)
                .fetchJoin()
                .leftJoin(user.userOptionalInfo, userOptionalInfo)
                .fetchJoin()
                .leftJoin(user.pressLikeTours, tour)
                .fetchJoin()
                .leftJoin(user.earnPayments, payment)
                .fetchJoin()
                .leftJoin(user.buyPayments, payment)
                .fetchJoin()
                .where(
                        user.userStatus.eq(UserStatus.REQUEST_WITHDRAWAL).or(user.userStatus.eq(UserStatus.WITHDRAWAL))
                )
                .distinct()
                .fetch();
    }

}
