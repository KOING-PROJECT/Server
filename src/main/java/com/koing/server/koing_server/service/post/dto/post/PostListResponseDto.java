package com.koing.server.koing_server.service.post.dto.post;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PostListResponseDto {

    public PostListResponseDto(String loginUserImage, List<PostResponseDto> posts) {
        this.loginUserImage = loginUserImage;
        this.posts = posts;
    }

    public PostListResponseDto(List<PostResponseDto> posts) {
        this.loginUserImage = null;
        this.posts = posts;
    }

    private String loginUserImage;
    private List<PostResponseDto> posts;

}
