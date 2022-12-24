package com.koing.server.koing_server.service.tour.dto;

import com.koing.server.koing_server.domain.tour.Tour;
import lombok.Getter;

@Getter
public class TourApplicationDto {

    public TourApplicationDto(Tour tour) {
        this.tourTitle = tour.getTitle();
        this.guideName = tour.getCreateUser().getName();
        this.tourThumbnail = tour.getThumbnail();
    }

    private String tourTitle;
    private String guideName;
    private String tourThumbnail;
}
