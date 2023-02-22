package com.koing.server.koing_server.service.review.dto;

import com.koing.server.koing_server.common.enums.TouristGrade;
import com.koing.server.koing_server.domain.tour.TourParticipant;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ReviewGuideMyPageListResponseDto {

    public ReviewGuideMyPageListResponseDto(List<ReviewGuideMyPageResponseDto> reviewGuideMyPageResponseDtos) {
        this.tourists = reviewGuideMyPageResponseDtos;
    }

    private List<ReviewGuideMyPageResponseDto> tourists;

}
