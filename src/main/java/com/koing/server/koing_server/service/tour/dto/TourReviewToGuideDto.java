package com.koing.server.koing_server.service.tour.dto;

import com.koing.server.koing_server.common.enums.TouristGrade;
import com.koing.server.koing_server.domain.review.ReviewToGuide;
import com.koing.server.koing_server.domain.tour.Tour;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class TourReviewToGuideDto {

    public TourReviewToGuideDto(ReviewToGuide reviewToGuide) {
        if (reviewToGuide.getWriteTourist().getUserOptionalInfo() != null) {
            if (reviewToGuide.getWriteTourist().getUserOptionalInfo().getImageUrls() != null
                    && reviewToGuide.getWriteTourist().getUserOptionalInfo().getImageUrls().size() > 0) {
                this.touristImage = reviewToGuide.getWriteTourist().getUserOptionalInfo().getImageUrls().get(0);
            }
        }
        this.touristGrade = reviewToGuide.getWriteTourist().getTouristGrade();
        this.touristName = reviewToGuide.getWriteTourist().getName();
        if (reviewToGuide.getGuideReviews() != null
                && reviewToGuide.getGuideReviews().size() > 0) {
            this.reviewImage = reviewToGuide.getReviewPhotos().get(0);
        }
        this.reviewDescription = reviewToGuide.getTotalReview();
        this.reviewedDate = dateFormatting(reviewToGuide.getUpdatedAt());
    }

    private String touristImage;
    private TouristGrade touristGrade;
    private String touristName;
    private String reviewImage;
    private String reviewDescription;
    private String reviewedDate;

    private String dateFormatting(LocalDateTime updatedAt) {
        String date = updatedAt.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        return date;
    }

}
