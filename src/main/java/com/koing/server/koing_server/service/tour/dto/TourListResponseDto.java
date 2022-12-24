package com.koing.server.koing_server.service.tour.dto;

import com.koing.server.koing_server.domain.tour.Tour;
import lombok.*;

import java.util.List;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class TourListResponseDto {

    private List<TourListDto> tours;

    public TourListResponseDto(List<TourListDto> tourListDtos) {
        this.tours = tourListDtos;
    }
}
