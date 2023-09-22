package com.koing.server.koing_server.domain.tour;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.koing.server.koing_server.common.enums.CreateStatus;
import com.koing.server.koing_server.domain.common.AbstractRootEntity;
import com.koing.server.koing_server.service.tour.dto.TourSurveyCreateDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TOUR_SURVEY_TABLE")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TourSurvey extends AbstractRootEntity {

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

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    private Tour tour;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CreateStatus createStatus;

}
