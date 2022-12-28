package com.koing.server.koing_server.service.tour.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class TourApplicationCreateDto {

    public TourApplicationCreateDto(Long tourId) {
        this.tourId = tourId;
    }

    private Long tourId;

}
