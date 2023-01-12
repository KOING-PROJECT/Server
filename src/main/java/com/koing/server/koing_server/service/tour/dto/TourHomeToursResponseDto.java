package com.koing.server.koing_server.service.tour.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TourHomeToursResponseDto {

    public TourHomeToursResponseDto(List<TourHomeToursDto> tours) {
        this.tours = tours;
    }

    private List<TourHomeToursDto> tours;

}
