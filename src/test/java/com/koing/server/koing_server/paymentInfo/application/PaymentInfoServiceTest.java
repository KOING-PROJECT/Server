package com.koing.server.koing_server.paymentInfo.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.koing.server.koing_server.paymentInfo.application.dto.PaymentInfoCreateCommand;
import com.koing.server.koing_server.paymentInfo.application.dto.PaymentInfoFacadeSuccessPaymentCommand;
import com.koing.server.koing_server.paymentInfo.application.dto.PaymentInfoSuccessPaymentCommand;
import com.koing.server.koing_server.paymentInfo.domain.PaymentInfo;
import com.koing.server.koing_server.paymentInfo.domain.repository.PaymentInfoRepository;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PaymentInfoServiceTest {

    @Autowired
    private PaymentInfoRepository paymentInfoRepository;

    @Autowired
    private PaymentInfoService paymentInfoService;

    @Autowired
    private PaymentInfoServiceFacade paymentInfoServiceFacade;

    @Test
    void 동시에_같은_투어_결제정보의_성공_상태와_웹훅_상태를_업데이트한다() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CountDownLatch countDownLatch = new CountDownLatch(2);

        String orderId = "4061565a-92f7-4908-93c1-fc8278d9c0a4/1/1/1/20240102";
        PaymentInfoSuccessPaymentCommand paymentInfoSuccessPaymentCommand = new PaymentInfoSuccessPaymentCommand(
                "1", orderId
        );

        executorService.submit(() -> {
            try {
                paymentInfoService.successPaymentByClient(paymentInfoSuccessPaymentCommand);
            } finally {
                countDownLatch.countDown();
            }
        });
        executorService.submit(() -> {
            try {
                paymentInfoService.successPaymentByPortOneWebhook(paymentInfoSuccessPaymentCommand);
            } finally {
                countDownLatch.countDown();
            }
        });

        countDownLatch.await();
        PaymentInfo paymentInfo = paymentInfoRepository.findPaymentInfoByOrderId(orderId);

        assertAll(
                () -> assertThat(paymentInfo.getPaymentStatus().toString()).isEqualTo("PAID"),
                () -> assertThat(paymentInfo.getPortOneWebhookStatus().toString()).isEqualTo("PAID")
        );
    }

    @Test
    void 투어_결제정보의_성공_상태를_업데이트하고_락을_해제하고_롤백하고_동시에_웹훅_상태를_업데이트한다() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CountDownLatch countDownLatch = new CountDownLatch(2);

        String orderId = "4061565a-92f7-4908-93c1-fc8278d9c0a4/1/1/1/20240102";
        PaymentInfoSuccessPaymentCommand paymentInfoSuccessPaymentCommand = new PaymentInfoSuccessPaymentCommand(
                "1", orderId
        );

        PaymentInfoFacadeSuccessPaymentCommand paymentInfoFacadeSuccessPaymentCommand = new PaymentInfoFacadeSuccessPaymentCommand(
                "1", orderId, 1L, 1L, "20220202", 1
        );

        executorService.submit(() -> {
            try {
                paymentInfoServiceFacade.paymentSuccessByClient(paymentInfoFacadeSuccessPaymentCommand);
            } finally {
                countDownLatch.countDown();
            }
        });
        executorService.submit(() -> {
            try {
                paymentInfoService.successPaymentByPortOneWebhook(paymentInfoSuccessPaymentCommand);
            } finally {
                countDownLatch.countDown();
            }
        });

        countDownLatch.await();
        PaymentInfo paymentInfo = paymentInfoRepository.findPaymentInfoByOrderId(orderId);

        assertAll(
                () -> assertThat(paymentInfo.getPaymentStatus().toString()).isEqualTo("READY"),
                () -> assertThat(paymentInfo.getPortOneWebhookStatus().toString()).isEqualTo("PAID")
        );
    }
}