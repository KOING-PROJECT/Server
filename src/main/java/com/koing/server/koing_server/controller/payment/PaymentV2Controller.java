package com.koing.server.koing_server.controller.payment;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Payment", description = "Payment API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/payment")
public class PaymentV2Controller {
}
