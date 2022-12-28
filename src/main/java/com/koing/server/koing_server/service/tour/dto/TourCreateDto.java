package com.koing.server.koing_server.service.tour.dto;

import com.koing.server.koing_server.common.enums.TourStatus;
import com.koing.server.koing_server.domain.tour.TourCategory;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Getter
public class TourCreateDto {

    public TourCreateDto(TourSetCreateDto tourSetCreateDto) {
        this.createUserId = tourSetCreateDto.getCreateUserId();
        this.title = tourSetCreateDto.getTitle();
        this.description = tourSetCreateDto.getDescription();
        this.tourCategoryNames = tourSetCreateDto.getTourCategoryNames();
        this.thumbnail = tourSetCreateDto.getThumbnail();
        this.participant = tourSetCreateDto.getParticipant();
        this.tourPrice = tourSetCreateDto.getTourPrice();
        this.hasLevy = tourSetCreateDto.isHasLevy();
        this.additionalPrice = tourSetCreateDto.getAdditionalPrice();
    }

    private Long createUserId;
    private String title;
    private String description;
    private List<String> tourCategoryNames;
    private String thumbnail;
    private int participant;
    private int tourPrice;
    private boolean hasLevy;
    private List<String> additionalPrice;

}
