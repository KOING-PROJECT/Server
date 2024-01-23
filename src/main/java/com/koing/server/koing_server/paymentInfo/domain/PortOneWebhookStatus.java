package com.koing.server.koing_server.paymentInfo.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PortOneWebhookStatus {

    STANDBY("생성"),
    PAID("결제 완료"),
    READY("가상계좌 발급"),
    CANCELLED("관리자가 결제 취소"),
    FAILED("예약결제 시도"),
    ;

    private final String description;
}
