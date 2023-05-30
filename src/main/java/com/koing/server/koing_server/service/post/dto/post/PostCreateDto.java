package com.koing.server.koing_server.service.post.dto.post;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PostCreateDto {

    private Long writerId;
    private String content;
    private List<String> tags;
    private List<String> uploadedPostPhotoUrls;
    private List<String> postPhotoOrders;

}
