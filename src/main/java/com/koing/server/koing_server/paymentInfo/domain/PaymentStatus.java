package com.koing.server.koing_server.paymentInfo.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus {

    READY("미결제"),
    PAID("결제 완료"),
    CANCELLED("결제 취소"),
    FAILED("결제 실패")
    ;

    private final String description;
}
