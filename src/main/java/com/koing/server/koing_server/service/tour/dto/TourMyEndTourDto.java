package com.koing.server.koing_server.service.tour.dto;

import com.koing.server.koing_server.common.enums.CurrentStatus;
import com.koing.server.koing_server.common.enums.TourApplicationStatus;
import com.koing.server.koing_server.domain.image.Thumbnail;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.tour.TourApplication;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TourMyEndTourDto {

    public TourMyEndTourDto(TourApplication tourApplication) {
        this.tourId = tourApplication.getTour().getId();
        this.tourTitle = tourApplication.getTour().getTitle();
        this.thumbnails = getThumbnails(tourApplication.getTour());
        this.tourDate = tourApplication.getTourDate();
//        this.hasReview = hasReview;
        this.currentStatus = decideCurrentStatus(tourApplication);
    }

    private Long tourId;
    private String tourTitle;
    private List<String> thumbnails;
    private String tourDate;
//    private boolean hasReview;
    private CurrentStatus currentStatus;

    private List<String> getThumbnails(Tour tour) {
        List<Thumbnail> thumbnails = tour.getThumbnails()
                .stream()
                .sorted(Comparator.comparing((Thumbnail t) -> t.getThumbnailOrder()))
                .collect(Collectors.toList());

        List<String> thumbnailUrls = new ArrayList<>();

        for (Thumbnail thumbnail : thumbnails) {
            thumbnailUrls.add(thumbnail.getFilePath());
        }

        return thumbnailUrls;
    }

    private CurrentStatus decideCurrentStatus(TourApplication tourApplication) {
        if (tourApplication.getTourApplicationStatus().equals(TourApplicationStatus.GUIDE_REVIEWED)) {
            return CurrentStatus.GUIDE_REVIEWED;
        }

        if (tourApplication.getTourApplicationStatus().equals(TourApplicationStatus.TOURIST_REVIEWED)) {
            return CurrentStatus.TOURIST_REVIEWED;
        }

        if (tourApplication.getTourApplicationStatus().equals(TourApplicationStatus.REVIEWED)) {
            return CurrentStatus.REVIEWED;
        }

        return CurrentStatus.NO_REVIEW;
    }
}
