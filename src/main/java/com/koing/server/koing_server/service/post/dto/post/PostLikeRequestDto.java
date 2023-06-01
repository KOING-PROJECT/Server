package com.koing.server.koing_server.service.post.dto.post;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostLikeRequestDto {

    private Long userId;
    private Long postId;

}
