package com.koing.server.koing_server.paymentInfo.application;


import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.NotAcceptableException;
import com.koing.server.koing_server.common.exception.PaymentServerException;
import com.koing.server.koing_server.common.redisson.RedissonLock;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.domain.tour.repository.TourApplication.TourApplicationRepositoryImpl;
import com.koing.server.koing_server.paymentInfo.application.component.IamportClientComponent;
import com.koing.server.koing_server.paymentInfo.application.dto.PaymentInfoCreateCommand;
import com.koing.server.koing_server.paymentInfo.application.dto.PaymentInfoDto;
import com.koing.server.koing_server.paymentInfo.application.exception.NotFoundPaymentInfoException;
import com.koing.server.koing_server.paymentInfo.domain.PaymentInfo;
import com.koing.server.koing_server.paymentInfo.domain.repository.PaymentInfoRepository;
import com.siot.IamportRestClient.exception.IamportResponseException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentInfoService {

    private final IamportClientComponent iamportClientComponent;
    private final PaymentInfoRepository paymentInfoRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(PaymentInfoService.class);

    private String getPortOneAccessToken() {
        LOGGER.info("[PaymentV2Service] 포트원 accessToken 요청 시도");
        try {
            LOGGER.info("[PaymentV2Service] 포트원 accessToken 요청 성공");
            return iamportClientComponent.getAuth().getResponse().getToken();
        } catch (IamportResponseException | IOException e) {
            throw new PaymentServerException("결제 서버와의 통신에서 오류가 발생했습니다.", ErrorCode.PAYMENT_SERVER_CONNECT_ERROR);
        }
    }

    @RedissonLock()
    public SuperResponse createPaymentInfo(final PaymentInfoCreateCommand command) {
        LOGGER.info("[PaymentV2Service] PaymentInfo 생성 시도");

        final PaymentInfo paymentInfo = command.toEntity();

        final PaymentInfo getPaymentInfo = findPaymentInfoByTourIdAndTourDate(paymentInfo);

        if (getPaymentInfo != null) {
            return ErrorResponse.error(ErrorCode.NOT_ACCEPTABLE_ALREADY_SOLD_OUT_EXCEPTION);
        }

        final PaymentInfo savedPaymentInfo = paymentInfoRepository.save(paymentInfo);

        LOGGER.info("[PaymentV2Service] PaymentInfo 생성 성공");

        return SuccessResponse.success(SuccessCode.PAYMENT_INFO_CREATE_SUCCESS, savedPaymentInfo.getOrderId());
    }

    public SuperResponse getPaymentInfo(final String orderId) {
        final PaymentInfo paymentInfo = findPaymentInfoByOrderId(orderId);

        return SuccessResponse.success(SuccessCode.PAYMENT_INFO_CREATE_SUCCESS, PaymentInfoDto.from(paymentInfo));
    }

    // 결제 도중에 취소 하는 경우 해당 paymentInfo 의 paymentStatus 를 CANCEL 로 수정 해야함.
//    public SuperResponse cancelPayment(final String orderId) {
//
//    }

    private PaymentInfo findPaymentInfoByOrderId(final String orderId) {
        final PaymentInfo paymentInfo = paymentInfoRepository.findPaymentInfoByOrderId(orderId);

        if(paymentInfo == null) {
            throw new NotFoundPaymentInfoException(orderId);
        }

        return paymentInfo;
    }

    private PaymentInfo findPaymentInfoByTourIdAndTourDate(final PaymentInfo newPaymentInfo) {
        final PaymentInfo paymentInfo = paymentInfoRepository.findPaymentInfoByTourIdAndTourDate(newPaymentInfo.getTourId(), newPaymentInfo.getTourDate());

        return paymentInfo;
    }
}
