package com.koing.server.koing_server.paymentInfo.application;

import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.paymentInfo.application.dto.PaymentInfoFacadeSuccessPaymentCommand;
import com.koing.server.koing_server.service.tour.TourApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentInfoServiceFacade {

    private final PaymentInfoService paymentInfoService;
    private final TourApplicationService tourApplicationService;
//    private final RedissonClient redissonClient;

    // 결제를 정상적으로 성공한 경우, paymentInfo의 paymentStatus를 업데이트 하고 tourParticipant를 생성한다.
    // PaymentInfoService의 successPaymentByClient로 paymentInfo 상태 업데이트,
    // 성공하면 tourApplicationServie의 participateTour 함수 호출해서 tourParticipant 생성
    @Transactional
    public SuperResponse paymentSuccessByClient(PaymentInfoFacadeSuccessPaymentCommand command) {
        paymentInfoService.successPaymentByClient(command.toPaymentInfoSuccessPaymentCommand());
        return tourApplicationService.participateTour(command.toTourApplicationParticipateDto());
    }

//    public SuperResponse proceedPayment(final PaymentInfoCreateCommand command) {
//
//        final String lockName = "application:tour:" + command.getTourId() + "/" + command.getTourDate();
//        RLock rLock = redissonClient.getLock(lockName);
//
//        try {
//            System.out.println(Thread.currentThread().getName() + " " + lockName + " " + "tryLock");
//            boolean available = rLock.tryLock(3, 30, TimeUnit.SECONDS);
//            System.out.println(Thread.currentThread().getName() + " " + lockName + " " + "getLock");
//            if (!available) {
////                System.out.println("redisson getLock timeout");
////                throw new IllegalArgumentException();
//                return ErrorResponse.error(ErrorCode.NOT_ACCEPTABLE_DURING_PAYMENT_EXCEPTION);
//            }
//
//            return paymentInfoService.createPaymentInfo(command);
//        } catch (InterruptedException e) {
////            throw new RuntimeException(e);
//            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
//        } finally {
//            rLock.unlock();
//            System.out.println(Thread.currentThread().getName() + " " + lockName + " " + "releaseLock");
//        }
//    }
}
