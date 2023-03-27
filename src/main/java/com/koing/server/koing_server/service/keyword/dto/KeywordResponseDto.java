package com.koing.server.koing_server.service.keyword.dto;

import com.koing.server.koing_server.domain.keyword.Keyword;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KeywordResponseDto {

    public KeywordResponseDto(Keyword keyword) {
        this.keyword = keyword.getKeyword();
        this.searchAmount = keyword.getSearchAmount();
    }

    private String keyword;
    private int searchAmount;

}
