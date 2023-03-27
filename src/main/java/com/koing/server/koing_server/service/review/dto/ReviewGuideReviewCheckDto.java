package com.koing.server.koing_server.service.review.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ReviewGuideReviewCheckDto {

    private ReviewToTouristDto sendReview;
    private ReviewToGuideDto receiveReview;

}
