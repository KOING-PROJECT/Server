package com.koing.server.koing_server.service.tour.dto;

import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class TourHomeToursDto {

    public TourHomeToursDto(Tour tour) {
        this.tourId = tour.getId();
        this.tourTitle = tour.getTitle();
        this.thumbnail = tour.getThumbnail();
        this.pressLikeUserIds = getPressLikeUserIds(tour);
    }

    private Long tourId;
    private String tourTitle;
    private String thumbnail;
    private Set<Long> pressLikeUserIds;

    private Set<Long> getPressLikeUserIds(Tour tour) {
        Set<User> pressLikeUsers = tour.getPressLikeUsers();

        Set<Long> userIds = new HashSet<>();
        for (User user : pressLikeUsers) {
            userIds.add(user.getId());
        }

        return userIds;
    }

}
