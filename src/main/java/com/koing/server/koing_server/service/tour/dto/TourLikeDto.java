package com.koing.server.koing_server.service.tour.dto;

import com.koing.server.koing_server.domain.tour.Tour;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TourLikeDto {

    public TourLikeDto(Tour tour) {
        this.tourId = tour.getId();
        this.title = tour.getTitle();
        this.thumbnails = tour.getThumbnails();
    }

    private Long tourId;
    private String title;
    private Set<String> thumbnails;

}
