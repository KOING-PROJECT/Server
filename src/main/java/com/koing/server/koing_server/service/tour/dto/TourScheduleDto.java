package com.koing.server.koing_server.service.tour.dto;

import com.koing.server.koing_server.domain.tour.TourDetailSchedule;
import lombok.*;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TourScheduleDto {

    private Long tourId;
    private Set<String> tourDates;
    private boolean dateNegotiation;
    private HashMap<String, HashMap<String, String>> tourDetailScheduleHashMap;

}
