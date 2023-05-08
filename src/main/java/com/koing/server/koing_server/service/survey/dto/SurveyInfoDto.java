package com.koing.server.koing_server.service.survey.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SurveyInfoDto {

    private int expeditionStyle;
    private int expeditionCharacter;
    private int expeditionCategory;
    private int expeditionDetailType;
    private int hasDate;
    private List<String> expeditionDates;
    private int expeditionTime;
    private int numberOfTourist;
    private int needMoveSupport;

}
