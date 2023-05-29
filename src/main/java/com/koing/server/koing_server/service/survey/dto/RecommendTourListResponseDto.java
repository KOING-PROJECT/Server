package com.koing.server.koing_server.service.survey.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class RecommendTourListResponseDto {

    public RecommendTourListResponseDto(List<String> keywords, List<RecommendTourResponseDto> recommendTours) {
        this.keywords = keywords;
        this.recommendTours = recommendTours;
    }

    private List<String> keywords;
    private List<RecommendTourResponseDto> recommendTours;

}
