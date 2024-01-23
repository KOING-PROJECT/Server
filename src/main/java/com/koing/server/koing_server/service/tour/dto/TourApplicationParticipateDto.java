package com.koing.server.koing_server.service.tour.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TourApplicationParticipateDto {

    private Long tourId;
    private Long userId;
    private String tourDate;
    private int numberOfParticipants;

}
