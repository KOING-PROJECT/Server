package com.koing.server.koing_server.service.tour.dto;

import com.koing.server.koing_server.common.enums.TourStatus;
import com.koing.server.koing_server.domain.image.Thumbnail;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.tour.TourApplication;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class TourHistoryDto {

    public TourHistoryDto(TourApplication tourApplication) {
        this.tourId = tourApplication.getTour().getId();
        this.title = tourApplication.getTour().getTitle();
        this.guideName = tourApplication.getTour().getCreateUser().getName();
        this.thumbnails = getThumbnails(tourApplication.getTour());
        this.tourDate = tourApplication.getTourDate();
        this.tourStatus = tourApplication.getTourStatus();
    }

    private Long tourId;
    private String title;
    private String guideName;
    private List<String> thumbnails;
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
