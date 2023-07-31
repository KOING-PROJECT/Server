package com.koing.server.koing_server.controller.tour;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.enums.CreateStatus;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.service.tour.TourSurveyService;
import com.koing.server.koing_server.service.tour.dto.TourSurveyCreateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@Tag(name = "TourSurvey", description = "TourSurvey API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/tour-survey")
public class TourSurveyController {

    private final Logger LOGGER = LoggerFactory.getLogger(TourSurveyController.class);
    private final TourSurveyService tourSurveyService;

    @Operation(description = "TourSurvey - 투어 설문을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "TourSurvey - 투어 설문 생성 성공"),
            @ApiResponse(responseCode = "402", description = "투어 설문 생성 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(responseCode = "402", description = "투어 설문 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
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


    @Operation(description = "TourSurvey - 투어 설문 임시 저장합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "TourSurvey - 투어 설문 임시저장 성공"),
            @ApiResponse(responseCode = "402", description = "투어 설문 생성 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(responseCode = "402", description = "투어 설문 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
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


    @Operation(description = "TourSurvey - 투어 설문을 업데이트 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "TourSurvey - 투어 설문 업데이트 성공"),
            @ApiResponse(responseCode = "402", description = "투어 설문 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(responseCode = "404", description = "해당 투어를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 투어 설문을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
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


    @Operation(description = "TourSurvey - 투어 설문을 complete 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "TourSurvey - 투어 설문 complete 성공"),
            @ApiResponse(responseCode = "402", description = "투어 설문 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(responseCode = "404", description = "해당 투어를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 투어 설문을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
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
