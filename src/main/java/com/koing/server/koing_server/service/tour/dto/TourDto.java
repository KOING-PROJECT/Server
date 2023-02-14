package com.koing.server.koing_server.service.tour.dto;

import com.koing.server.koing_server.domain.tour.Tour;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourDto {

    public TourDto(Tour tour) {
        this.tourId = tour.getId();
        this.tourTitle = tour.getTitle();
        this.maxParticipant = tour.getParticipant();
        this.thumbnails = tour.getThumbnails();
        this.guideName = tour.getCreateUser().getName();
        if (tour.getCreateUser().getUserOptionalInfo() != null) {
            if (tour.getCreateUser().getUserOptionalInfo().getImageUrls() != null &&
                    tour.getCreateUser().getUserOptionalInfo().getImageUrls().size() > 0) {
                this.guideThumbnail = tour.getCreateUser().getUserOptionalInfo().getImageUrls().get(0);
            }
        }
        if (tour.getTourSchedule() != null) {
            this.tourDates = tour.getTourSchedule().getTourDates();
        }
    }

    private Long tourId;
    private String tourTitle;
    private int maxParticipant;
    private Set<String> thumbnails;
    private String guideName;
    private String guideThumbnail;
    private Set<String> tourDates;

}
