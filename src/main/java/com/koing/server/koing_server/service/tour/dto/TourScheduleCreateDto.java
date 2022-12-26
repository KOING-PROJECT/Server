package com.koing.server.koing_server.service.tour.dto;

import lombok.*;

import java.util.HashMap;
import java.util.Set;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TourScheduleCreateDto {

    private Long tourId;
    private Set<String> tourDates;
    private boolean dateNegotiation;
    private HashMap<String, HashMap<String, String>> tourDetailScheduleHashMap;

}
