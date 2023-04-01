package com.koing.server.koing_server.service.tour.dto;

import com.koing.server.koing_server.domain.image.Thumbnail;
import com.koing.server.koing_server.domain.tour.Tour;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TourLikeDto {

    public TourLikeDto(Tour tour) {
        this.tourId = tour.getId();
        this.title = tour.getTitle();
        this.thumbnails = getThumbnails(tour);
        if (tour.getCreateUser().getUserOptionalInfo() != null) {
            if (tour.getCreateUser().getUserOptionalInfo().getImageUrls() != null
                    && tour.getCreateUser().getUserOptionalInfo().getImageUrls().size() > 0)
            this.guideThumbnail = tour.getCreateUser().getUserOptionalInfo().getImageUrls().get(0);
        }
    }

    private Long tourId;
    private String title;
    private List<String> thumbnails;
    private String guideThumbnail;

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
