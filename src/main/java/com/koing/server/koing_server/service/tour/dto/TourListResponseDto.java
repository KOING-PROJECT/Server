package com.koing.server.koing_server.service.tour.dto;

import com.koing.server.koing_server.domain.tour.Tour;
import lombok.*;

import java.util.List;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class TourListResponseDto {

    private List<Tour> tours;

    public TourListResponseDto(List<Tour> tours) {
        this.tours = tours;
    }
}
