package com.koing.server.koing_server.controller.tour;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.enums.CreateStatus;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.service.tour.TourScheduleService;
import com.koing.server.koing_server.service.tour.dto.TourScheduleCreateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@Tag(name = "TourSchedule", description = "TourSchedule API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/tour-schedule")
public class TourScheduleController {

    private final Logger LOGGER = LoggerFactory.getLogger(TourScheduleController.class);
    private final TourScheduleService tourScheduleService;

    @Operation(description = "TourSchedule - 투어 스케줄을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "TourSchedule - 투어 스케줄 생성 성공"),
            @ApiResponse(responseCode = "401", description = "토큰이 없습니다."),
            @ApiResponse(responseCode = "402", description = "투어 세부 스케줄 생성 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(responseCode = "402", description = "투어 스케줄 생성 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(responseCode = "402", description = "투어 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(responseCode = "404", description = "해당 투어를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping("")
    public SuperResponse createTourSchedule(@RequestBody TourScheduleCreateDto tourScheduleCreateDto) {
        LOGGER.info("[TourScheduleController] 투어 스케줄 생성 시도");

        SuperResponse createTourScheduleResponse;
        try {
            createTourScheduleResponse = tourScheduleService.createTourSchedule(
                    tourScheduleCreateDto,
                    CreateStatus.COMPLETE
            );
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[TourScheduleController] 투어 스케줄 생성 성공");

        return createTourScheduleResponse;
    }


    @Operation(description = "TourSchedule - 생성 중인 투어 스케줄을 임시 저장 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "TourSchedule - 투어 스케줄 임시 저장 성공"),
            @ApiResponse(responseCode = "401", description = "토큰이 없습니다."),
            @ApiResponse(responseCode = "402", description = "투어 세부 스케줄 생성 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(responseCode = "402", description = "투어 스케줄 생성 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(responseCode = "402", description = "투어 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(responseCode = "404", description = "해당 투어를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping("/temporary")
    public SuperResponse createTemporaryTourSchedule(@RequestBody TourScheduleCreateDto tourScheduleCreateDto) {
        LOGGER.info("[TourScheduleController] 생성 중인 투어 스케줄 임시 저장 시도");

        SuperResponse createTourScheduleResponse;
        try {
            createTourScheduleResponse = tourScheduleService.createTourSchedule(
                    tourScheduleCreateDto,
                    CreateStatus.CREATING
            );
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[TourScheduleController] 생성 중인 투어 스케줄 임시 저장 성공");

        return createTourScheduleResponse;
    }


    @Operation(description = "TourSchedule - 투어 스케줄을 update 합니다.(완성 tourSchedule 혹은 임시저장 tourSchedule를 업데이트)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "TourSchedule - 투어 스케줄 update 성공"),
            @ApiResponse(responseCode = "404", description = "해당 tourSchedule이 존재하지 않습니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PatchMapping("/{tourId}")
    public SuperResponse updateTourSchedule(
            @PathVariable("tourId") Long tourId,
            @RequestBody TourScheduleCreateDto tourScheduleCreateDto) {
        // 완성 된 tourSchedule를 수정하거나, 임시 저장 중인 tourSchedule를 다시 임시 저장 할 때 사용
        LOGGER.info("[TourScheduleController] 투어 스케줄 update 시도");
        SuperResponse updateTourScheduleResponse;
        try {
            updateTourScheduleResponse = tourScheduleService.updateTourSchedule(
                    tourId, tourScheduleCreateDto, null);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[TourScheduleController] 투어 스케줄 update 성공");

        return updateTourScheduleResponse;
    }


    @Operation(description = "TourSchedule - 투어 스케줄을 complete 합니다.(임시저장 tourSchedule를 완성)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "TourSchedule - 투어 스케줄 complete 성공"),
            @ApiResponse(responseCode = "404", description = "해당 tourSchedule이 존재하지 않습니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PatchMapping("/complete/{tourId}")
    public SuperResponse completeTourSchedule(
            @PathVariable("tourId") Long tourId,
            @RequestBody TourScheduleCreateDto tourScheduleCreateDto) {
        // 임시 저장 중인 tourSchedule를 complete 할 때 사용
        LOGGER.info("[TourScheduleController] 투어 스케줄 complete 시도");
        SuperResponse completeTourScheduleResponse;
        try {
            completeTourScheduleResponse = tourScheduleService.updateTourSchedule(
                    tourId, tourScheduleCreateDto, CreateStatus.COMPLETE);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[TourScheduleController] 투어 스케줄 complete 성공");

        return completeTourScheduleResponse;
    }

}
