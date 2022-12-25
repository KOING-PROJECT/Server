package com.koing.server.koing_server.service.tour.dto;

import lombok.Getter;

@Getter
public class TourApplicationParticipateDto {

    private Long tourId;
//    private String userEmail;
    private Long userId;
    private String tourDate;

}
