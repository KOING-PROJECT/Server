package com.koing.server.koing_server.paymentInfo.application;


import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.PaymentServerException;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.paymentInfo.application.component.IamportClientComponent;
import com.koing.server.koing_server.paymentInfo.application.dto.PaymentInfoCreateCommand;
import com.koing.server.koing_server.paymentInfo.application.dto.PaymentInfoDto;
import com.koing.server.koing_server.paymentInfo.application.exception.NotFoundPaymentInfoException;
import com.koing.server.koing_server.paymentInfo.domain.PaymentInfo;
import com.koing.server.koing_server.paymentInfo.domain.repository.PaymentInfoRepository;
import com.siot.IamportRestClient.exception.IamportResponseException;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

    public SuperResponse createPaymentInfo(final PaymentInfoCreateCommand command) {
        final PaymentInfo paymentInfo = command.toEntity();
        final PaymentInfo savedPaymentInfo = paymentInfoRepository.save(paymentInfo);

        return SuccessResponse.success(SuccessCode.PAYMENT_INFO_CREATE_SUCCESS, savedPaymentInfo.getOrderId());
    }

    public SuperResponse getPaymentInfo(final String orderId) {
        final PaymentInfo paymentInfo = paymentInfoRepository.findPaymentInfoByOrderId(orderId);

        if(paymentInfo == null) {
            throw new NotFoundPaymentInfoException(orderId);
        }

        return SuccessResponse.success(SuccessCode.PAYMENT_INFO_CREATE_SUCCESS, PaymentInfoDto.from(paymentInfo));
    }
}
