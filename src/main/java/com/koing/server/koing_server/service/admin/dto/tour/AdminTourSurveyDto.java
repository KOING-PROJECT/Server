package com.koing.server.koing_server.service.admin.dto.tour;

import com.koing.server.koing_server.domain.tour.TourSurvey;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminTourSurveyDto {

    public AdminTourSurveyDto(TourSurvey tourSurvey) {
        this.style = getStyleName(tourSurvey.getStyle());
        this.type = getTypeName(tourSurvey.getType());
        this.movingSupport = tourSurvey.isMovingSupport();
    }

    private String style;
    private String type;
    private boolean movingSupport;

    private String getStyleName(String style) {
        if (style.equals("0")) {
            return "계획형";
        }
        else if (style.equals("1")) {
            return "자유형";
        }
        else {
            return "확인 불가";
        }
    }

    private String getTypeName(String type) {
        if (type.equals("0")) {
            return "다양한 체험 중심";
        }
        else if (type.equals("1")) {
            return "SNS 인생샷, 핫플 방문";
        }
        else if (type.equals("2")) {
            return "여유롭게 힐링";
        }
        else if (type.equals("3")) {
            return "기타";
        }
        else {
            return "확인 불가";
        }
    }

}
