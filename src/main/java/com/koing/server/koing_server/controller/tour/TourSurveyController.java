package com.koing.server.koing_server.controller.tour;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.enums.CreateStatus;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.service.tour.TourService;
import com.koing.server.koing_server.service.tour.TourSurveyService;
import com.koing.server.koing_server.service.tour.dto.TourCreateDto;
import com.koing.server.koing_server.service.tour.dto.TourSurveyCreateDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@Api(tags = "TourSurvey")
@RequestMapping("/tour-survey")
@RestController
@RequiredArgsConstructor
public class TourSurveyController {

    private final Logger LOGGER = LoggerFactory.getLogger(TourSurveyController.class);
    private final TourSurveyService tourSurveyService;

    @ApiOperation("TourSurvey - 투어 설문을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "TourSurvey - 투어 설문 생성 성공"),
            @ApiResponse(code = 402, message = "투어 설문 생성 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(code = 402, message = "투어 설문 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping("")
    public SuperResponse createTourSurvey(@RequestBody TourSurveyCreateDto tourSurveyCreateDto) throws BoilerplateException {
        LOGGER.info("[TourSurveyController] 투어 설문 생성 시도");
        SuperResponse createTourResponse;
        try {
            createTourResponse = tourSurveyService.createTourSurvey(tourSurveyCreateDto, CreateStatus.COMPLETE);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[TourSurveyController] 투어 생성 생성 성공");
        return createTourResponse;
    }


    @ApiOperation("TourSurvey - 투어 설문 임시 저장합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "TourSurvey - 투어 설문 임시저장 성공"),
            @ApiResponse(code = 402, message = "투어 설문 생성 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(code = 402, message = "투어 설문 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping("/temporary")
    public SuperResponse createTemporaryTourSurvey(@RequestBody TourSurveyCreateDto tourSurveyCreateDto) throws BoilerplateException {
        LOGGER.info("[TourSurveyController] 투어 설문 임시 저장 시도");
        SuperResponse createTemporaryTourResponse;
        try {
            createTemporaryTourResponse = tourSurveyService.createTourSurvey(tourSurveyCreateDto, CreateStatus.CREATING);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[TourSurveyController] 투어 생성 임시 저장 성공");
        return createTemporaryTourResponse;
    }


    @ApiOperation("TourSurvey - 투어 설문을 업데이트 합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "TourSurvey - 투어 설문 업데이트 성공"),
            @ApiResponse(code = 402, message = "투어 설문 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(code = 404, message = "해당 투어를 찾을 수 없습니다."),
            @ApiResponse(code = 404, message = "해당 투어 설문을 찾을 수 없습니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PatchMapping("/{tourId}")
    public SuperResponse updateTourSurvey(
            @PathVariable("tourId") Long tourId,
            @RequestBody TourSurveyCreateDto tourSurveyCreateDto
    ) throws BoilerplateException {
        LOGGER.info("[TourSurveyController] 투어 설문 업데이트 시도");
        SuperResponse updateTourResponse;
        try {
            updateTourResponse = tourSurveyService.updateTourSurvey(
                    tourId,
                    tourSurveyCreateDto,
                    null
            );
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[TourSurveyController] 투어 생성 업데이트 성공");
        return updateTourResponse;
    }


    @ApiOperation("TourSurvey - 투어 설문을 complete 합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "TourSurvey - 투어 설문 complete 성공"),
            @ApiResponse(code = 402, message = "투어 설문 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(code = 404, message = "해당 투어를 찾을 수 없습니다."),
            @ApiResponse(code = 404, message = "해당 투어 설문을 찾을 수 없습니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PatchMapping("/complete/{tourId}")
    public SuperResponse completeTourSurvey(
            @PathVariable("tourId") Long tourId,
            @RequestBody TourSurveyCreateDto tourSurveyCreateDto
    ) throws BoilerplateException {
        LOGGER.info("[TourSurveyController] 투어 설문 complete 시도");
        SuperResponse completeTourResponse;
        try {
            completeTourResponse = tourSurveyService.updateTourSurvey(
                    tourId,
                    tourSurveyCreateDto,
                    CreateStatus.COMPLETE
            );
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[TourSurveyController] 투어 생성 complete 성공");
        return completeTourResponse;
    }

}
