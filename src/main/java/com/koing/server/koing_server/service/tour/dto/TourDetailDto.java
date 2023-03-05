package com.koing.server.koing_server.service.tour.dto;

import com.koing.server.koing_server.domain.image.Thumbnail;
import com.koing.server.koing_server.domain.tour.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class TourDetailDto {

    public TourDetailDto(Long userId, Tour tour, TourSchedule tourSchedule) {
        this.userId = userId;
        this.createUserId = tour.getCreateUser().getId();
        this.createUserName = tour.getCreateUser().getName();
        this.title = tour.getTitle();
        this.description = tour.getDescription();
        this.tourCategoryNames = new ArrayList<>();
        for (TourCategory tourCategory : tour.getTourCategories()) {
            this.tourCategoryNames.add(tourCategory.getCategoryName());
        }
        this.tourDetailTypes = tour.getTourDetailTypes().stream().collect(Collectors.toList());
        this.thumbnails = getThumbnails(tour);
        this.participant = tour.getParticipant();
        this.tourPrice = tour.getTourPrice();
        this.hasLevy = tour.isHasLevy();
        this.additionalPrice = tour.getAdditionalPrice().stream().collect(Collectors.toList());
        this.tourDates = tourSchedule.getTourDates().stream().sorted().collect(Collectors.toList());
        sortTourDetailSchedule(tourSchedule);
        this.isUserPressTourLike = false;
    }

    private Long userId;
    private Long createUserId;
    private String createUserName;
    private String title;
    private String description;
    private List<String> tourCategoryNames;
    private List<String> tourDetailTypes;
    private List<String> thumbnails;
    private int participant;
    private int tourPrice;
    private boolean hasLevy;
    private List<HashMap<String, List>> additionalPrice;
    private List<String> tourDates;
    private List<TourDetailSchedule> tourDetailSchedules;
    private boolean isUserPressTourLike;
    // 후기 추가해야됨

    private void sortTourDetailSchedule(TourSchedule tourSchedule) {

        if (tourSchedule.getTourDetailScheduleList() == null || tourSchedule.getTourDetailScheduleList().size() < 1) {
            return;
        }

        List<TourDetailSchedule> tourDetailSchedules = tourSchedule.getTourDetailScheduleList()
                .stream()
                .sorted(Comparator.comparing(
                        (TourDetailSchedule t) -> t.getStartTime()
                ).reversed().reversed())
                .collect(Collectors.toList());

        this.tourDetailSchedules = tourDetailSchedules;
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
