package com.koing.server.koing_server.domain.tour;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.koing.server.koing_server.common.enums.CreateStatus;
import com.koing.server.koing_server.common.enums.TourStatus;
import com.koing.server.koing_server.domain.common.AuditingTimeEntity;
import com.koing.server.koing_server.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TOUR_TABLE")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Tour extends AuditingTimeEntity {

    @Builder
    public Tour(String title, User createUser, String description,
                Set<TourCategory> tourCategories, String thumbnail,
                int participant, int tourPrice, boolean hasLevy,
                TourStatus tourStatus, Set<HashMap<String, List>> additionalPrice,
                CreateStatus createStatus
                ) {
        this.title = title;
        this.createUser = createUser;
        this.description = description;
        this.tourCategories = tourCategories;
        this.thumbnail = thumbnail;
        this.participant = participant;
        this.tourPrice = tourPrice;
        this.hasLevy = hasLevy;
        this.tourStatus = tourStatus;
        this.additionalPrice = additionalPrice;
        this.createStatus = createStatus;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    @JsonManagedReference
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY) // 연관관계 주인
    private User createUser;

    @Column(length = 30, unique = true, nullable = false)
    private String title;

    @Column(length = 300, nullable = false)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<TourCategory> tourCategories;

    @Column(length = 30, nullable = false)
    private String thumbnail;

    @Column(nullable = false)
    private int participant;

    @Column(nullable = false)
    private int tourPrice;

    private boolean hasLevy;

    @ElementCollection(fetch = FetchType.LAZY)
    @Column(name = "additional_price")
    private Set<HashMap<String, List>> additionalPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TourStatus tourStatus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tour", orphanRemoval = true)
    @JsonIgnore
    private Set<TourApplication> tourApplications;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<User> pressLikeUsers;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private TourSchedule tourSchedule;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    private TourSurvey tourSurvey;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CreateStatus createStatus;

    public void setTourSchedule(TourSchedule tourSchedule) {
        this.tourSchedule = tourSchedule;
        tourSchedule.setTour(this);
    }

    public void setTourCategories(Set<TourCategory> tourCategories) {
        this.tourCategories = tourCategories;
        for (TourCategory tourCategory : tourCategories) {
            tourCategory.setTour(this);
        }
    }

    public void deleteTourCategories(Set<TourCategory> tourCategories) {
        this.tourCategories = null;
        for (TourCategory tourCategory : tourCategories) {
            tourCategory.deleteTour(this);
        }
    }

    public void setTourSurvey(TourSurvey tourSurvey) {
        this.tourSurvey = tourSurvey;
    }

}
