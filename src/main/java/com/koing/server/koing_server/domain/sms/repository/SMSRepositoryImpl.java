package com.koing.server.koing_server.domain.sms.repository;

import com.koing.server.koing_server.domain.sms.SMS;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.koing.server.koing_server.domain.sms.QSMS.sMS;

@RequiredArgsConstructor
public class SMSRepositoryImpl implements SMSRepositoryCustom {

    private final JPQLQueryFactory jpqlQueryFactory;

    @Override
    public boolean hasSMSByTargetPhoneNumber(String targetPhoneNumber) {
        return jpqlQueryFactory
                .selectFrom(sMS)
                .where(
                        sMS.targetPhoneNumber.eq(targetPhoneNumber)
                ).fetchFirst() != null;
    }

    @Override
    public SMS findSMSByTargetPhoneNumber(String targetPhoneNumber) {
        return jpqlQueryFactory
                .selectFrom(sMS)
                .where(
                        sMS.targetPhoneNumber.eq(targetPhoneNumber)
                )
                .fetchOne();
    }
}
