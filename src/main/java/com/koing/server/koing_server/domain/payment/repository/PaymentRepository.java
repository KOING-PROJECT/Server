package com.koing.server.koing_server.domain.payment.repository;

import com.koing.server.koing_server.domain.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
