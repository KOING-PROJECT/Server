package com.koing.server.koing_server.domain.review;

import com.koing.server.koing_server.domain.common.AuditingTimeEntity;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.tour.TourParticipant;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.service.review.dto.ReviewToTouristCreateDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "REVIEW_TO_TOURIST_TABLE")
public class ReviewToTourist extends AuditingTimeEntity {

    public ReviewToTourist(ReviewToTouristCreateDto reviewToTouristCreateDto, User writeGuide, TourParticipant relatedTourParticipant) {
        this.writeGuide = writeGuide;
        this.relatedTourParticipant = relatedTourParticipant;
        this.progressReviews = reviewToTouristCreateDto.getProgressReviews();
        this.touristReviews = reviewToTouristCreateDto.getTouristReviews();
        this.attachment = reviewToTouristCreateDto.getAttachment();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User writeGuide;

    @OneToOne(fetch = FetchType.LAZY)
    private TourParticipant relatedTourParticipant;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(nullable = false, name = "progress_reviews")
    private Set<String> progressReviews;


    @ElementCollection(fetch = FetchType.EAGER)
    @Column(nullable = false, name = "tourist_reviews")
    private Set<String> touristReviews;

    private int attachment;

}
