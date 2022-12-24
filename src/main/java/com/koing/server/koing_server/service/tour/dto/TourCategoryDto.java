package com.koing.server.koing_server.service.tour.dto;

import lombok.Getter;

@Getter
public class TourCategoryDto {

    public TourCategoryDto(String categoryName) {
        this.categoryName = categoryName;
    }

    private String categoryName;

}
