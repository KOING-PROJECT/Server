package com.koing.server.koing_server.domain.payment;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.koing.server.koing_server.domain.common.AbstractRootEntity;
import com.koing.server.koing_server.domain.tour.TourApplication;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.service.payment.dto.PaymentCreateDto;
import com.koing.server.koing_server.service.payment.dto.PaymentInquiryResultDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "PAYMENT_TABLE")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Payment extends AbstractRootEntity {

    public Payment(PaymentCreateDto paymentCreateDto, PaymentInquiryResultDto paymentInquiryResultDto) {
        this.imp_uid = paymentCreateDto.getTxId();
        this.merchant_uid = paymentCreateDto.getPaymentId();
        this.paymentProduct = null;
        this.guide = null;
        this.tourist = null;
        this.paymentMethod = paymentInquiryResultDto.getPaymentMethod();
        this.paymentGatewayName = paymentInquiryResultDto.getPaymentGatewayName();
        this.paymentAmount = paymentInquiryResultDto.getPaymentAmount();
        this.paymentUnit = paymentInquiryResultDto.getPaymentUnit();
        this.paymentTime = paymentInquiryResultDto.getPaymentTime();
        this.paymentStatus = paymentInquiryResultDto.getPaymentStatus();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imp_uid; // 포트원 결제 UID
    private String merchant_uid; // mid_timestamp/userID/tourId/tourDate 형식

    @ManyToOne(fetch = FetchType.LAZY)
    private TourApplication paymentProduct; // 결제 상품명
    @ManyToOne(fetch = FetchType.LAZY)
    private User guide;
    @ManyToOne(fetch = FetchType.LAZY)
    private User tourist;
    private String paymentMethod; // 결제수단
    private String paymentGatewayName; // 결제대행사
    private int paymentAmount;
    private String paymentUnit;
    private String paymentTime;
    private String paymentStatus;

    public void setGuide(User guide) {
        this.guide = guide;

        if (guide.getEarnPayments() == null) {
            guide.setEarnPayments(new HashSet<>());
        }

//        guide.getEarnPayments().add(this);
    }

    public void setTourist(User tourist) {
        this.tourist = tourist;

        if (tourist.getBuyPayments() == null) {
            tourist.setBuyPayments(new HashSet<>());
        }

//        tourist.getBuyPayments().add(this);
    }

    public void setPaymentProduct(TourApplication tourApplication) {
        this.paymentProduct = tourApplication;

        if (tourApplication.getPayments() == null) {
            tourApplication.setPayments(new HashSet<>());
        }

//        tourApplication.getPayments().add(this);
    }
}
