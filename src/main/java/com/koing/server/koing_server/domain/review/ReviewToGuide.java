package com.koing.server.koing_server.domain.review;

import com.koing.server.koing_server.domain.common.AbstractRootEntity;
import com.koing.server.koing_server.domain.tour.TourApplication;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.service.review.dto.ReviewToGuideCreateDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "REVIEW_TO_GUIDE_TABLE")
public class ReviewToGuide extends AbstractRootEntity {

    public ReviewToGuide(ReviewToGuideCreateDto reviewToGuideCreateDto, User writeTourist, List<String> reviewPhotos) {
        this.writeTourist = writeTourist;
        this.relatedTourApplication = null;
        this.tourReviews = reviewToGuideCreateDto.getTourReviews();
        this.guideReviews = reviewToGuideCreateDto.getGuideReviews();
        this.attachment = reviewToGuideCreateDto.getAttachment();
        this.totalReview = reviewToGuideCreateDto.getTotalReview();
        this.reviewPhotos = reviewPhotos;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User writeTourist;

    @ManyToOne(fetch = FetchType.LAZY)
    private TourApplication relatedTourApplication;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "tour_reviews")
    private Set<String> tourReviews;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(nullable = false, name = "guide_reviews")
    private Set<String> guideReviews;

    private double attachment;

    @Column(length = 500)
    private String totalReview;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(nullable = false, name = "review_photos")
    private List<String> reviewPhotos;

    public void setRelatedTourApplication(TourApplication tourApplication, Long touristId) {
        this.relatedTourApplication = tourApplication;

        if (tourApplication.getReviewsToGuide() == null) {
            tourApplication.setReviewsToGuide(new HashSet<>());
        }

        tourApplication.getReviewsToGuide().add(this);

        if (tourApplication.getReviewedTouristId() == null) {
            tourApplication.setReviewedTouristId(new HashSet<>());
        }

        tourApplication.getReviewedTouristId().add(touristId);
    }
}
