package com.koing.server.koing_server.service.review.dto;

import com.koing.server.koing_server.domain.review.ReviewToGuide;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class ReviewToGuideDto {

    public ReviewToGuideDto(ReviewToGuide reviewToGuide) {
        this.tourReviews = reviewToGuide.getTourReviews();
        this.guideReviews = reviewToGuide.getGuideReviews();
        this.attachment = reviewToGuide.getAttachment();
        this.totalReview = reviewToGuide.getTotalReview();
        this.reviewPhotos = reviewToGuide.getReviewPhotos();
    }

    private Set<String> tourReviews;
    private Set<String> guideReviews;
    private int attachment;
    private String totalReview;
    private List<String> reviewPhotos;

}
