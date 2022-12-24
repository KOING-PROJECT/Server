package com.koing.server.koing_server.service.tour.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class TourCategoryCreateDto {
    private String categoryName;
    private List<String> detailTypes;
}
