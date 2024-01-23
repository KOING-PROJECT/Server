package com.koing.server.koing_server.paymentInfo.application.dto;

import com.koing.server.koing_server.common.visitor.CommandAcceptor;
import com.koing.server.koing_server.common.visitor.CommandVisitor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInfoSuccessPaymentCommand implements CommandAcceptor {

    private String impUid; // impUid
    private String merchantUid; // orderId

    @Override
    public void accept(final CommandVisitor visitor) {
        visitor.visit(this);
    }
}
