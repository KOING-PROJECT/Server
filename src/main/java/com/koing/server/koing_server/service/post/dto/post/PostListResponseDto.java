package com.koing.server.koing_server.service.post.dto.post;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PostListResponseDto {

    public PostListResponseDto(List<PostResponseDto> posts) {
        this.posts = posts;
    }

    private List<PostResponseDto> posts;

}
