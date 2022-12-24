package com.koing.server.koing_server.domain.tour;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.koing.server.koing_server.common.enums.TourStatus;
import com.koing.server.koing_server.domain.common.AuditingTimeEntity;
import com.koing.server.koing_server.domain.user.User;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "TOUR_APPLICATION_TABLE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TourApplication extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Tour tour;

    @Column(length = 20)
    private String tourDate;

    @ManyToMany(mappedBy = "tourApplication", fetch = FetchType.LAZY)
    private List<User> participants;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TourStatus tourStatus;

    private int maxParticipant;

    public TourApplication(String tourDate, int maxParticipant) {
        this.tour = null;
        this.participants = null;
        this.tourDate = tourDate;
        this.maxParticipant = maxParticipant;
        this.tourStatus = TourStatus.RECRUITMENT;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
        tour.getTourApplications().add(this);
//        Set<TourApplication> tourApplications = tour.getTourApplications();
//        tourApplications.add(this);
//        tour.setTourApplications(tourApplications);
    }
}
