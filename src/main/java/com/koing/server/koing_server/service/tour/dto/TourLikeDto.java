package com.koing.server.koing_server.service.tour.dto;

import com.koing.server.koing_server.domain.tour.Tour;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TourLikeDto {

    public TourLikeDto(Tour tour) {
        this.tourId = tour.getId();
        this.title = tour.getTitle();
        this.thumbnail = tour.getThumbnail();
    }

    private Long tourId;
    private String title;
    private String thumbnail;

}
