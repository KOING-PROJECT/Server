package com.koing.server.koing_server.service.tour.dto;

import com.koing.server.koing_server.common.enums.CreateStatus;
import com.koing.server.koing_server.common.enums.TourStatus;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class TourMyTourDto {

    public TourMyTourDto(Tour tour) {
        this.tourId = tour.getId();
        this.tourTitle = tour.getTitle();
        this.maxParticipant = tour.getParticipant();
        this.thumbnails = tour.getThumbnails();
        this.guideName = tour.getCreateUser().getName();
        if (tour.getCreateUser().getUserOptionalInfo() != null) {
            this.guideThumbnails = tour.getCreateUser().getUserOptionalInfo().getImageUrls();
        }
        if (tour.getTourSchedule() != null) {
            if (tour.getTourSchedule().getTourDates() != null) {
                this.tourDates = tour.getTourSchedule().getTourDates()
                        .stream()
                        .sorted()
                        .collect(Collectors.toList());
            }
        }
        this.tourStatus = tour.getTourStatus();
        this.createStatus = tour.getCreateStatus();
    }

    private Long tourId;
    private String tourTitle;
    private int maxParticipant;
    private List<String> thumbnails;
    private String guideName;
    private List<String> guideThumbnails;
    private List<String> tourDates;
    private TourStatus tourStatus;
    private CreateStatus createStatus;
}
