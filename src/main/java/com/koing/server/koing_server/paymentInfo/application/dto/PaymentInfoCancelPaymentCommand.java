package com.koing.server.koing_server.paymentInfo.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentInfoCancelPaymentCommand {

    private String orderId;
}
