package com.koing.server.koing_server.service.admin.dto.user;

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
public class AdminUserCreateTourDto {

    public AdminUserCreateTourDto(Tour tour) {
        this.tourId = tour.getId();
        this.tourTitle = tour.getTitle();
        this.thumbnail = getThumbnail(tour);
        this.tourStatus = tour.getTourStatus().getStatus();
    }

    private Long tourId;
    private String tourTitle;
    private String thumbnail;
    private String tourStatus;

    private String getThumbnail(Tour tour) {
        List<Thumbnail> thumbnails = tour.getThumbnails()
                .stream()
                .sorted(Comparator.comparing((Thumbnail t) -> t.getThumbnailOrder()))
                .collect(Collectors.toList());

        return thumbnails.get(0).getFilePath();
    }

}
