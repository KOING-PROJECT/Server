package com.koing.server.koing_server.paymentInfo.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.koing.server.koing_server.config.redis.EmbeddedRedisConfig;
import com.koing.server.koing_server.paymentInfo.application.dto.PaymentInfoCreateCommand;
import com.koing.server.koing_server.paymentInfo.domain.repository.PaymentInfoRepository;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
class PaymentInfoServiceFacadeTest {

    @Autowired
    private PaymentInfoServiceFacade paymentInfoServiceFacade;

    @Autowired
    private PaymentInfoRepository paymentInfoRepository;

    @Autowired
    private PaymentInfoService paymentInfoService;

//    @Test
//    void 동시에_같은_투어_결제정보를_10개_생성한다() throws InterruptedException {
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//        CountDownLatch countDownLatch = new CountDownLatch(10);
//
//        PaymentInfoCreateCommand paymentInfoCreateCommand = new PaymentInfoCreateCommand(1L, 1L, 1L, "20240102");
//
//        for (int i = 0; i < 10; i++) {
//            executorService.submit(() -> {
//                try {
//                    paymentInfoServiceFacade.proceedPayment(paymentInfoCreateCommand);
//                } finally {
//                    countDownLatch.countDown();
//                }
//            });
//        }
//
//        countDownLatch.await();
//        int paymentInfoCount = paymentInfoRepository.findAll().size();
//
//        assertThat(paymentInfoCount).isEqualTo(2);
//    }

    @Test
    void 동시에_같은_투어_결제정보를_10개_생성한다() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch countDownLatch = new CountDownLatch(10);

        PaymentInfoCreateCommand paymentInfoCreateCommand = new PaymentInfoCreateCommand(1L, 1L, 1L, "20240102");

        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                try {
                    paymentInfoService.createPaymentInfo(paymentInfoCreateCommand);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();
        int paymentInfoCount = paymentInfoRepository.findAll().size();

        assertThat(paymentInfoCount).isEqualTo(1);
    }
}