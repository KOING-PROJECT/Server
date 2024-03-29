package com.koing.server.koing_server.service.payment.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PaymentInquiryResponseDto {

    private String imp_uid;
    private String merchant_uid;
    private String pay_method;
    private String channel;
    private String pg_provider;
    private String emb_pg_provider;
    private String pg_tid;
    private String pg_id;
    private boolean escrow;
    private String apply_num;
    private String bank_code;
    private String bank_name;
    private String card_code;
    private String card_name;
    private int card_quota;
    private String card_number;
    private String card_type;
    private String vbank_code;
    private String vbank_name;
    private String vbank_holder;
    private String vbank_date;
    private String vbank_issued_at;
    private String name;
    private int amount;
    private int cancel_amount;
    private String currency;
    private String buyer_name;
    private String buyer_email;
    private String buyer_tel;
    private String buyer_addr;
    private String buyer_postcode;
    private String custom_data;
    private String user_agent;
    private String status;
    private String started_at;
    private String paid_at;
    private String failed_at;
    private String cancelled_at;
    private String fail_reason;
    private String cancel_reason;
    private String receipt_url;
    private boolean cash_receipt_issued;
    private String customer_uid;
    private String customer_uid_usage;
//    private PaymentCancelHistoryDto cancel_history;
//    private List<String> cancel_receipt_urls;

}
