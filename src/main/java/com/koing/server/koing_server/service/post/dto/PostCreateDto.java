package com.koing.server.koing_server.service.post.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PostCreateDto {

    private Long writerId;
    private String description;
    private List<String> tags;

}
