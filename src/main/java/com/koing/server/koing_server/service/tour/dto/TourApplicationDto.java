package com.koing.server.koing_server.service.tour.dto;

import com.koing.server.koing_server.common.enums.TourStatus;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.tour.TourApplication;
import lombok.Getter;

@Getter
public class TourApplicationDto {

    // tourApplication create 할 때 response, create 한 tour의 정보만
    public TourApplicationDto(Tour tour) {
        this.tourTitle = tour.getTitle();
        this.guideName = tour.getCreateUser().getName();
        this.tourThumbnail = tour.getThumbnail();
        this.tourDate = null;
        this.tourStatus = TourStatus.RECRUITMENT;
    }

    public TourApplicationDto(TourApplication tourApplication) {
        this.tourTitle = tourApplication.getTour().getTitle();
        this.guideName = tourApplication.getTour().getCreateUser().getName();
        this.tourThumbnail = tourApplication.getTour().getThumbnail();
        this.tourDate = tourApplication.getTourDate();
        this.tourStatus = tourApplication.getTourStatus();
    }

    private String tourTitle;
    private String guideName;
    private String tourThumbnail;
    private String tourDate;
    private TourStatus tourStatus;
}
