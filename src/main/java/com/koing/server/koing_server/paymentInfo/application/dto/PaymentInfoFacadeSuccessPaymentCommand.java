package com.koing.server.koing_server.paymentInfo.application.dto;

import com.koing.server.koing_server.common.visitor.CommandAcceptor;
import com.koing.server.koing_server.common.visitor.CommandVisitor;
import com.koing.server.koing_server.service.tour.dto.TourApplicationParticipateDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInfoFacadeSuccessPaymentCommand {

    private String impUid; // impUid
    private String merchantUid; // orderId
    private Long tourId;
    private Long userId;
    private String tourDate;
    private int numberOfParticipants;

    public PaymentInfoSuccessPaymentCommand toPaymentInfoSuccessPaymentCommand() {
        return new PaymentInfoSuccessPaymentCommand(impUid, merchantUid);
    }

    public TourApplicationParticipateDto toTourApplicationParticipateDto() {
        return new TourApplicationParticipateDto(tourId, userId, tourDate, numberOfParticipants);
    }
}
