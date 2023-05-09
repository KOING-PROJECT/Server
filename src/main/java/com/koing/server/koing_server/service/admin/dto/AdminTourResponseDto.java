package com.koing.server.koing_server.service.admin.dto;

import com.koing.server.koing_server.common.enums.TourCategoryIndex;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.tour.TourCategory;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class AdminTourResponseDto {

    public AdminTourResponseDto(Tour tour) {
        this.tourTitle = tour.getTitle();
        this.tourCategories = getTourCategoryName(tour.getTourCategories());
        this.guideName = tour.getCreateUser().getName();
        this.guideGrade = tour.getCreateUser().getGuideGrade().getGrade();
        this.tourDates = tour.getTourSchedule().getTourDates().stream().sorted().collect(Collectors.toList());
        this.tourStatus = tour.getTourStatus().getStatus();
    }

    private String tourTitle;
    private List<String> tourCategories;
    private String guideName;
    private String guideGrade;
    private List<String> tourDates;
    private String tourStatus;

    private List<String> getTourCategoryName(Set<TourCategory> tourCategories) {
        List<String> tourCategoryNames = new ArrayList<>();
        for (TourCategory tourCategory : tourCategories) {
            for (TourCategoryIndex category : TourCategoryIndex.values()) {
                if (category.getCategoryIndex().equals(tourCategory.getCategoryName())) {
                    tourCategoryNames.add(category.getCategoryName());
                }
            }
        }

        return tourCategoryNames;
    }

}
