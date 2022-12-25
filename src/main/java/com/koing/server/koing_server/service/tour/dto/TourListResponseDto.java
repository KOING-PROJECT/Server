package com.koing.server.koing_server.service.tour.dto;

import lombok.*;

import java.util.List;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class TourListResponseDto {

    private List<TourDto> tours;

    public TourListResponseDto(List<TourDto> tourDtos) {
        this.tours = tourDtos;
    }
}
