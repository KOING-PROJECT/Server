package com.koing.server.koing_server.controller.survey;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.service.survey.SurveyService;
import com.koing.server.koing_server.service.survey.dto.SurveyInfoDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Survey")
@RequestMapping("/survey")
@RestController
@RequiredArgsConstructor
public class SurveyController {

    private final Logger LOGGER = LoggerFactory.getLogger(SurveyController.class);
    private final SurveyService surveyService;

    @ApiOperation("Survey - 투어를 추천합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "survey - 투어 추천 성공"),
            @ApiResponse(code = 404, message = "존재하지 않는 투어 카테고리 입니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping("")
    public SuperResponse recommendTour(@RequestBody SurveyInfoDto surveyInfoDto) {
        LOGGER.info("[SurveyController] 투어 추천 시도");
        SuperResponse recommendTourResponse;
        try {
            recommendTourResponse = surveyService.recommendSurvey(surveyInfoDto);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            System.out.println(exception);
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[SurveyController] 투어 추천 성공");

        return recommendTourResponse;
    }

}
