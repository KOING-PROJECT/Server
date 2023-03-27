package com.koing.server.koing_server.service.review.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewTouristReviewCheckDto {

    private ReviewToGuideDto sendReview;
    private ReviewToTouristDto receiveReview;

}
