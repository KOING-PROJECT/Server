package com.koing.server.koing_server.service.tour.dto;

import lombok.*;

import java.util.HashMap;
import java.util.Set;

@ToString
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TourScheduleCreateDto {

    public TourScheduleCreateDto(Long tourId, TourSetCreateDto tourSetCreateDto) {
        this.tourId = tourId;
        this.tourDates = tourSetCreateDto.getTourDates();
        this.dateNegotiation = tourSetCreateDto.isDateNegotiation();
        this.tourDetailScheduleHashMap = tourSetCreateDto.getTourDetailScheduleHashMap();
    }

    private Long tourId;
    private Set<String> tourDates;
    private boolean dateNegotiation;
    private HashMap<String, HashMap<String, String>> tourDetailScheduleHashMap;

}
