package com.koing.server.koing_server.domain.payment.repository;

import com.koing.server.koing_server.domain.payment.Payment;

public interface PaymentRepositoryCustom {

    Payment findPaymentByImpUid(String impUid);

}
