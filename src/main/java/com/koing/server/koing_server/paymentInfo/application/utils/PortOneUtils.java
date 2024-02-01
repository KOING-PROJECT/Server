package com.koing.server.koing_server.paymentInfo.application.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.InternalServerException;
import com.koing.server.koing_server.service.payment.PaymentService;
import com.koing.server.koing_server.service.payment.dto.PaymentAccessTokenRequestDto;
import com.koing.server.koing_server.service.payment.dto.PaymentAccessTokenResponseDto;
import com.koing.server.koing_server.service.payment.dto.PaymentInquiryDto;
import com.koing.server.koing_server.service.payment.dto.PaymentInquiryResultDto;
import java.net.URI;
import java.nio.charset.Charset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class PortOneUtils {

    @Value("${iamport.imp.secret}")
    private String impSecret;
    @Value("${iamport.imp.key}")
    private String impKey;

    private final Logger LOGGER = LoggerFactory.getLogger(PortOneUtils.class);
    public PaymentInquiryResultDto getPaymentInfoAtPortOne(String imp_uid) {
        LOGGER.info("[PortOneUtils] 결제 정보 조회 시도");

        String accessToken = getPortOneAccessToken();

        String path = String.format("/payments/%s", imp_uid);

        URI uri = UriComponentsBuilder
                .fromUriString("https://api.iamport.kr")
                .path(path)
                .encode()
                .build()
                .toUri();

        Charset utf8 = Charset.forName("UTF-8");
        MediaType mediaType = new MediaType("application", "json", utf8);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        headers.set("Authorization", accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        ResponseEntity<PaymentInquiryDto> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, PaymentInquiryDto.class);

        if (responseEntity.getStatusCodeValue() != 200) {
            throw new InternalServerException("예상치 못한 서버 에러가 발생하였습니다.", ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }

        if (responseEntity.getBody().getCode() != 0) {
            throw new InternalServerException("예상치 못한 서버 에러가 발생하였습니다.", ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }

        LOGGER.info("[PortOneUtils] 결제 정보 조회 성공");

//        if (!responseEntity.getBody().getResponse().getStatus().equals("paid")) {
//            throw new PaymentServerException("결제 도중에 결제가 취소되었습니다.", ErrorCode.PAYMENT_CANCEL_WHILE_PAYMENT_ERROR);
//        }

        return new PaymentInquiryResultDto(responseEntity);
    }

    private String getPortOneAccessToken() {
        LOGGER.info("[PortOneUtils] AccessToken 획득 시도");

        String path = String.format("/users/getToken");

        URI uri = UriComponentsBuilder
                .fromUriString("https://api.iamport.kr")
                .path(path)
                .encode()
                .build()
                .toUri();

        Charset utf8 = Charset.forName("UTF-8");
        MediaType mediaType = new MediaType("application", "json", utf8);

        PaymentAccessTokenRequestDto paymentAccessTokenRequestDto = new PaymentAccessTokenRequestDto(impKey, impSecret);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = "";
        try {
            jsonBody = objectMapper.writeValueAsString(paymentAccessTokenRequestDto);
        } catch (JsonProcessingException e) {
            throw new InternalServerException("PortOne AccessToken을 가져오는 중에 오류가 발생했습니다.");
        }

        RequestEntity<String> requestEntity = RequestEntity
                .post(uri)
                .contentType(mediaType)
                .body(jsonBody);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        ResponseEntity<PaymentAccessTokenResponseDto> responseEntity = restTemplate.exchange(requestEntity, PaymentAccessTokenResponseDto.class);

        LOGGER.info("[PortOneUtils] AccessToken 획득 성공");

        return responseEntity.getBody().getResponse().getAccessToken();
    }
}
