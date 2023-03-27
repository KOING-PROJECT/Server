package com.koing.server.koing_server.service.keyword.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class KeywordResponseListDto {

    public KeywordResponseListDto(List<KeywordResponseDto> keywords) {
        this.keywords = keywords;
    }

    private List<KeywordResponseDto> keywords;

}
