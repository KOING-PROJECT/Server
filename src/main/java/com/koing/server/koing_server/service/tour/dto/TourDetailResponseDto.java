package com.koing.server.koing_server.service.tour.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TourDetailResponseDto {

    public TourDetailResponseDto(TourDetailDto tourDetailDto) {
        this.tour = tourDetailDto;
    }

    private TourDetailDto tour;

}
