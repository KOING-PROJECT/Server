package com.koing.server.koing_server.service.tour.dto;

import lombok.Getter;

@Getter
public class TourApplicationParticipateDto {

    private Long tourId;
    private Long userId;
    private String tourDate;
    private int numberOfParticipants;

}
