package com.koing.server.koing_server.service.payment.dto;

import com.koing.server.koing_server.domain.tour.TourApplication;
import com.koing.server.koing_server.domain.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
public class PaymentCreateDto {

    private String txId;
    private String paymentId;

}
