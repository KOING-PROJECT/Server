package com.koing.server.koing_server.service.tour.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Data
public class TourSetCreateDto {

    private Long createUserId;
    private String title;
    private String description;
    private List<String> tourCategoryNames;
    private List<String> uploadedThumbnailUrls;
    private int participant;
    private int tourPrice;
    private boolean hasLevy;
    private List<String> additionalPrice;
    private Set<String> tourDates;
    private boolean dateNegotiation;
    private HashMap<String, HashMap<String, String>> tourDetailScheduleHashMap;
    private String style;
    private String type;
    private boolean movingSupport;

}
