package com.koing.server.koing_server.paymentInfo.domain;

import com.koing.server.koing_server.domain.common.AbstractRootEntity;
import com.koing.server.koing_server.domain.tour.TourApplication;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.service.payment.dto.PaymentInquiryResultDto;
import java.util.HashSet;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "PAYMENT_INFO")
public class PaymentInfo extends AbstractRootEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "g_id")
    private Long guideId;

    @Column(name = "t_id")
    private Long touristId;

    private Long tourId;

    private String tourDate;

    private String orderId; // merchant_uid

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    private PortOneWebhookStatus portOneWebhookStatus;

    private String impUid; // 포트원 결제 UID

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

    @Builder
    public PaymentInfo(
            final Long guideId,
            final Long touristId,
            final Long tourId,
            final String tourDate,
            final String orderId,
            final PaymentStatus paymentStatus,
            final PortOneWebhookStatus portOneWebhookStatus,
            final String impUid,
            final TourApplication paymentProduct,
            final User guide,
            final User tourist,
            final String paymentMethod,
            final String paymentGatewayName,
            final int paymentAmount,
            final String paymentUnit,
            final String paymentTime
    ) {
        this.guideId = guideId;
        this.touristId = touristId;
        this.tourId = tourId;
        this.tourDate = tourDate;
        this.orderId = orderId;
        this.paymentStatus = paymentStatus;
        this.portOneWebhookStatus = portOneWebhookStatus;
        this.impUid = impUid;
        this.paymentProduct = null;
        this.guide = null;
        this.tourist = null;
        this.paymentMethod = paymentMethod;
        this.paymentGatewayName = paymentGatewayName;
        this.paymentAmount = paymentAmount;
        this.paymentUnit = paymentUnit;
        this.paymentTime = paymentTime;
    }

    public void successPaymentByClient(String impUid) {
        this.paymentStatus = PaymentStatus.PAID;
        this.impUid = impUid;
    }

    public void cancelPaymentByClient() {
        this.paymentStatus = PaymentStatus.CANCELLED;
    }

    public void successPaymentByPortOne(String impUid) {
        this.portOneWebhookStatus = PortOneWebhookStatus.PAID;
        this.impUid = impUid;
    }

    public void setGuide(User guide) {
        this.guide = guide;

        if (guide.getEarnPayments() == null) {
            guide.setEarnPayments(new HashSet<>());
        }

        guide.getEarnPayments().add(this);
    }

    public void setTourist(User tourist) {
        this.tourist = tourist;

        if (tourist.getBuyPayments() == null) {
            tourist.setBuyPayments(new HashSet<>());
        }

        tourist.getBuyPayments().add(this);
    }

    public void setPaymentProduct(TourApplication tourApplication) {
        this.paymentProduct = tourApplication;

        if (tourApplication.getPayments() == null) {
            tourApplication.setPayments(new HashSet<>());
        }

        tourApplication.getPayments().add(this);
    }

    public void updatePaymentInfo(PaymentInquiryResultDto paymentInquiryResultDto) {
        this.paymentProduct = null;
        this.guide = null;
        this.tourist = null;
        this.paymentMethod = paymentInquiryResultDto.getPaymentMethod();
        this.paymentGatewayName = paymentInquiryResultDto.getPaymentGatewayName();
        this.paymentAmount = paymentInquiryResultDto.getPaymentAmount();
        this.paymentUnit = paymentInquiryResultDto.getPaymentUnit();
        this.paymentTime = paymentInquiryResultDto.getPaymentTime();
    }
}
