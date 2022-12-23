package com.koing.server.koing_server.domain.tour;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.koing.server.koing_server.common.enums.TourStatus;
import com.koing.server.koing_server.domain.common.AuditingTimeEntity;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.service.tour.dto.TourApplicationDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "TOUR_APPLICATION_TABLE")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TourApplication extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "tourApplication")
    private Tour tour;

    @ManyToMany
    @JoinTable(name = "user_application")
    private List<User> users;

    @Column(nullable = false)
    private TourStatus tourStatus;

    public TourApplication() {
        this.tour = null;
        this.users = null;
        this.tourStatus = TourStatus.RECRUITMENT;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
        tour.setTourApplication(this);
    }
}
