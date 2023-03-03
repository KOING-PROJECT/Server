package com.koing.server.koing_server.service.tour.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.koing.server.koing_server.common.enums.CreateStatus;
import com.koing.server.koing_server.common.enums.TourStatus;
import com.koing.server.koing_server.domain.tour.*;
import com.koing.server.koing_server.domain.user.User;
import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Data
public class TourTemporaryTourDto {

    public TourTemporaryTourDto(Tour tour) {
        this.id = tour.getId();
        this.title = tour.getTitle();
        this.description = tour.getDescription();
        getCategoryNamesFromTour(tour.getTourCategories());
        this.tourDetailTypes = tour.getTourDetailTypes();
        this.thumbnails = tour.getThumbnails();
        this.participant = tour.getParticipant();
        this.tourPrice = tour.getTourPrice();
        this.hasLevy = tour.isHasLevy();
        this.temporarySavePage = tour.getTemporarySavePage();
        this.additionalPrice = getAdditionalPriceFromTour(tour.getAdditionalPrice());

        if (tour.getTourSchedule() != null) {
            this.tourDates = tour.getTourSchedule().getTourDates();
            this.dateNegotiation = tour.getTourSchedule().isDateNegotiation();
            this.tourDetailScheduleHashMap = getTourDetailScheduleHashMapFromTour(tour.getTourSchedule().getTourDetailScheduleList());
        }

        if (tour.getTourSurvey() != null) {
            this.style = tour.getTourSurvey().getStyle();
            this.type = tour.getTourSurvey().getType();
            this.movingSupport = tour.getTourSurvey().isMovingSupport();
        }
    }

    private Long id;
    private String title;
    private String description;
    private Set<String> tourCategoryNames;
    private Set<String> tourDetailTypes;
    private Set<String> thumbnails;
    private int participant;
    private int tourPrice;
    private boolean hasLevy;
    private int temporarySavePage;
    private List<String> additionalPrice;
    private Set<String> tourDates;
    private boolean dateNegotiation;
    private HashMap<String, HashMap<String, String>> tourDetailScheduleHashMap;
    private String style;
    private String type;
    private boolean movingSupport;

    private void getCategoryNamesFromTour(Set<TourCategory> tourCategories) {
        if (tourCategories == null || tourCategories.size() == 0) {
            this.tourCategoryNames = new HashSet<>();
            return;
        }

        this.tourCategoryNames = new HashSet<>();

        for (TourCategory tourCategory : tourCategories) {
            this.tourCategoryNames.add(tourCategory.getCategoryName());
        }
    }

    private List<String> getAdditionalPriceFromTour(Set<HashMap<String, List>> additionalPrice) {
        if (additionalPrice == null) {
            return new ArrayList<>();
        }

        List<String> newAdditionalPrice = new ArrayList<>();

        for (HashMap<String, List> hashMap : additionalPrice) {
            StringBuilder stringBuilder = new StringBuilder();

            String k = "";
            for (String key : hashMap.keySet()) {
                k = key;
                stringBuilder.append(key);
                stringBuilder.append("/");
            }

            stringBuilder.append(hashMap.get(k).get(0));
            stringBuilder.append("/");
            stringBuilder.append(hashMap.get(k).get(1));

            newAdditionalPrice.add(stringBuilder.toString());
        }

        return newAdditionalPrice;
    }

    private HashMap<String, HashMap<String, String>> getTourDetailScheduleHashMapFromTour(List<TourDetailSchedule> tourDetailSchedules) {
        if (tourDetailSchedules == null) {
            return new HashMap<>();
        }

        HashMap<String, HashMap<String, String>> newHashMap = new HashMap<>();

        for (TourDetailSchedule tourDetailSchedule : tourDetailSchedules) {
            StringBuilder key = new StringBuilder();
            key.append(tourDetailSchedule.getStartTime());
            key.append("~");
            key.append(tourDetailSchedule.getEndTime());

            HashMap<String, String> schedule = new HashMap<>();
            schedule.put("locationName", tourDetailSchedule.getLocationName());
            schedule.put("longitude", Double.toString(tourDetailSchedule.getLongitude()));
            schedule.put("latitude", Double.toString(tourDetailSchedule.getLatitude()));
            schedule.put("description", tourDetailSchedule.getDescription());

            newHashMap.put(key.toString(), schedule);
        }

        return newHashMap;
    }


}
