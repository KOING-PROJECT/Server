package com.koing.server.koing_server.paymentInfo.application.exception;

import com.koing.server.koing_server.common.exception.PaymentServerException;

public class PaymentFailException extends PaymentServerException {

    public PaymentFailException() {
        super(String.format("결제에 실패했습니다."));
    }
}
