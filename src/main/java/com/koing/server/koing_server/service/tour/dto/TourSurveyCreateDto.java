package com.koing.server.koing_server.service.tour.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TourSurveyCreateDto {

    public TourSurveyCreateDto(Long tourId, TourSetCreateDto tourSetCreateDto) {
        this.tourId = tourId;
        this.style = tourSetCreateDto.getStyle();
        this.type = tourSetCreateDto.getType();
        this.movingSupport = tourSetCreateDto.isMovingSupport();
    }

    private Long tourId;
    private String style;
    private String type;
    private boolean movingSupport;

}
