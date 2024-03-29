package com.koing.server.koing_server.domain.tour;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.koing.server.koing_server.common.enums.CreateStatus;
import com.koing.server.koing_server.common.enums.TourStatus;
import com.koing.server.koing_server.domain.common.AbstractRootEntity;
import com.koing.server.koing_server.domain.image.Thumbnail;
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
public class Tour extends AbstractRootEntity {

    @Builder
    public Tour(String title, User createUser, String description,
                Set<TourCategory> tourCategories, Set<String> tourDetailTypes,
                List<Thumbnail> thumbnails, int participant, int tourPrice,
                boolean hasLevy, int temporarySavePage,
                TourStatus tourStatus, Set<HashMap<String, List>> additionalPrice,
                CreateStatus createStatus, Set<String> exceedTourDate
                ) {
        this.title = title;
        this.createUser = createUser;
        this.description = description;
        this.tourCategories = tourCategories;
        this.tourDetailTypes = tourDetailTypes;
        this.thumbnails = thumbnails;
        this.participant = participant;
        this.tourPrice = tourPrice;
        this.hasLevy = hasLevy;
        this.temporarySavePage = temporarySavePage;
        this.tourStatus = tourStatus;
        this.additionalPrice = additionalPrice;
        this.createStatus = createStatus;
        this.exceedTourDate = exceedTourDate;
        this.recentStartedTourDate = "";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    @JsonManagedReference
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY) // 연관관계 주인
    private User createUser;

    @Column(length = 30, nullable = false)
    private String title;

    @Column(length = 300, nullable = false)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<TourCategory> tourCategories;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(nullable = false, name = "detailTypes")
    private Set<String> tourDetailTypes;

//    @ElementCollection(fetch = FetchType.EAGER)
//    @Column(nullable = false, name = "thumbnails")
//    private List<String> thumbnails;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ownedTour")
    private List<Thumbnail> thumbnails;

    @Column(nullable = false)
    private int participant;

    @Column(nullable = false)
    private int tourPrice;

    private boolean hasLevy;

    private int temporarySavePage;

    @ElementCollection(fetch = FetchType.LAZY)
    @Column(name = "additional_price")
    private Set<HashMap<String, List>> additionalPrice;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TourStatus tourStatus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tour", orphanRemoval = true)
    @JsonIgnore
    private Set<TourApplication> tourApplications;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "like_tour_table",
            joinColumns = @JoinColumn(name = "tour_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> pressLikeUsers;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private TourSchedule tourSchedule;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    private TourSurvey tourSurvey;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CreateStatus createStatus;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "exceed_tour_date")
    private Set<String> exceedTourDate;

    private String recentStartedTourDate;

    public void setTourSchedule(TourSchedule tourSchedule) {
        this.tourSchedule = tourSchedule;
        tourSchedule.setTour(this);
    }

    public void setTourSurvey(TourSurvey tourSurvey) {
        this.tourSurvey = tourSurvey;
        tourSurvey.setTour(this);
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

    public void pressLikeTour(User user) {
        if (this.pressLikeUsers == null || !(this.pressLikeUsers.contains(user))) {
            addLikeUsers(user);
        }
        else {
            deleteLikeUsers(user);
        }
    }

    private void addLikeUsers(User user) {
        if (this.pressLikeUsers == null) {
            this.pressLikeUsers = new HashSet<>();
        }
        this.pressLikeUsers.add(user);

        if (user.getPressLikeTours() == null) {
            Set<Tour> pressListTours = new HashSet<>();
            user.setPressLikeTours(pressListTours);
        }
        user.getPressLikeTours().add(this);
    }

    private void deleteLikeUsers(User user) {
        this.pressLikeUsers.remove(user);

        if (user.getPressLikeTours() != null) {
            user.getPressLikeTours().remove(this);
        }
    }

    public boolean checkCategories(List<String> tourCategories) {

        for (TourCategory tourCategory : this.tourCategories) {
            if (tourCategories.contains(tourCategory.getCategoryName())) {
                return true;
            }
        }

        return false;
    }

    public void deleteExceedDate(String exceedTourDate) {
        if (this.exceedTourDate.contains(exceedTourDate)) {
            this.exceedTourDate.remove(exceedTourDate);
        }
    }

}
