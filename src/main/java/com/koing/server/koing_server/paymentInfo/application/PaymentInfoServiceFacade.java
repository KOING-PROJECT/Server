package com.koing.server.koing_server.paymentInfo.application;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.paymentInfo.application.dto.PaymentInfoCreateCommand;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentInfoServiceFacade {

    private final PaymentInfoService paymentInfoService;
    private final RedissonClient redissonClient;

    public SuperResponse proceedPayment(final PaymentInfoCreateCommand command) {

        final String lockName = "application:tour:" + command.getTourId() + "/" + command.getTourDate();
        RLock rLock = redissonClient.getLock(lockName);

        try {
            System.out.println(Thread.currentThread().getName() + " " + lockName + " " + "tryLock");
            boolean available = rLock.tryLock(3, 30, TimeUnit.SECONDS);
            System.out.println(Thread.currentThread().getName() + " " + lockName + " " + "getLock");
            if (!available) {
//                System.out.println("redisson getLock timeout");
//                throw new IllegalArgumentException();
                return ErrorResponse.error(ErrorCode.NOT_ACCEPTABLE_DURING_PAYMENT_EXCEPTION);
            }

            return paymentInfoService.createPaymentInfo(command);
        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        } finally {
            rLock.unlock();
            System.out.println(Thread.currentThread().getName() + " " + lockName + " " + "releaseLock");
        }
    }
}
