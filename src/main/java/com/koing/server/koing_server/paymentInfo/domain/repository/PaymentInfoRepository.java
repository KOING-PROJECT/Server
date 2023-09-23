package com.koing.server.koing_server.paymentInfo.domain.repository;

import com.koing.server.koing_server.paymentInfo.domain.PaymentInfo;
import com.koing.server.koing_server.paymentInfo.infrastructure.PaymentInfoRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, Long>, PaymentInfoRepositoryCustom {

}
