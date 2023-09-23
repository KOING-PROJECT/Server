package com.koing.server.koing_server.paymentInfo.application.exception;

import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.upperException.NotFoundException;

public class NotFoundPaymentInfoException extends NotFoundException {

    public NotFoundPaymentInfoException(final String orderId) {
        super(String.format("해당 결제 정보가 존재하지 않습니다. orderId = %s", orderId));
    }
}
