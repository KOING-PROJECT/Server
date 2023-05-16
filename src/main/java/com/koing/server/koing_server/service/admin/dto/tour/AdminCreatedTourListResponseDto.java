package com.koing.server.koing_server.service.admin.dto.tour;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AdminCreatedTourListResponseDto {

    public AdminCreatedTourListResponseDto(List<AdminCreatedTourResponseDto> createdTours) {
        this.createdTours = createdTours;
    }

    private List<AdminCreatedTourResponseDto> createdTours;

}
