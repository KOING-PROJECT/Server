package com.koing.server.koing_server.service.tour.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.koing.server.koing_server.common.enums.CreateStatus;
import com.koing.server.koing_server.common.enums.TourStatus;
import com.koing.server.koing_server.domain.tour.*;
import com.koing.server.koing_server.domain.user.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class TourDetailDto {

    public TourDetailDto(Long userId, Tour tour, TourSchedule tourSchedule) {
        this.userId = userId;
        this.createUserId = tour.getCreateUser().getId();
        this.createUserName = tour.getCreateUser().getName();
        this.title = tour.getTitle();
        this.description = tour.getDescription();
        this.tourCategoryNames = new HashSet<>();
        for (TourCategory tourCategory : tour.getTourCategories()) {
            this.tourCategoryNames.add(tourCategory.getCategoryName());
        }
        this.tourDetailTypes = tour.getTourDetailTypes();
        this.thumbnails = tour.getThumbnails();
        this.participant = tour.getParticipant();
        this.tourPrice = tour.getTourPrice();
        this.hasLevy = tour.isHasLevy();
        this.additionalPrice = tour.getAdditionalPrice();
        this.tourDates = tourSchedule.getTourDates();
        this.tourDetailSchedules = tourSchedule.getTourDetailScheduleList();
        this.isUserPressTourLike = false;
    }

    private Long userId;
    private Long createUserId;
    private String createUserName;
    private String title;
    private String description;
    private Set<String> tourCategoryNames;
    private Set<String> tourDetailTypes;
    private Set<String> thumbnails;
    private int participant;
    private int tourPrice;
    private boolean hasLevy;
    private Set<HashMap<String, List>> additionalPrice;
    private Set<String> tourDates;
    private List<TourDetailSchedule> tourDetailSchedules;
    private boolean isUserPressTourLike;
    // 후기 추가해야됨

}
