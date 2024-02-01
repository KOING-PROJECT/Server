package com.koing.server.koing_server.paymentInfo.application;

import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.paymentInfo.application.dto.PaymentInfoFacadeSuccessPaymentCommand;
import com.koing.server.koing_server.paymentInfo.application.dto.PaymentInfoWebhookCommand;
import com.koing.server.koing_server.paymentInfo.domain.PaymentInfo;
import com.koing.server.koing_server.service.tour.TourApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentInfoServiceFacade {
    private final PaymentInfoService paymentInfoService;
    private final TourApplicationService tourApplicationService;

    // paymentByClient 응답이 성공인 경우, paymentInfo의 paymentStatus를 업데이트한다.
    // PaymentInfoService의 successPaymentByClient으로 paymentStatus 업데이트,
    // 성공하면 participate 정보를 생성.
    // tourApplicationService의 participateTour로 participate 생
    @Transactional
    public SuperResponse paymentSuccessByClient(final PaymentInfoFacadeSuccessPaymentCommand command) {
        paymentInfoService.successPaymentByClient(command.toPaymentInfoSuccessPaymentCommand());

        return tourApplicationService.participateTour(command.toTourApplicationParticipateDto());
    }

    // Webhook 응답이 성공인 경우, paymentInfo의 webhookStatus를 업데이트한다.
    // PaymentInfoService의 successPaymentByPortOneWebhook으로 webhookStatus 업데이트,
    // 성공하면 portOne으로 단건조회 요청해서 paymentInfo 정보를 업데이트한다.
    // PaymentInfoService의 updatePaymentInfoByPortOne로 paymentInfo 업데이트
    @Transactional
    public SuperResponse successPaymentByPortOneWebhook(final PaymentInfoWebhookCommand paymentInfoWebhookCommand) {
        final PaymentInfo paymentInfo = paymentInfoService.successPaymentByPortOneWebhook(paymentInfoWebhookCommand.toPaymentInfoSuccessPaymentCommand());

        return paymentInfoService.updatePaymentInfoByPortOne(paymentInfoWebhookCommand, paymentInfo);
    }
}
