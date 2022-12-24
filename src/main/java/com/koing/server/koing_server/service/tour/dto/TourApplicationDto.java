package com.koing.server.koing_server.service.tour.dto;

import lombok.Getter;

import java.util.Set;

@Getter
public class TourApplicationDto {

    private Long tourId;
    private Set<String> tourDates;

}
