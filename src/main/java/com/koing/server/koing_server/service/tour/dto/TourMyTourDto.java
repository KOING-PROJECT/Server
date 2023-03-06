package com.koing.server.koing_server.service.tour.dto;

import com.koing.server.koing_server.common.enums.CreateStatus;
import com.koing.server.koing_server.common.enums.ProgressStatus;
import com.koing.server.koing_server.common.enums.TourStatus;
import com.koing.server.koing_server.domain.image.Thumbnail;
import com.koing.server.koing_server.domain.tour.Tour;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class TourMyTourDto {

    public TourMyTourDto(Tour tour) {
        this.tourId = tour.getId();
        this.tourTitle = tour.getTitle();
        this.maxParticipant = tour.getParticipant();
        this.thumbnails = getThumbnails(tour);
        this.guideName = tour.getCreateUser().getName();
        if (tour.getCreateUser().getUserOptionalInfo() != null) {
            this.guideThumbnails = tour.getCreateUser().getUserOptionalInfo().getImageUrls();
        }
        if (tour.getTourSchedule() != null) {
            if (tour.getTourSchedule().getTourDates() != null) {
                this.tourDates = tour.getTourSchedule().getTourDates()
                        .stream()
                        .sorted()
                        .collect(Collectors.toList());
            }
        }
        this.tourStatus = tour.getTourStatus();
        this.createStatus = tour.getCreateStatus();
    }

    public TourMyTourDto(Tour tour, ProgressStatus progressStatus) {
        this.tourId = tour.getId();
        this.tourTitle = tour.getTitle();
        this.maxParticipant = tour.getParticipant();
        this.thumbnails = getThumbnails(tour);
        this.guideName = tour.getCreateUser().getName();
        if (tour.getCreateUser().getUserOptionalInfo() != null) {
            this.guideThumbnails = tour.getCreateUser().getUserOptionalInfo().getImageUrls();
        }
        if (tour.getTourSchedule() != null) {
            if (tour.getTourSchedule().getTourDates() != null) {
                this.tourDates = tour.getTourSchedule().getTourDates()
                        .stream()
                        .sorted()
                        .collect(Collectors.toList());
            }
        }
        this.tourStatus = tour.getTourStatus();
        this.createStatus = tour.getCreateStatus();
        this.guideProgressStatus = progressStatus;
    }

    private Long tourId;
    private String tourTitle;
    private int maxParticipant;
    private List<String> thumbnails;
    private String guideName;
    private List<String> guideThumbnails;
    private List<String> tourDates;
    private TourStatus tourStatus;
    private CreateStatus createStatus;
    private ProgressStatus guideProgressStatus;

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
