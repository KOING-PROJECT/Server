package com.koing.server.koing_server.service.tour.dto;

import com.koing.server.koing_server.domain.image.Thumbnail;
import com.koing.server.koing_server.domain.tour.*;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class TourTemporaryTourDto {

    public TourTemporaryTourDto(Tour tour) {
        this.id = tour.getId();
        this.title = tour.getTitle();
        this.description = tour.getDescription();
        getCategoryNamesFromTour(tour.getTourCategories());
        this.tourDetailTypes = tour.getTourDetailTypes();
        this.thumbnails = getThumbnails(tour);
        this.participant = tour.getParticipant();
        this.tourPrice = tour.getTourPrice();
        this.hasLevy = tour.isHasLevy();
        this.temporarySavePage = tour.getTemporarySavePage();
        this.additionalPrice = getAdditionalPriceFromTour(tour.getAdditionalPrice());

        if (tour.getTourSchedule() != null) {
            this.tourDates = tour.getTourSchedule().getTourDates().stream().sorted().collect(Collectors.toList());
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
    private List<String> thumbnails;
    private int participant;
    private int tourPrice;
    private boolean hasLevy;
    private int temporarySavePage;
    private List<String> additionalPrice;
    private List<String> tourDates;
    private boolean dateNegotiation;
//    private HashMap<String, HashMap<String, String>> tourDetailScheduleHashMap;
    private Map<String, HashMap<String, String>> tourDetailScheduleHashMap;
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

    private TreeMap<String, HashMap<String, String>> getTourDetailScheduleHashMapFromTour(List<TourDetailSchedule> tourDetailSchedules) {
        if (tourDetailSchedules == null) {
            return new TreeMap<>();
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

        List<String> keyList = new ArrayList<>(newHashMap.keySet());
        keyList.sort((s1, s2) -> s1.compareTo(s2));

        TreeMap<String, HashMap<String, String>> newTempHashMap = new TreeMap<>();

        for (String key : keyList) {
            newTempHashMap.put(key, newHashMap.get(key));
        }

        return newTempHashMap;
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
