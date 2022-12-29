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
    private String thumbnail;
    private int participant;
    private int tourPrice;
    private boolean hasLevy;
    private List<String> additionalPrice;
    private Set<String> tourDates;
    private boolean dateNegotiation;
    private HashMap<String, HashMap<String, String>> tourDetailScheduleHashMap;

}