package com.koing.server.koing_server.paymentInfo.infrastructure;

import static com.koing.server.koing_server.paymentInfo.domain.QPaymentInfo.paymentInfo;

import com.koing.server.koing_server.paymentInfo.domain.PaymentInfo;
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

    private BooleanExpression isNotDeleted() {
        return paymentInfo.isDeleted.eq(Boolean.FALSE);
    }
}
