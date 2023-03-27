package com.koing.server.koing_server.service.payment.dto;

import com.koing.server.koing_server.domain.tour.TourApplication;
import com.koing.server.koing_server.domain.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
public class PaymentInquiryResultDto {

    public PaymentInquiryResultDto(ResponseEntity<PaymentInquiryDto> responseEntity) {
        setTourIdAndTourDate(responseEntity);
        this.paymentAmount = responseEntity.getBody().getResponse().getAmount();
        this.touristId = Long.parseLong(responseEntity.getBody().getResponse().getBuyer_name());
        this.paymentMethod = responseEntity.getBody().getResponse().getPay_method();
        this.paymentGatewayName = responseEntity.getBody().getResponse().getPg_provider();
        this.paymentUnit = responseEntity.getBody().getResponse().getCurrency();
        this.paymentTime = responseEntity.getBody().getResponse().getStarted_at();
        this.paymentStatus = responseEntity.getBody().getResponse().getStatus();
    }

    private Long tourId;
    private String tourDate;
    private int paymentAmount;
    private Long touristId;
    private String paymentMethod; // 결제수단
    private String paymentGatewayName; // 결제대행사
    private String paymentUnit;
    private String paymentTime;
    private String paymentStatus;

    private void setTourIdAndTourDate(ResponseEntity<PaymentInquiryDto> responseEntity) {
        String[] idAndDate = responseEntity.getBody().getResponse().getName().split("/");

        this.tourId = Long.parseLong(idAndDate[0]);
        this.tourDate = idAndDate[1];
    }

}
