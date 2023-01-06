package com.koing.server.koing_server.service.tour.dto;

import com.koing.server.koing_server.domain.tour.Tour;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TourMyEndTourDto {

    public TourMyEndTourDto(Tour tour) {
        this.tourId = tour.getId();
        this.tourTitle = tour.getTitle();
        this.thumbnail = tour.getThumbnail();
        List<String> dates = new ArrayList<>(tour.getTourSchedule().getTourDates());
        Collections.sort(dates);
        this.firstTourDate = dates.get(0);
    }

    private Long tourId;
    private String tourTitle;
    private String thumbnail;
    private String firstTourDate;

}
