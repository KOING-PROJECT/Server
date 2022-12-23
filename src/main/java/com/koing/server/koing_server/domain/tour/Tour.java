package com.koing.server.koing_server.domain.tour;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.koing.server.koing_server.common.enums.TourStatus;
import com.koing.server.koing_server.domain.common.AuditingTimeEntity;
import com.koing.server.koing_server.domain.user.GenderType;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.domain.user.UserOptionalInfo;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TOUR_TABLE")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Tour extends AuditingTimeEntity {

    @Builder
    public Tour(String title, User createUser, String description
            , Set<TourCategory> tourCategories, String thumbnail, int participant
            , int tourPrice, boolean hasLevy, TourStatus tourStatus, Set<HashMap<String, List>> additionalPrice) {
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
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY) // 연관관계 주인
    private User createUser;

    @Column(length = 30, unique = true, nullable = false)
    private String title;

    @Column(length = 300, nullable = false)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<TourCategory> tourCategories;

    @Column(length = 30, nullable = false)
    private String thumbnail;

    @Column(nullable = false)
    private int participant;

    @Column(nullable = false)
    private int tourPrice;

    private boolean hasLevy;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "additional_price")
    private Set<HashMap<String, List>> additionalPrice;

    @Column(nullable = false)
    private TourStatus tourStatus;

    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnore
    private TourApplication tourApplication;

}
