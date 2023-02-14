package com.koing.server.koing_server.service.tour.dto;

import com.koing.server.koing_server.common.enums.TourStatus;
import com.koing.server.koing_server.domain.tour.TourApplication;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class TourHistoryDto {

    public TourHistoryDto(TourApplication tourApplication) {
        this.tourId = tourApplication.getTour().getId();
        this.title = tourApplication.getTour().getTitle();
        this.guideName = tourApplication.getTour().getCreateUser().getName();
        this.thumbnails = tourApplication.getTour().getThumbnails();
        this.tourDate = tourApplication.getTourDate();
        this.tourStatus = tourApplication.getTourStatus();
    }

    private Long tourId;
    private String title;
    private String guideName;
    private Set<String> thumbnails;
    private String tourDate;
    private TourStatus tourStatus;

}
