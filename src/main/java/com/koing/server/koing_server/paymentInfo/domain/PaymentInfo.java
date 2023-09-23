package com.koing.server.koing_server.paymentInfo.domain;

import com.koing.server.koing_server.domain.common.AbstractRootEntity;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    private Long guestId;

    private Long touristId;

    private Long tourId;

    private String tourDate;

    private String orderId;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Builder
    public PaymentInfo(
            final Long guestId,
            final Long touristId,
            final Long tourId,
            final String tourDate,
            final String orderId,
            final PaymentStatus paymentStatus
    ) {
        this.guestId = guestId;
        this.touristId = touristId;
        this.tourId = tourId;
        this.tourDate = tourDate;
        this.orderId = orderId;
        this.paymentStatus = paymentStatus;
    }
}
