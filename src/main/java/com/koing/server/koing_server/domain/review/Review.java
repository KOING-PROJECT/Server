package com.koing.server.koing_server.domain.review;

import com.koing.server.koing_server.domain.common.AuditingTimeEntity;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "REVIEW_TABLE")
public class Review extends AuditingTimeEntity {

    public Review(User sendUser, User receiveUser, Tour relatedTour, Set<String> tourReviews, Set<String> guideReviews, int attachment, String totalReview, Set<String> photos) {
        this.sendUser = sendUser;
        this.receiveUser = receiveUser;
        this.relatedTour = relatedTour;
        this.tourReviews = tourReviews;
        this.guideReviews = guideReviews;
        this.attachment = attachment;
        this.totalReview = totalReview;
        this.photos = photos;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User sendUser;

    @ManyToOne(fetch = FetchType.LAZY)
    private User receiveUser;

    @ManyToOne(fetch = FetchType.LAZY)
    private Tour relatedTour;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(nullable = false, name = "tour_reviews")
    private Set<String> tourReviews;


    @ElementCollection(fetch = FetchType.EAGER)
    @Column(nullable = false, name = "guide_reviews")
    private Set<String> guideReviews;

    private int attachment;

    @Column(length = 500)
    private String totalReview;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(nullable = false, name = "tour_photos")
    private Set<String> photos;

}
