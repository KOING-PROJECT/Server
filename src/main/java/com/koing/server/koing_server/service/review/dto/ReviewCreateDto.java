package com.koing.server.koing_server.service.review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCreateDto {

    private String tourReview;
    private String guideReview;
    private int attachment;
    private String totalReview;
    private List<String> photos;

}
