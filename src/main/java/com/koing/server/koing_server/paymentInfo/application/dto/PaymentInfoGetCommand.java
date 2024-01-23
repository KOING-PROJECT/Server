package com.koing.server.koing_server.paymentInfo.application.dto;

import com.koing.server.koing_server.common.visitor.CommandAcceptor;
import com.koing.server.koing_server.common.visitor.CommandVisitor;
import com.koing.server.koing_server.paymentInfo.domain.PaymentInfo;
import com.koing.server.koing_server.paymentInfo.domain.PaymentStatus;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentInfoGetCommand {

    private String orderId;
}
