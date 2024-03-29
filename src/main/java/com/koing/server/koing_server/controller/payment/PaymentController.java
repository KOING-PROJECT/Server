package com.koing.server.koing_server.controller.payment;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.service.payment.PaymentService;
import com.koing.server.koing_server.service.payment.dto.PaymentCreateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Payment", description = "Payment API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);
    private final PaymentService paymentService;

    @Operation(description = "Payment : 결제 정보를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment : 결제 정보 생성 성공"),
            @ApiResponse(responseCode = "402", description = "유저 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(responseCode = "402", description = "결제 정보 생성 과정에서 오류가 발생했습니다."),
            @ApiResponse(responseCode = "404", description = "해당 유저를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 투어 신청서를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "407", description = "결제 도중에 결제가 취소되었습니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping("")
    public SuperResponse createPaymentInfo(@RequestBody PaymentCreateDto paymentCreateDto) {
        LOGGER.info("[PaymentController] 결제 정보 생성 시도");
        SuperResponse paymentCreateInfoResponse;
        try {
            paymentCreateInfoResponse = paymentService.savePaymentInfo(paymentCreateDto);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[PaymentController] 결제 정보 생성 성공");

        return paymentCreateInfoResponse;
    }

//    @ApiOperation("Payment : 결제를 합니다.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = 200, description = "Payment : 결제 성공"),
//            @ApiResponse(responseCode = 404, description = "존재하지 않는 페이지 입니다."),
//            @ApiResponse(responseCode = 500, description = "예상치 못한 서버 에러가 발생했습니다.")
//    })
//    @PostMapping("")
//    public SuperResponse payment(@RequestBody PaymentSuccessDto paymentSuccessDto) {
//        LOGGER.info("[PaymentController] 결제 시도");
////        SuperResponse signUpResponse;
////        try {
////            signUpResponse = signService.signUp(signUpRequestDto);
////        } catch (BoilerplateException boilerplateException) {
////            return ErrorResponse.error(boilerplateException.getErrorCode());
////        } catch (Exception exception) {
////            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
////        }
//        LOGGER.info("[PaymentController] 결제 성공");
//
//        return SuccessResponse.success(SuccessCode.SUCCESS, paymentSuccessDto);
//    }
//
//
//    @ApiOperation("Payment : 거래 세부사항을 조회합니다.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = 200, description = "Payment : 거래 세부사항 조회 성공"),
//            @ApiResponse(responseCode = 404, description = "존재하지 않는 페이지 입니다."),
//            @ApiResponse(responseCode = 500, description = "예상치 못한 서버 에러가 발생했습니다.")
//    })
//    @PostMapping("/detail/{impUid}")
//    public SuperResponse getPaymentDetail(@PathVariable("impUid") String impUid) {
//        // test imp_uid = imp_086213154175
//        LOGGER.info("[PaymentController] 결제 세부사항 조회 시도");
//        SuperResponse getPaymentDetailResponse;
//        try {
//            getPaymentDetailResponse = paymentService.getPaymentDetail(impUid);
//        } catch (BoilerplateException boilerplateException) {
//            return ErrorResponse.error(boilerplateException.getErrorCode());
//        } catch (Exception exception) {
//            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
//        }
//        LOGGER.info("[PaymentController] 결제 세부사항 조회 성공");
//
//        return SuccessResponse.success(SuccessCode.SUCCESS, getPaymentDetailResponse);
//    }
//
//
//    @ApiOperation("Payment : 거래를 취소합니다.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = 200, description = "Payment : 거래 취소 성공"),
//            @ApiResponse(responseCode = 404, description = "존재하지 않는 페이지 입니다."),
//            @ApiResponse(responseCode = 500, description = "예상치 못한 서버 에러가 발생했습니다.")
//    })
//    @PostMapping("/cancel")
//    public SuperResponse cancelPayment(@RequestBody PaymentCancelDto paymentCancelDto) {
//        LOGGER.info("[PaymentController] 결제 취소 시도");
//        SuperResponse cancelPaymentResponse;
//        try {
//            cancelPaymentResponse = paymentService.cancelPayment(paymentCancelDto);
//        } catch (BoilerplateException boilerplateException) {
//            return ErrorResponse.error(boilerplateException.getErrorCode());
//        } catch (Exception exception) {
//            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
//        }
//        LOGGER.info("[PaymentController] 결제 취소 성공");
//
//        return SuccessResponse.success(SuccessCode.SUCCESS, cancelPaymentResponse);
//    }

}
