package com.koing.server.koing_server.service.tour.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourListDto {

    private String tourTitle;
    private int maxParticipant;
    private String thumbnail;
    private String guideName;
    private String guideThumbnail;
    private Set<String> tourDates;

}
