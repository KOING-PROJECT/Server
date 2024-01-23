package com.koing.server.koing_server.paymentInfo.application.dto;

import com.koing.server.koing_server.common.visitor.CommandAcceptor;
import com.koing.server.koing_server.common.visitor.CommandVisitor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInfoWebhookCommand implements CommandAcceptor {

    private String imp_uid; // impUid
    private String merchant_uid; // orderId
    private String status;

    @Override
    public void accept(final CommandVisitor visitor) {
        visitor.visit(this);
    }

    public PaymentInfoSuccessPaymentCommand toPaymentInfoSuccessPaymentCommand() {
        return new PaymentInfoSuccessPaymentCommand(imp_uid, merchant_uid);
    }
}
