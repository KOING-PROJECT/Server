package com.koing.server.koing_server.paymentInfo.application.dto;

import com.koing.server.koing_server.paymentInfo.domain.PaymentInfo;
import com.koing.server.koing_server.paymentInfo.domain.PaymentStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentInfoDto {

    private Long id;
    private String orderId;
    private PaymentStatus paymentStatus;

    private PaymentInfoDto(final Long id, final String orderId, final PaymentStatus paymentStatus) {
        this.id = id;
        this.orderId = orderId;
        this.paymentStatus = paymentStatus;
    }

    public static PaymentInfoDto from(final PaymentInfo paymentInfo) {
        return new PaymentInfoDto(paymentInfo.getId(), paymentInfo.getOrderId(), paymentInfo.getPaymentStatus());
    }
}
