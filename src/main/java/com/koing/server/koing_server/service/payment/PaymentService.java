package com.koing.server.koing_server.service.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.DBFailException;
import com.koing.server.koing_server.common.exception.InternalServerException;
import com.koing.server.koing_server.common.exception.NotFoundException;
import com.koing.server.koing_server.common.exception.PaymentServerException;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.domain.payment.Payment;
import com.koing.server.koing_server.domain.payment.repository.PaymentRepository;
import com.koing.server.koing_server.domain.payment.repository.PaymentRepositoryCustom;
import com.koing.server.koing_server.domain.tour.TourApplication;
import com.koing.server.koing_server.domain.tour.repository.TourApplication.TourApplicationRepository;
import com.koing.server.koing_server.domain.tour.repository.TourApplication.TourApplicationRepositoryImpl;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.domain.user.repository.UserRepository;
import com.koing.server.koing_server.domain.user.repository.UserRepositoryImpl;
import com.koing.server.koing_server.service.payment.dto.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.Charset;

@Service
@RequiredArgsConstructor
public class PaymentService {

    @Value("${iamport.imp.secret}")
    private String impSecret;
    @Value("${iamport.imp.key}")
    private String impKey;

    private final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);
    private final PaymentRepository paymentRepository;
    private final PaymentRepositoryCustom paymentRepositoryCustom;
    private final UserRepositoryImpl userRepositoryImpl;
    private final UserRepository userRepository;
    private final TourApplicationRepositoryImpl tourApplicationRepositoryImpl;
    private final TourApplicationRepository tourApplicationRepository;


    @Transactional
    public SuperResponse savePaymentInfo(PaymentCreateDto paymentCreateDto) throws JsonProcessingException {
        LOGGER.info("[PaymentService] 결제 정보를 서버에 저장 시도");

        String accessToken = getPortOneAccessToken();
        LOGGER.info("[PaymentService] PortOne AccessToken 조회 성공");

        PaymentInquiryResultDto paymentInquiryResultDto = getPaymentInfoAtPortOne(accessToken, paymentCreateDto.getTxId());
        LOGGER.info("[PaymentService] PortOne에서 결제 정보 조회 성공");

        SuperResponse createPaymentInfoResponse = createPaymentInfo(paymentCreateDto, paymentInquiryResultDto);

        return createPaymentInfoResponse;
    }

    private SuperResponse createPaymentInfo(PaymentCreateDto paymentCreateDto, PaymentInquiryResultDto paymentInquiryResultDto) {
        LOGGER.info("[PaymentService] 결제 정보 생성 시도");

        User tourist = getUser(paymentInquiryResultDto.getTouristId());
        TourApplication tourApplication = getTourApplication(paymentInquiryResultDto.getTourId(), paymentInquiryResultDto.getTourDate());

        Long guideId = tourApplication.getTour().getCreateUser().getId();

        User guide = getUser(guideId);

        int paymentAmount = paymentInquiryResultDto.getPaymentAmount();
        int previousTotalEarnAmount = guide.getTotalEarnAmount();
        int previousRemainAmount = guide.getCurrentRemainAmount();


        Payment payment = new Payment(paymentCreateDto, paymentInquiryResultDto);
        payment.setPaymentProduct(tourApplication);
        payment.setGuide(guide);
        payment.setTourist(tourist);

        guide.setTotalEarnAmount(previousTotalEarnAmount + paymentAmount);
        guide.setCurrentRemainAmount(previousRemainAmount + paymentAmount);

        User updatedGuide = userRepository.save(guide);

        if (updatedGuide.getTotalEarnAmount() == previousTotalEarnAmount) {
            throw new DBFailException("유저 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요.", ErrorCode.DB_FAIL_UPDATE_USER_FAIL_EXCEPTION);
        }

        LOGGER.info("[PaymentService] 결제 정보 생성");

        Payment savedPayment = paymentRepository.save(payment);

        if (savedPayment == null) {
            throw new DBFailException("결제 정보 생성 과정에서 오류가 발생했습니다.", ErrorCode.DB_FAIL_CREATE_PAYMENT_EXCEPTION);
        }

        LOGGER.info("[PaymentService] 결제 정보 생성 성공");

        return SuccessResponse.success(SuccessCode.PAYMENT_CREATE_SUCCESS, null);
    }

    private String getPortOneAccessToken() throws JsonProcessingException {
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
        String jsonBody = objectMapper.writeValueAsString(paymentAccessTokenRequestDto);

        RequestEntity<String> requestEntity = RequestEntity
                .post(uri)
                .contentType(mediaType)
                .body(jsonBody);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        ResponseEntity<PaymentAccessTokenResponseDto> responseEntity = restTemplate.exchange(requestEntity, PaymentAccessTokenResponseDto.class);

        return responseEntity.getBody().getResponse().getAccessToken();
    }

    private PaymentInquiryResultDto getPaymentInfoAtPortOne(String accessToken, String imp_uid) {
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

        LOGGER.info("[PaymentService] 결제 정보 조회 성공");

//        if (!responseEntity.getBody().getResponse().getStatus().equals("paid")) {
//            throw new PaymentServerException("결제 도중에 결제가 취소되었습니다.", ErrorCode.PAYMENT_CANCEL_WHILE_PAYMENT_ERROR);
//        }

        PaymentInquiryResultDto paymentInquiryResultDto = new PaymentInquiryResultDto(responseEntity);

        return paymentInquiryResultDto;
    }

    private User getUser(Long userId) {
        User user = userRepositoryImpl.loadUserByUserId(userId, true);
        if (user == null) {
            throw new NotFoundException("해당 유저를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_USER_EXCEPTION);
        }

        return user;
    }

    private TourApplication getTourApplication(Long tourId, String tourDate) {
        TourApplication tourApplication = tourApplicationRepositoryImpl.findTourApplicationByTourIdAndTourDate(tourId, tourDate);
        if (tourApplication == null) {
            throw new NotFoundException("해당 투어 신청서를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_TOUR_APPLICATION_EXCEPTION);
        }

        return tourApplication;
    }



//    public SuperResponse getPaymentDetail(String impUid) {
//
//        String accessToken;
//        try {
//            accessToken = getIamportAccessToken();
//        } catch (Exception exception) {
//            throw new PaymentServerException(exception.getMessage(), ErrorCode.PAYMENT_SERVER_CONNECT_ERROR);
//        }
//
//        URI uri = UriComponentsBuilder
//                .fromUriString("https://api.iamport.kr")
//                .path("/payments/{imp_uid}")
//                .encode()
//                .build()
//                .expand(impUid)
//                .toUri();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + accessToken);
//        headers.set("Content-Type", "application/json");
//
//        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        ResponseEntity<PaymentIamportResponseDto> result = restTemplate
//                .exchange(uri, HttpMethod.GET, requestEntity, PaymentIamportResponseDto.class);
//
//        return SuccessResponse.success(SuccessCode.SUCCESS, result);
//    }
//
//    public SuperResponse cancelPayment(PaymentCancelDto paymentCancelDto) {
//
//        String accessToken;
//        try {
//            accessToken = getIamportAccessToken();
//        } catch (Exception exception) {
//            throw new PaymentServerException(exception.getMessage(), ErrorCode.PAYMENT_SERVER_CONNECT_ERROR);
//        }
//
//        URI uri = UriComponentsBuilder
//                .fromUriString("https://api.iamport.kr")
//                .path("/payments/cancel")
//                .encode()
//                .build()
//                .toUri();
//
//        RequestEntity<PaymentCancelDto> requestEntity = RequestEntity
//                .post(uri)
//                .header("Authorization", "Bearer " + accessToken)
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(paymentCancelDto);
//
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        ResponseEntity<PaymentIamportResponseDto> cancelPaymentResult
//                = restTemplate.exchange(requestEntity, PaymentIamportResponseDto.class);
//
//        return SuccessResponse.success(SuccessCode.SUCCESS, cancelPaymentResult);
//    }
//
//
//    private String getIamportAccessToken() {
//
//        URI uri = UriComponentsBuilder
//                .fromUriString("https://api.iamport.kr")
//                .path("/users/getToken")
//                .encode()
//                .build()
//                .toUri();
//
//        PaymentAccessTokenRequestDto data = new PaymentAccessTokenRequestDto(impKey, impSecret);
//
//        RequestEntity<PaymentAccessTokenRequestDto> requestEntity = RequestEntity
//                .post(uri)
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(data);
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        ResponseEntity<PaymentAccessTokenResponseDto> accessTokenResult
//                = restTemplate.exchange(requestEntity, PaymentAccessTokenResponseDto.class);
//
//        return accessTokenResult.getBody().getResponse().getAccessToken();
//    }

}
