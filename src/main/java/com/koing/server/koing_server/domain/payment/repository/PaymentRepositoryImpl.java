package com.koing.server.koing_server.domain.payment.repository;

import com.koing.server.koing_server.domain.payment.Payment;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.koing.server.koing_server.domain.payment.QPayment.payment;
import static com.koing.server.koing_server.domain.tour.QTourApplication.tourApplication;
import static com.koing.server.koing_server.domain.user.QUser.user;

@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepositoryCustom {

    private final JPQLQueryFactory jpqlQueryFactory;

    @Override
    public Payment findPaymentByImpUid(String impUid) {
        return jpqlQueryFactory
                .selectFrom(payment)
                .leftJoin(payment.guide, user)
                .fetchJoin()
                .leftJoin(payment.tourist, user)
                .fetchJoin()
                .leftJoin(payment.paymentProduct, tourApplication)
                .fetchJoin()
                .distinct()
                .where(
                        payment.imp_uid.eq(impUid)
                )
                .fetchOne();
    }
}
