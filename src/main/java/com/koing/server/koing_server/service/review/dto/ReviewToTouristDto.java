package com.koing.server.koing_server.service.review.dto;

import com.koing.server.koing_server.domain.review.ReviewToTourist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewToTouristDto {

    public ReviewToTouristDto(ReviewToTourist reviewToTourist) {
        this.progressReviews = reviewToTourist.getProgressReviews();
        this.touristReviews = reviewToTourist.getTouristReviews();
        this.attachment = reviewToTourist.getAttachment();
    }

    private Set<String> progressReviews;
    private Set<String> touristReviews;
    private double attachment;

}
