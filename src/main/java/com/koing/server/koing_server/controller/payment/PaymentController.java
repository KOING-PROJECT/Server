package com.koing.server.koing_server.controller.payment;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.service.Payment.PaymentService;
import com.koing.server.koing_server.service.Payment.dto.PaymentCancelDto;
import com.koing.server.koing_server.service.Payment.dto.PaymentSuccessDto;
import com.koing.server.koing_server.service.sign.dto.SignUpRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Payment")
@RequestMapping("/payment")
@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);
    private final PaymentService paymentService;

    @ApiOperation("Payment : 결제를 합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Payment : 결제 성공"),
            @ApiResponse(code = 404, message = "존재하지 않는 페이지 입니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping("")
    public SuperResponse payment(@RequestBody PaymentSuccessDto paymentSuccessDto) {
        LOGGER.info("[PaymentController] 결제 시도");
//        SuperResponse signUpResponse;
//        try {
//            signUpResponse = signService.signUp(signUpRequestDto);
//        } catch (BoilerplateException boilerplateException) {
//            return ErrorResponse.error(boilerplateException.getErrorCode());
//        } catch (Exception exception) {
//            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
//        }
        LOGGER.info("[PaymentController] 결제 성공");

        return SuccessResponse.success(SuccessCode.SUCCESS, paymentSuccessDto);
    }


    @ApiOperation("Payment : 거래 세부사항을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Payment : 거래 세부사항 조회 성공"),
            @ApiResponse(code = 404, message = "존재하지 않는 페이지 입니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping("/detail/{impUid}")
    public SuperResponse getPaymentDetail(@PathVariable("impUid") String impUid) {
        // test imp_uid = imp_086213154175
        LOGGER.info("[PaymentController] 결제 세부사항 조회 시도");
        SuperResponse getPaymentDetailResponse;
        try {
            getPaymentDetailResponse = paymentService.getPaymentDetail(impUid);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[PaymentController] 결제 세부사항 조회 성공");

        return SuccessResponse.success(SuccessCode.SUCCESS, getPaymentDetailResponse);
    }


    @ApiOperation("Payment : 거래를 취소합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Payment : 거래 취소 성공"),
            @ApiResponse(code = 404, message = "존재하지 않는 페이지 입니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping("/cancel")
    public SuperResponse cancelPayment(@RequestBody PaymentCancelDto paymentCancelDto) {
        LOGGER.info("[PaymentController] 결제 취소 시도");
        SuperResponse cancelPaymentResponse;
        try {
            cancelPaymentResponse = paymentService.cancelPayment(paymentCancelDto);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[PaymentController] 결제 취소 성공");

        return SuccessResponse.success(SuccessCode.SUCCESS, cancelPaymentResponse);
    }

}
