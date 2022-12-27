package com.koing.server.koing_server.domain.tour;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TOUR_DETAIL_SCHEDULE_TABLE")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TourDetailSchedule {

    public TourDetailSchedule(Map.Entry<String, HashMap<String, String>> tourDetailSchedule) {
        String[] timeKey = tourDetailSchedule.getKey().split("~");
        String sTime = timeKey[0];
        String eTime = timeKey[1];

        HashMap<String, String> schedule = tourDetailSchedule.getValue();

        this.startTime = sTime;
        this.endTime = eTime;
        if (schedule.containsKey("locationName")) {
            this.locationName = schedule.get("locationName");
        }
        if (schedule.containsKey("longitude")) {
            this.longitude = Double.parseDouble(schedule.get("longitude"));
        }
        if (schedule.containsKey("latitude")) {
            this.latitude = Double.parseDouble(schedule.get("latitude"));
        }
        if (schedule.containsKey("description")) {
            this.description = schedule.get("description");
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false)
    private String startTime;

    @Column(length = 10, nullable = false)
    private String endTime;

    @Column(length = 50, nullable = false)
    private String locationName;

    @Column(nullable = false)
    private double longitude;

    @Column(nullable = false)
    private double latitude;

    @Column(length = 300)
    private String description;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private TourSchedule tourSchedule;

    public void setNewTourSchedule(TourSchedule tourSchedule) {
        this.tourSchedule = tourSchedule;

        if (tourSchedule.getTourDetailScheduleList() == null) {
            List<TourDetailSchedule> tourDetailSchedules = new ArrayList<>();
            tourDetailSchedules.add(this);
            tourSchedule.setTourDetailScheduleList(tourDetailSchedules);
        }
        else {
            tourSchedule.getTourDetailScheduleList().add(this);
        }
    }

    public void deleteTourSchedule() {
        this.tourSchedule = null;
    }
}
