package com.koing.server.koing_server.domain.tour;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.koing.server.koing_server.common.enums.ProgressStatus;
import com.koing.server.koing_server.common.enums.TourApplicationStatus;
import com.koing.server.koing_server.domain.common.AbstractRootEntity;
import com.koing.server.koing_server.domain.payment.Payment;
import com.koing.server.koing_server.domain.review.ReviewToGuide;
import com.koing.server.koing_server.paymentInfo.domain.PaymentInfo;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "TOUR_APPLICATION_TABLE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TourApplication extends AbstractRootEntity {

    public TourApplication(String tourDate, int maxParticipant) {
        this.tour = null;
        this.tourParticipants = new ArrayList<>();
        this.tourDate = tourDate;
        this.maxParticipant = maxParticipant;
        this.tourApplicationStatus = TourApplicationStatus.RECRUITMENT;
        this.previousTourApplicationStatus = TourApplicationStatus.NONE;
        this.currentParticipants = 0;
        this.reviewsToGuide = new HashSet<>();
        this.reviewedTouristId = new HashSet<>();
        this.guideProgressStatus = ProgressStatus.READY;
        this.isExceed = false;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Tour tour;

    @Column(length = 20)
    private String tourDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tourApplication")
    private List<TourParticipant> tourParticipants;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TourApplicationStatus tourApplicationStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TourApplicationStatus previousTourApplicationStatus;

    private int maxParticipant;

    private int currentParticipants;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "relatedTourApplication")
    private Set<ReviewToGuide> reviewsToGuide;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "reviewed_tourist")
    private Set<Long> reviewedTouristId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProgressStatus guideProgressStatus;

    private boolean isExceed;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "paymentProduct")
    private Set<PaymentInfo> payments;

    public void setTour(Tour tour) {
        this.tour = tour;
        if (tour.getTourApplications() == null) {
            Set<TourApplication> tourApplications = new HashSet<>();
            tourApplications.add(this);
            tour.setTourApplications(tourApplications);
        }
        else {
            tour.getTourApplications().add(this);
        }
//        Set<TourApplication> tourApplications = tour.getTourApplications();
//        tourApplications.add(this);
//        tour.setTourApplications(tourApplications);
    }

    public void deleteTour(Tour tour) {
        if (tour.getTourApplications() != null) {
            if (tour.getTourApplications().contains(this)) {
                tour.getTourApplications().remove(this);
            }
        }

        this.tour = null;
    }
}
