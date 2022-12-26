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
