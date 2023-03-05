package com.koing.server.koing_server.service.tour.dto;

import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.tour.TourApplication;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TourMyEndTourDto {

    public TourMyEndTourDto(TourApplication tourApplication, boolean hasReview) {
        this.tourId = tourApplication.getTour().getId();
        this.tourTitle = tourApplication.getTour().getTitle();
        this.thumbnails = tourApplication.getTour().getThumbnails();
        this.tourDate = tourApplication.getTourDate();
        this.hasReview = hasReview;
    }

    private Long tourId;
    private String tourTitle;
    private List<String> thumbnails;
    private String tourDate;
    private boolean hasReview;

}
