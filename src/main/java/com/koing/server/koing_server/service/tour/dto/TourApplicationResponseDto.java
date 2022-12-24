package com.koing.server.koing_server.service.tour.dto;

import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.tour.TourApplication;
import lombok.Getter;

import java.util.List;

@Getter
public class TourApplicationResponseDto {

    public TourApplicationResponseDto(List<TourApplicationDto> tourApplications) {
        this.tourApplications = tourApplications;
    }

    private List<TourApplicationDto> tourApplications;

}
