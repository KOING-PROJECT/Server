package com.koing.server.koing_server.paymentInfo.application.dto;

import com.koing.server.koing_server.paymentInfo.domain.PaymentInfo;
import com.koing.server.koing_server.paymentInfo.domain.PaymentStatus;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentInfoCreateCommand {

    private Long guestId;
    private Long touristId;
    private Long tourId;
    private String tourDate;

    public PaymentInfo toEntity() {
        return PaymentInfo.builder()
                .guestId(guestId)
                .touristId(touristId)
                .tourId(tourId)
                .tourDate(tourDate)
                .orderId(createOrderId(guestId, touristId, tourId, tourDate))
                .paymentStatus(PaymentStatus.READY)
                .build();
    }

    private String createOrderId(
            final Long guestId,
            final Long touristId,
            final Long tourId,
            final String tourDate)
    {
        return UUID.randomUUID()
                + "/" + guestId
                + "/" + touristId
                + "/" + tourId
                + "/" + tourDate;
    }
}
