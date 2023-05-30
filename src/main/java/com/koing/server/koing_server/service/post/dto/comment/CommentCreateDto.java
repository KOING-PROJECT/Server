package com.koing.server.koing_server.service.post.dto.comment;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentCreateDto {

    private Long writeUserId;
    private Long postId;
    private String comment;

}
