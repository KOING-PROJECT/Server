package com.koing.server.koing_server.service.admin.dto.tour;

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
public class AdminCreatedTourResponseDto {

    public AdminCreatedTourResponseDto(Tour tour) {
        this.tourId = tour.getId();
        this.thumbnail = getThumbnail(tour.getThumbnails());
        this.tourTitle = tour.getTitle();
        this.guideName = tour.getCreateUser().getName();
        this.guideGrade = tour.getCreateUser().getGuideGrade().getGrade();
        this.guideAccumulatedApprovalNumber = tour.getCreateUser().getAccumulatedApprovalTourCount();
    }

    private Long tourId;
    private String thumbnail;
    private String tourTitle;
    private String guideName;
    private String guideGrade;
    private int guideAccumulatedApprovalNumber;

    private String getThumbnail(List<Thumbnail> thumbnails) {
        if (thumbnails.size() < 1) {
            return "NO THUMBNAIL";
        }

        System.out.println(thumbnails.size());
        List<Thumbnail> tempThumbnails = thumbnails
                .stream()
                .sorted(Comparator.comparing((Thumbnail t) -> t.getThumbnailOrder()))
                .collect(Collectors.toList());

        List<String> thumbnailUrls = new ArrayList<>();

        for (Thumbnail thumbnail : tempThumbnails) {
            thumbnailUrls.add(thumbnail.getFilePath());
        }

        return thumbnailUrls.get(0);
    }
}
