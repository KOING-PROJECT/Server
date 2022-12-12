package com.koing.server.koing_server.service.tour.dto;

import lombok.*;

import java.util.List;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class TourCategoryListResponseDto {

    private List<TourCategoryDto> tourCategories;

    public TourCategoryListResponseDto(List<TourCategoryDto> tourCategories) {
        this.tourCategories = tourCategories;
    }
}
