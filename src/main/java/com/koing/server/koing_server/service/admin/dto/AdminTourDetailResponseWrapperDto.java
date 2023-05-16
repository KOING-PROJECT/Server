package com.koing.server.koing_server.service.admin.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminTourDetailResponseWrapperDto {

    public AdminTourDetailResponseWrapperDto(AdminTourDetailResponseDto tourDetail) {
        this.tourDetail = tourDetail;
    }

    private AdminTourDetailResponseDto tourDetail;

}
