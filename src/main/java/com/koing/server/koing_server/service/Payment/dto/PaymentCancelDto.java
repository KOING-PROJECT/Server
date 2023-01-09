package com.koing.server.koing_server.service.Payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentCancelDto {

    @JsonProperty("imp_uid")
    private String impUid;

    @JsonProperty("merchant_uid")
    private String merchantUid;

    private double amount;

    @JsonProperty("tax_free")
    private double taxFree;

    @JsonProperty("vat_amount")
    private double vatAmount;

    private double checksum;

    private String reason;

    @JsonProperty("refund_holder")
    private String refundHolder;

    @JsonProperty("refund_bank")
    private String refundBank;

    @JsonProperty("refund_account")
    private String refundAccount;

    @JsonProperty("refund_tel")
    private String refundTel;
}
