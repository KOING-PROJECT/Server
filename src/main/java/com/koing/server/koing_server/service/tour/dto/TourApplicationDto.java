package com.koing.server.koing_server.service.tour.dto;

import com.koing.server.koing_server.common.enums.TourStatus;
import com.koing.server.koing_server.domain.image.Thumbnail;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.tour.TourApplication;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TourApplicationDto {

    // tourApplication create 할 때 response, create 한 tour의 정보만
    public TourApplicationDto(Tour tour) {
        this.tourTitle = tour.getTitle();
        this.guideName = tour.getCreateUser().getName();
        this.tourThumbnails = getThumbnails(tour);
        this.tourDate = null;
        this.tourStatus = TourStatus.RECRUITMENT;
    }

    public TourApplicationDto(TourApplication tourApplication) {
        this.tourTitle = tourApplication.getTour().getTitle();
        this.guideName = tourApplication.getTour().getCreateUser().getName();
        this.tourThumbnails = getThumbnails(tourApplication.getTour());
        this.tourDate = tourApplication.getTourDate();
        this.tourStatus = tourApplication.getTourStatus();
    }

    private String tourTitle;
    private String guideName;
    private List<String> tourThumbnails;
    private String tourDate;
    private TourStatus tourStatus;

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
}
