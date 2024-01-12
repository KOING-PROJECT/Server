package com.koing.server.koing_server.service.post.dto.post;

import com.koing.server.koing_server.common.visitor.CommandAcceptor;
import com.koing.server.koing_server.common.visitor.CommandVisitor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostLikeRequestDto implements CommandAcceptor {

    private Long userId;
    private Long postId;

    @Override
    public void accept(final CommandVisitor visitor) {
        visitor.visit(this);
    }
}
