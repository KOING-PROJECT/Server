package com.koing.server.koing_server.service.admin.dto.tour;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AdminTourListResponseDto {

    public AdminTourListResponseDto(List<AdminTourResponseDto> tours) {
        this.notFinishTours = tours;
    }

    private List<AdminTourResponseDto> notFinishTours;

}
