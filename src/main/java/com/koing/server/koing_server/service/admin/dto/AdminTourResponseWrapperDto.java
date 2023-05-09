package com.koing.server.koing_server.service.admin.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminTourResponseWrapperDto {

    public AdminTourResponseWrapperDto(AdminTourDetailResponseDto tourDetail) {
        this.tourDetail = tourDetail;
    }

    private AdminTourDetailResponseDto tourDetail;

}
