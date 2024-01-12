package com.koing.server.koing_server.common.visitor;

import com.koing.server.koing_server.paymentInfo.application.dto.PaymentInfoCreateCommand;
import com.koing.server.koing_server.service.post.dto.post.PostLikeRequestDto;

public class LockNameVisitor implements CommandVisitor {
    private static final String REDISSON_LOCK_NAME_PREFIX = "application:tour:";
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
}
