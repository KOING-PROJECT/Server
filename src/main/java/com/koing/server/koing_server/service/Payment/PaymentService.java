package com.koing.server.koing_server.service.Payment;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.PaymentServerException;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.service.Payment.dto.PaymentAccessTokenResponseDto;
import com.koing.server.koing_server.service.Payment.dto.PaymentAccessTokenRequestDto;
import com.koing.server.koing_server.service.Payment.dto.PaymentCancelDto;
import com.koing.server.koing_server.service.Payment.dto.PaymentIamportResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class PaymentService {

    @Value("${iamport.imp.secret}")
    private String impSecret = "";

    private final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);
    private static String impKey = "0428352841086548";

    public SuperResponse getPaymentDetail(String impUid) {

        String accessToken;
        try {
            accessToken = getIamportAccessToken();
        } catch (Exception exception) {
            throw new PaymentServerException(exception.getMessage(), ErrorCode.PAYMENT_SERVER_CONNECT_ERROR);
        }

        URI uri = UriComponentsBuilder
                .fromUriString("https://api.iamport.kr")
                .path("/payments/{imp_uid}")
                .encode()
                .build()
                .expand(impUid)
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("Content-Type", "application/json");

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<PaymentIamportResponseDto> result = restTemplate
                .exchange(uri, HttpMethod.GET, requestEntity, PaymentIamportResponseDto.class);

        return SuccessResponse.success(SuccessCode.SUCCESS, result);
    }

    public SuperResponse cancelPayment(PaymentCancelDto paymentCancelDto) {

        String accessToken;
        try {
            accessToken = getIamportAccessToken();
        } catch (Exception exception) {
            throw new PaymentServerException(exception.getMessage(), ErrorCode.PAYMENT_SERVER_CONNECT_ERROR);
        }

        URI uri = UriComponentsBuilder
                .fromUriString("https://api.iamport.kr")
                .path("/payments/cancel")
                .encode()
                .build()
                .toUri();

        RequestEntity<PaymentCancelDto> requestEntity = RequestEntity
                .post(uri)
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .body(paymentCancelDto);


        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<PaymentIamportResponseDto> cancelPaymentResult
                = restTemplate.exchange(requestEntity, PaymentIamportResponseDto.class);

        return SuccessResponse.success(SuccessCode.SUCCESS, cancelPaymentResult);
    }


    private String getIamportAccessToken() {

        URI uri = UriComponentsBuilder
                .fromUriString("https://api.iamport.kr")
                .path("/users/getToken")
                .encode()
                .build()
                .toUri();

        PaymentAccessTokenRequestDto data = new PaymentAccessTokenRequestDto(impKey, impSecret);

        RequestEntity<PaymentAccessTokenRequestDto> requestEntity = RequestEntity
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .body(data);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<PaymentAccessTokenResponseDto> accessTokenResult
                = restTemplate.exchange(requestEntity, PaymentAccessTokenResponseDto.class);

        return accessTokenResult.getBody().getResponse().getAccessToken();
    }

}
