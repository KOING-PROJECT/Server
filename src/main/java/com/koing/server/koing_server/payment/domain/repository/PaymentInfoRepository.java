package com.koing.server.koing_server.payment.domain.repository;

import com.koing.server.koing_server.payment.domain.PaymentInfo;
import com.koing.server.koing_server.payment.infrastructure.PaymentInfoRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, Long>, PaymentInfoRepositoryCustom {

}
