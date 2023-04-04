package com.koing.server.koing_server.service.tour.dto;

import com.koing.server.koing_server.domain.image.Thumbnail;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class TourHomeToursDto {

    public TourHomeToursDto(Tour tour) {
        this.tourId = tour.getId();
        this.tourTitle = tour.getTitle();
        this.thumbnails = getThumbnails(tour);
        this.pressLikeUserIds = getPressLikeUserIds(tour);
        if (tour.getCreateUser().getUserOptionalInfo() != null) {
            if (tour.getCreateUser().getUserOptionalInfo().getImageUrls() != null &&
                    tour.getCreateUser().getUserOptionalInfo().getImageUrls().size() > 0) {
                this.guideThumbnail = tour.getCreateUser().getUserOptionalInfo().getImageUrls().get(0);
            }
        }
    }

    private Long tourId;
    private String tourTitle;
    private List<String> thumbnails;
    private Set<Long> pressLikeUserIds;
    private String guideThumbnail;

    private Set<Long> getPressLikeUserIds(Tour tour) {
        Set<User> pressLikeUsers = tour.getPressLikeUsers();

        Set<Long> userIds = new HashSet<>();
        for (User user : pressLikeUsers) {
            userIds.add(user.getId());
        }

        return userIds;
    }

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
