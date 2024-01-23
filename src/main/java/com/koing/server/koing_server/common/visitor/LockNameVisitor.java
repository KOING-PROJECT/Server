package com.koing.server.koing_server.common.visitor;

import com.koing.server.koing_server.paymentInfo.application.dto.PaymentInfoCreateCommand;
import com.koing.server.koing_server.paymentInfo.application.dto.PaymentInfoSuccessPaymentCommand;
import com.koing.server.koing_server.paymentInfo.application.dto.PaymentInfoWebhookCommand;
import com.koing.server.koing_server.service.post.dto.post.PostLikeRequestDto;

public class LockNameVisitor implements CommandVisitor {
    private static final String REDISSON_LOCK_NAME_PREFIX = "application:tour:";
    private static final String REDISSON_LOCK_NAME_PAYMENT_INFO_UPDATE_PREFIX = "application:payment-info:";
    private String lockName;

    public String getLockName() {
        return lockName;
    }

    @Override
    public void visit(final PaymentInfoCreateCommand command) {
        lockName = REDISSON_LOCK_NAME_PREFIX + command.getTourId() + "/" + command.getTourDate();
    }

    @Override
    public void visit(final PostLikeRequestDto command) {
        lockName = REDISSON_LOCK_NAME_PREFIX + command.getPostId();
    }

    @Override
    public void visit(final PaymentInfoSuccessPaymentCommand command) {
        lockName = REDISSON_LOCK_NAME_PAYMENT_INFO_UPDATE_PREFIX + "/" +command.getImpUid();
    }

    @Override
    public void visit(final PaymentInfoWebhookCommand command) {
        lockName = REDISSON_LOCK_NAME_PAYMENT_INFO_UPDATE_PREFIX + "/" +command.getImp_uid();
    }
}
