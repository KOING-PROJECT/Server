package com.koing.server.koing_server.service.tour.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TourApplicationCancelDto {

    private Long tourId;
    private Long userId;
    private String tourDate;
    private String today;

}
