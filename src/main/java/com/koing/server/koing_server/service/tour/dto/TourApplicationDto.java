package com.koing.server.koing_server.service.tour.dto;

import com.koing.server.koing_server.common.enums.TourStatus;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.tour.TourApplication;
import lombok.Getter;

import java.util.Set;

@Getter
public class TourApplicationDto {

    // tourApplication create 할 때 response, create 한 tour의 정보만
    public TourApplicationDto(Tour tour) {
        this.tourTitle = tour.getTitle();
        this.guideName = tour.getCreateUser().getName();
        this.tourThumbnails = tour.getThumbnails();
        this.tourDate = null;
        this.tourStatus = TourStatus.RECRUITMENT;
    }

    public TourApplicationDto(TourApplication tourApplication) {
        this.tourTitle = tourApplication.getTour().getTitle();
        this.guideName = tourApplication.getTour().getCreateUser().getName();
        this.tourThumbnails = tourApplication.getTour().getThumbnails();
        this.tourDate = tourApplication.getTourDate();
        this.tourStatus = tourApplication.getTourStatus();
    }

    private String tourTitle;
    private String guideName;
    private Set<String> tourThumbnails;
    private String tourDate;
    private TourStatus tourStatus;
}
