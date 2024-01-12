package com.koing.server.koing_server.common.visitor;

import com.koing.server.koing_server.paymentInfo.application.dto.PaymentInfoCreateCommand;
import com.koing.server.koing_server.service.post.dto.post.PostLikeRequestDto;

public interface CommandVisitor {
    void visit(PaymentInfoCreateCommand command);
    void visit(PostLikeRequestDto command);
}
