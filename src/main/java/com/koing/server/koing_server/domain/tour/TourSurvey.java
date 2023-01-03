package com.koing.server.koing_server.domain.tour;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.koing.server.koing_server.common.enums.CreateStatus;
import com.koing.server.koing_server.service.tour.dto.TourSurveyCreateDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TOUR_SURVEY_TABLE")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TourSurvey {

    public TourSurvey(TourSurveyCreateDto tourSurveyCreateDto, CreateStatus createStatus) {
        this.style = tourSurveyCreateDto.getStyle();
        this.type = tourSurveyCreateDto.getType();
        this.movingSupport = tourSurveyCreateDto.isMovingSupport();
        this.createStatus = createStatus;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private String style;

    @Column(length = 50)
    private String type;

    private boolean movingSupport;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CreateStatus createStatus;

}
