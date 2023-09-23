package com.koing.server.koing_server.paymentInfo.infrastructure;

import com.koing.server.koing_server.paymentInfo.domain.PaymentInfo;

public interface PaymentInfoRepositoryCustom {

    PaymentInfo findPaymentInfoByOrderId(final String orderId);

}
