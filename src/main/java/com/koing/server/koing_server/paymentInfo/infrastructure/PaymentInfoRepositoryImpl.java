package com.koing.server.koing_server.paymentInfo.infrastructure;

import static com.koing.server.koing_server.paymentInfo.domain.QPaymentInfo.paymentInfo;

import com.koing.server.koing_server.paymentInfo.domain.PaymentInfo;
import com.koing.server.koing_server.paymentInfo.domain.PaymentStatus;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentInfoRepositoryImpl implements PaymentInfoRepositoryCustom {

    private final JPQLQueryFactory jpqlQueryFactory;

    @Override
    public PaymentInfo findPaymentInfoByOrderId(final String orderId) {
        return jpqlQueryFactory
                .selectFrom(paymentInfo)
                .where(
                        paymentInfo.orderId.eq(orderId),
                        isNotDeleted()
                )
                .fetchOne();
    }

    @Override
    public PaymentInfo findPaymentInfoByTourIdAndTourDate(final Long tourId, final String tourDate) {
        return jpqlQueryFactory
                .selectFrom(paymentInfo)
                .where(
                        paymentInfo.tourId.eq(tourId),
                        paymentInfo.tourDate.eq(tourDate),
                        isNotCanceled(),
                        isNotDeleted()
                )
                .fetchOne();
    }

    private BooleanExpression isNotDeleted() {
        return paymentInfo.isDeleted.eq(Boolean.FALSE);
    }

    private BooleanExpression isNotCanceled() {
        return paymentInfo.paymentStatus.ne(PaymentStatus.CANCELLED);
    }
}
