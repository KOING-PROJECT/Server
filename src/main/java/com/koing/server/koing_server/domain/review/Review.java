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

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "REVIEW_TABLE")
public class Review extends AuditingTimeEntity {

    public Review(Long id, String tourReview, String guideReview, int attachment, String totalReview, List<String> photos) {
        this.tourReview = tourReview;
        this.guideReview = guideReview;
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

    @Column(length = 30)
    private String tourReview;

    @Column(length = 30)
    private String guideReview;

    private int attachment;

    @Column(length = 500)
    private String totalReview;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(nullable = false, name = "tour_photos")
    private List<String> photos;

}
