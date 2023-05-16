package com.koing.server.koing_server.service.admin.dto;

import com.koing.server.koing_server.domain.review.ReviewToGuide;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@NoArgsConstructor
public class AdminTourReviewDto {

    public AdminTourReviewDto(ReviewToGuide reviewToGuide) {
        this.touristName = reviewToGuide.getWriteTourist().getName();
        this.touristGrade = reviewToGuide.getWriteTourist().getTouristGrade().getGrade();
        this.createdAt = createdAtFormatting(reviewToGuide.getCreatedAt());
        this.reviewPhotos = reviewToGuide.getReviewPhotos();
        this.tourReview = reviewToGuide.getTotalReview();
    }

    private String touristName;
    private String touristGrade;
    private String createdAt;
    private List<String> reviewPhotos;
    private String tourReview;

    private String createdAtFormatting(LocalDateTime createdAt) {
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }

}
