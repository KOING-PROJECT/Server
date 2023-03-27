package com.koing.server.koing_server.service.review.dto;

import com.koing.server.koing_server.common.enums.TouristGrade;
import com.koing.server.koing_server.domain.tour.TourParticipant;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewGuideMyPageResponseDto {

    public ReviewGuideMyPageResponseDto(TourParticipant tourParticipant, boolean hasReview) {
        if (tourParticipant.getParticipant() != null) {
            this.touristId = tourParticipant.getParticipant().getId();
            this.touristName = tourParticipant.getParticipant().getName();
            this.touristGrade = tourParticipant.getParticipant().getTouristGrade();

            if (tourParticipant.getParticipant().getUserOptionalInfo() != null) {
                this.touristProfileImage = tourParticipant.getParticipant().getUserOptionalInfo().getImageUrls().get(0);
            }

            this.hasReview = hasReview;
        }
    }

    private Long touristId;
    private String touristName;
    private String touristProfileImage;
    private TouristGrade touristGrade;
    private boolean hasReview;

}
