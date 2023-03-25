package com.koing.server.koing_server.service.payment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentCancelHistoryDto {

    private String pg_tid;
    private int amount;
    private String cancelled_at;
    private String reason;
    private String receipt_url;

}
