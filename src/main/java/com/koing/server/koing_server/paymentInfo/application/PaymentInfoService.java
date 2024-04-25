package com.koing.server.koing_server.paymentInfo.application;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.DBFailException;
import com.koing.server.koing_server.common.exception.NotAcceptableException;
import com.koing.server.koing_server.common.exception.NotFoundException;
import com.koing.server.koing_server.common.exception.PaymentServerException;
import com.koing.server.koing_server.common.redisson.RedissonLock;
import com.koing.server.koing_server.common.redisson.RedissonLockWithNoTransaction;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.domain.payment.Payment;
import com.koing.server.koing_server.domain.tour.TourApplication;
import com.koing.server.koing_server.domain.tour.repository.TourApplication.TourApplicationRepositoryImpl;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.domain.user.repository.UserRepositoryImpl;
import com.koing.server.koing_server.paymentInfo.application.component.IamportClientComponent;
import com.koing.server.koing_server.paymentInfo.application.dto.PaymentInfoCancelPaymentCommand;
import com.koing.server.koing_server.paymentInfo.application.dto.PaymentInfoCreateCommand;
import com.koing.server.koing_server.paymentInfo.application.dto.PaymentInfoDto;
import com.koing.server.koing_server.paymentInfo.application.dto.PaymentInfoGetCommand;
import com.koing.server.koing_server.paymentInfo.application.dto.PaymentInfoSuccessPaymentCommand;
import com.koing.server.koing_server.paymentInfo.application.dto.PaymentInfoWebhookCommand;
import com.koing.server.koing_server.paymentInfo.application.exception.NotFoundPaymentInfoException;
import com.koing.server.koing_server.paymentInfo.application.exception.PaymentFailException;
import com.koing.server.koing_server.paymentInfo.application.utils.PortOneUtils;
import com.koing.server.koing_server.paymentInfo.domain.PaymentInfo;
import com.koing.server.koing_server.paymentInfo.domain.PaymentStatus;
import com.koing.server.koing_server.paymentInfo.domain.PortOneWebhookStatus;
import com.koing.server.koing_server.paymentInfo.domain.repository.PaymentInfoRepository;
import com.koing.server.koing_server.service.payment.dto.PaymentInquiryResultDto;
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

    private final PaymentInfoRepository paymentInfoRepository;
    private final PortOneUtils portOneUtils;
    private final UserRepositoryImpl userRepositoryImpl;
    private final TourApplicationRepositoryImpl tourApplicationRepositoryImpl;

    private final Logger LOGGER = LoggerFactory.getLogger(PaymentInfoService.class);

    @RedissonLock()
    public SuperResponse createPaymentInfo(final PaymentInfoCreateCommand command) {
        LOGGER.info("[PaymentV2Service] PaymentInfo 생성 시도");

        final PaymentInfo paymentInfo = command.toEntity();

        final PaymentInfo getPaymentInfo = findPaymentInfoByTourIdAndTourDate(paymentInfo);

        if (getPaymentInfo != null) {
            // 취소한 결제는 조회 하지 않음
            // 따라서 조회된 결제는 원래 정상적으로 결제가 되었어야 함.
            // 따라서 아래에서 PaymentStatus가 READY 상태가 아니거나, PortOneWebhookStatus가 STANDBY 상태가 아니면 결제 완료된 것으로 판단
            if (!getPaymentInfo.getPaymentStatus().equals(PaymentStatus.READY)
                    || !getPaymentInfo.getPortOneWebhookStatus().equals(PortOneWebhookStatus.STANDBY)) {
                return ErrorResponse.error(ErrorCode.NOT_ACCEPTABLE_ALREADY_SOLD_OUT_EXCEPTION);
            }
            else {
                getPaymentInfo.delete();
                paymentInfoRepository.save(getPaymentInfo);
            }
        }

        final PaymentInfo savedPaymentInfo = paymentInfoRepository.save(paymentInfo);

        LOGGER.info("[PaymentV2Service] PaymentInfo 생성 성공");

        return SuccessResponse.success(SuccessCode.PAYMENT_INFO_CREATE_SUCCESS, savedPaymentInfo.getOrderId());
    }

    public SuperResponse getPaymentInfo(final PaymentInfoGetCommand command) {
        final PaymentInfo paymentInfo = findPaymentInfoByOrderId(command.getOrderId());

        return SuccessResponse.success(SuccessCode.PAYMENT_INFO_CREATE_SUCCESS, PaymentInfoDto.from(paymentInfo));
    }

    @RedissonLock
    public void successPaymentByClient(final PaymentInfoSuccessPaymentCommand command) {
        LOGGER.info("[PaymentV2Service] 결제 성공 정보 업데이트 시도");

        final String orderId = command.getMerchantUid();

        final PaymentInfo paymentInfo = findPaymentInfoByOrderId(orderId);

        paymentInfo.successPaymentByClient(command.getImpUid());

        paymentInfoRepository.save(paymentInfo);

        LOGGER.info("[PaymentV2Service] 결제 성공 정보 업데이트 성공");
    }

    // PortOneWebhook endPoint
    @RedissonLock
    public SuperResponse updatePaymentInfoByPortOne(final PaymentInfoWebhookCommand command, final PaymentInfo paymentInfo) {

        LOGGER.info("[PaymentV2Service] 결제 정보 업데이트 시도");

        PaymentInquiryResultDto paymentInquiryResultDto = portOneUtils.getPaymentInfoAtPortOne(command.getImp_uid());

        paymentInfo.updatePaymentInfo(paymentInquiryResultDto);

        User tourist = getUser(paymentInquiryResultDto.getTouristId());

        TourApplication tourApplication = getTourApplication(paymentInquiryResultDto.getTourId(), paymentInquiryResultDto.getTourDate());

        Long guideId = tourApplication.getTour().getCreateUser().getId();

        User guide = getUser(guideId);

        paymentInfo.setPaymentProduct(tourApplication);
        paymentInfo.setGuide(guide);
        paymentInfo.setTourist(tourist);

        paymentInfoRepository.save(paymentInfo);

        LOGGER.info("[PaymentV2Service] 결제 정보 업데이트 성공");

        return SuccessResponse.success(SuccessCode.UPDATE_PAYMENT_INFO_SUCCESS, null);
    }


    // successPaymentByPortOneWebhook
    @RedissonLock() //
    public PaymentInfo successPaymentByPortOneWebhook(final PaymentInfoSuccessPaymentCommand command) {
        LOGGER.info("[PaymentV2Service] 포트원 결제 상태 정보 성공으로 업데이트 시도");

        final String orderId = command.getMerchantUid();

        final PaymentInfo paymentInfo = findPaymentInfoByOrderId(orderId);

        paymentInfo.successPaymentByPortOne(command.getImpUid());

        final PaymentInfo savedPaymentInfo = paymentInfoRepository.save(paymentInfo);

        LOGGER.info("[PaymentV2Service] 포트원 결제 상태 정보 성공으로 업데이트 성공");

        return savedPaymentInfo;
    }

    // 결제 도중에 취소 하는 경우 해당 paymentInfo 의 paymentStatus 를 CANCEL 로 수정 해야함.
    public SuperResponse cancelPaymentByClient(final PaymentInfoCancelPaymentCommand command) {
        LOGGER.info("[PaymentV2Service] 결제 취소 정보 업데이트 시도");

        final String orderId = command.getOrderId();

        final PaymentInfo paymentInfo = findPaymentInfoByOrderId(orderId);

        paymentInfo.cancelPaymentByClient();

        final PaymentInfo savedPaymentInfo = paymentInfoRepository.save(paymentInfo);

        LOGGER.info("[PaymentV2Service] 결제 취소 정보 업데이트 성공");

        return SuccessResponse.success(SuccessCode.UPDATE_PAYMENT_INFO_TO_CANCEL, null);
    }

    private PaymentInfo findPaymentInfoByOrderId(final String orderId) {
        final PaymentInfo paymentInfo = paymentInfoRepository.findPaymentInfoByOrderId(orderId);

        if(paymentInfo == null) {
            throw new NotFoundException("해당 결제 정보를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_PAYMENT_INFO_EXCEPTION);
        }

        return paymentInfo;
    }

    private PaymentInfo findPaymentInfoByTourIdAndTourDate(final PaymentInfo newPaymentInfo) {
        final PaymentInfo paymentInfo = paymentInfoRepository.findPaymentInfoByTourIdAndTourDate(newPaymentInfo.getTourId(), newPaymentInfo.getTourDate());

        return paymentInfo;
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
}
