package com.koing.server.koing_server.service.tour.dto;

import com.koing.server.koing_server.domain.image.Thumbnail;
import com.koing.server.koing_server.domain.tour.Tour;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourDto {

    public TourDto(Tour tour) {
        this.tourId = tour.getId();
        this.tourTitle = tour.getTitle();
        this.maxParticipant = tour.getParticipant();
        getThumbnails(tour);
        this.guideName = tour.getCreateUser().getName();
        if (tour.getCreateUser().getUserOptionalInfo() != null) {
            if (tour.getCreateUser().getUserOptionalInfo().getImageUrls() != null &&
                    tour.getCreateUser().getUserOptionalInfo().getImageUrls().size() > 0) {
                this.guideThumbnail = tour.getCreateUser().getUserOptionalInfo().getImageUrls().get(0);
            }
        }
        if (tour.getTourSchedule() != null) {
            this.tourDates = tour.getTourSchedule().getTourDates()
                    .stream()
                    .sorted()
                    .collect(Collectors.toList());
        }
    }

    private Long tourId;
    private String tourTitle;
    private int maxParticipant;
    private List<String> thumbnails;
    private String guideName;
    private String guideThumbnail;
    private List<String> tourDates;

    private void getThumbnails(Tour tour) {
        List<Thumbnail> thumbnails = tour.getThumbnails()
                .stream()
                .sorted(Comparator.comparing((Thumbnail t) -> t.getThumbnailOrder()))
                .collect(Collectors.toList());
        System.out.println(thumbnails);

        List<String> thumbnailUrls = new ArrayList<>();

        for (Thumbnail thumbnail : thumbnails) {
            thumbnailUrls.add(thumbnail.getFilePath());
        }

        this.thumbnails = thumbnailUrls;
    }

}
