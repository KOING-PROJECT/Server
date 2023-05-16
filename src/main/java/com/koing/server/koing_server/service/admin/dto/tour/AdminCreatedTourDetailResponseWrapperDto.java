package com.koing.server.koing_server.service.admin.dto.tour;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminCreatedTourDetailResponseWrapperDto {

    public AdminCreatedTourDetailResponseWrapperDto(AdminCreatedTourDetailResponseDto createdTourDetail) {
        this.createdTourDetail = createdTourDetail;
    }

    private AdminCreatedTourDetailResponseDto createdTourDetail;

}
