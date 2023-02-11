package com.koing.server.koing_server.service.review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewToGuideRequestDto {

    private Long tourId;
    private String tourDate;

}
