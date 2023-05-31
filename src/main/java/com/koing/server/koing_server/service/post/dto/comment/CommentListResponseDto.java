package com.koing.server.koing_server.service.post.dto.comment;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CommentListResponseDto {

    public CommentListResponseDto(List<CommentResponseDto> comments) {
        this.comments = comments;
    }

    private List<CommentResponseDto> comments;

}
