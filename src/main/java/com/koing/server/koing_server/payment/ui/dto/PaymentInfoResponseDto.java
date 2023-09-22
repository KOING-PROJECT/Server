package com.koing.server.koing_server.payment.ui.dto;

import com.koing.server.koing_server.payment.domain.PaymentInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentInfoResponseDto {

    private String orderId;

    private PaymentInfoResponseDto(final String orderId) {
        this.orderId = orderId;
    }

    public static PaymentInfoResponseDto from(final PaymentInfo paymentInfo) {
        return new PaymentInfoResponseDto(paymentInfo.getOrderId());
    }
}
