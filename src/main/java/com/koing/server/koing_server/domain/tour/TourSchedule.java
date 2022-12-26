package com.koing.server.koing_server.domain.tour;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.koing.server.koing_server.common.enums.CreateStatus;
import com.koing.server.koing_server.domain.common.AuditingTimeEntity;
import com.koing.server.koing_server.service.tour.dto.TourScheduleCreateDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TOUR_SCHEDULE_TABLE")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TourSchedule extends AuditingTimeEntity {

    public TourSchedule(TourScheduleCreateDto tourScheduleCreateDto, CreateStatus createStatus) {
        this.tour = null;
        this.tourDates = tourScheduleCreateDto.getTourDates();
        this.dateNegotiation = tourScheduleCreateDto.isDateNegotiation();
        this.tourDetailScheduleList = null;
        this.createStatus = createStatus;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    private Tour tour;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(length = 20, nullable = false, name = "tour_dates")
    private Set<String> tourDates;

    @Column(nullable = false, name = "date_negotiation")
    private boolean dateNegotiation = false;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CreateStatus createStatus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tourSchedule", orphanRemoval = true)
    private List<TourDetailSchedule> tourDetailScheduleList;

//    public void setTourDetailSchedule(TourDetailSchedule tourDetailSchedule) {
//        if (this.tourDetailScheduleList == null) {
//            List<TourDetailSchedule> tourDetailSchedules = new ArrayList<>();
//            tourDetailSchedules.add(tourDetailSchedule);
//            this.tourDetailScheduleList = tourDetailSchedules;
//        }
//        else {
//            this.tourDetailScheduleList.add(tourDetailSchedule);
//        }
//    }

}
