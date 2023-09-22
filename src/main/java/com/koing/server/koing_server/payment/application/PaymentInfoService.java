package com.koing.server.koing_server.payment.application;


import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.PaymentServerException;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.payment.application.component.IamportClientComponent;
import com.koing.server.koing_server.payment.application.dto.PaymentInfoRequestCommand;
import com.koing.server.koing_server.payment.domain.PaymentInfo;
import com.koing.server.koing_server.payment.domain.repository.PaymentInfoRepository;
import com.koing.server.koing_server.payment.ui.dto.PaymentInfoResponseDto;
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

    public SuperResponse<PaymentInfoResponseDto> createPaymentInfo(final PaymentInfoRequestCommand command) {
        final PaymentInfo paymentInfo = command.toEntity();
        final PaymentInfo savedPaymentInfo = paymentInfoRepository.save(paymentInfo);

        return SuccessResponse.success(SuccessCode.PAYMENT_INFO_CREATE_SUCCESS, savedPaymentInfo.getOrderId());
    }
}
