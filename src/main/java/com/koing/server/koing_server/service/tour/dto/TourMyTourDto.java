package com.koing.server.koing_server.service.tour.dto;

import com.koing.server.koing_server.common.enums.CreateStatus;
import com.koing.server.koing_server.common.enums.TourStatus;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class TourMyTourDto {

    public TourMyTourDto(Tour tour) {
        this.tourId = tour.getId();
        this.tourTitle = tour.getTitle();
        this.maxParticipant = tour.getParticipant();
        this.thumbnail = tour.getThumbnail();
        this.guideName = tour.getCreateUser().getName();
        this.guideThumbnail = tour.getThumbnail();
        if (tour.getTourSchedule() != null) {
            if (tour.getTourSchedule().getTourDates() != null) {
                this.tourDates = tour.getTourSchedule().getTourDates();
            }
        }
        this.tourStatus = tour.getTourStatus();
        this.createStatus = tour.getCreateStatus();
    }

    private Long tourId;
    private String tourTitle;
    private int maxParticipant;
    private String thumbnail;
    private String guideName;
    private String guideThumbnail;
    private Set<String> tourDates;
    private TourStatus tourStatus;
    private CreateStatus createStatus;
}
