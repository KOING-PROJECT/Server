package com.koing.server.koing_server.service.tour.dto;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import java.util.List;

@Getter
public class TourCategoryDto {
    private String categoryName;
    private List<String> detailTypes;
}
