package com.koing.server.koing_server.payment.ui;


import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.payment.application.PaymentInfoService;
import com.koing.server.koing_server.payment.application.dto.PaymentInfoRequestCommand;
import com.koing.server.koing_server.payment.ui.dto.PaymentInfoResponseDto;
import com.koing.server.koing_server.service.payment.dto.PaymentCreateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Payment", description = "Payment V2 API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/payment-info")
public class PaymentInfoController {

    private final PaymentInfoService paymentInfoService;

    private final Logger LOGGER = LoggerFactory.getLogger(PaymentInfoService.class);

    @Operation(description = "PaymentInfo : 결제 정보를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "PaymentInfo : 결제 정보 생성 성공"),
            @ApiResponse(responseCode = "407", description = "결제 서버와의 통신에서 오류가 발생했습니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping("")
    public SuperResponse<PaymentInfoResponseDto> createPaymentInfo(
            @RequestBody PaymentInfoRequestCommand paymentInfoRequestCommand
    ) {
        LOGGER.info("[PaymentInfoController] 결제 정보 생성 시도");
        SuperResponse paymentInfoCreateResponse;
        try {
            paymentInfoCreateResponse = paymentInfoService.createPaymentInfo(paymentInfoRequestCommand);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[PaymentInfoController] 결제 정보 생성 성공");

        return paymentInfoCreateResponse;
    }
}
