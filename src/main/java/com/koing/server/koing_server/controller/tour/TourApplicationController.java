package com.koing.server.koing_server.controller.tour;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.common.exception.NotFoundException;
import com.koing.server.koing_server.service.tour.TourApplicationService;
import com.koing.server.koing_server.service.tour.dto.TourApplicationCreateDto;
import com.koing.server.koing_server.service.tour.dto.TourApplicationParticipateDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@Api(tags = "TourApplication")
@RequestMapping("/tour-application")
@RestController
@RequiredArgsConstructor
public class TourApplicationController {

    private final Logger LOGGER = LoggerFactory.getLogger(TourApplicationController.class);
    private final TourApplicationService tourApplicationService;

    @ApiOperation("TourApplication - 투어 신청서를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "TourApplication - 투어 신청서 생성 성공"),
            @ApiResponse(code = 402, message = "투어 카테고리 생성과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(code = 402, message = "투어 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(code = 404, message = "해당 투어를 찾을 수 없습니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping("")
    public SuperResponse createTourApplication(@RequestBody TourApplicationCreateDto tourApplicationCreateDto) {
        LOGGER.info("[TourApplicationController] 투어 신청서 생성 시도");
        SuperResponse createTourApplicationResponse;
        try {
            createTourApplicationResponse = tourApplicationService.createTourApplication(tourApplicationCreateDto);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[TourApplicationController] 투어 신청서 생성 성공");

        return createTourApplicationResponse;
    }

    @ApiOperation("TourApplication - 투어를 신청합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "TourApplication - 투어 신청 성공"),
            @ApiResponse(code = 402, message = "투어 신청서 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(code = 404, message = "해당 투어를 찾을 수 없습니다."),
            @ApiResponse(code = 404, message = "해당 투어 신청서를 찾을 수 없습니다."),
            @ApiResponse(code = 404, message = "탈퇴했거나 존재하지 않는 유저입니다."),
            @ApiResponse(code = 406, message = "해당 투어의 정원을 초과했습니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping("/participate")
    public SuperResponse participateTour(@RequestBody TourApplicationParticipateDto tourApplicationParticipateDto) {
        LOGGER.info("[TourApplicationController] 투어 신청 시도");
        SuperResponse participateTourApplicationResponse;
        try {
            participateTourApplicationResponse = tourApplicationService.participateTour(tourApplicationParticipateDto);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[TourApplicationController] 투어 신청 성공");

        return participateTourApplicationResponse;
    }

    @ApiOperation("TourApplication - 투어 신청서 리스트를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "TourApplication - 투어 신청서 리스트 조회 성공"),
            @ApiResponse(code = 402, message = "해당 유저를 찾을 수 없습니다."),
            @ApiResponse(code = 402, message = "해당 투어 신청서를 찾을 수 없습니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @GetMapping("/{userId}")
    public SuperResponse getTourApplications(@PathVariable("userId") Long userId) {
        LOGGER.info("[TourApplicationController] 투어 신청서 리스트 조회 시도");

        SuperResponse getTourApplicationResponse;
        try {
            getTourApplicationResponse = tourApplicationService.getTourApplications(userId);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[TourApplicationController] 투어 신청 리스트 조회 성공");

        return getTourApplicationResponse;
    }


    @ApiOperation("TourApplication - 투어에 참가하는 투어리스트 리스트를 가져옵니다. (날짜 형식을 20221225 이런 식으로 줘야합니다)")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "TourApplication - 투어에 참가하는 투어리스트 리스트를 조회 성공"),
            @ApiResponse(code = 402, message = "해당 투어 신청서를 찾을 수 없습니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @GetMapping("/participants/{tourId}/{tourDate}")
    public SuperResponse getTourApplicationParticipants(
            @PathVariable("tourId") Long tourId,
            @PathVariable("tourDate") String tourDate
    ) {
        LOGGER.info("[TourApplicationController] 투어에 참가하는 투어리스트 리스트를 조회 시도");

        SuperResponse getTourApplicationParticipantsResponse;
        try {
            getTourApplicationParticipantsResponse = tourApplicationService.getTourApplicationParticipants(tourId, tourDate);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[TourApplicationController] 투어에 참가하는 투어리스트 리스트를 조회 성공");

        return getTourApplicationParticipantsResponse;
    }


    @ApiOperation("TourApplication - 가이드가 시작 버튼을 누릅니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "TourApplication - 가이드 시작 버튼 클릭 성공"),
            @ApiResponse(code = 402, message = "투어 신청서 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(code = 404, message = "해당 투어를 찾을 수 없습니다."),
            @ApiResponse(code = 404, message = "해당 투어 신청서를 찾을 수 없습니다."),
            @ApiResponse(code = 404, message = "탈퇴했거나 존재하지 않는 유저입니다."),
            @ApiResponse(code = 406, message = "해당 투어의 정원을 초과했습니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PatchMapping("/start/guide/{tourId}/{today}")
    public SuperResponse guidePressTourStart(
            @PathVariable("tourId") Long tourId,
            @PathVariable("today") String today
            ) {
        LOGGER.info("[TourApplicationController] 가이드 투어 시작 시도");
        SuperResponse guidePressTourStartResponse;
        try {
            guidePressTourStartResponse = tourApplicationService.guideStartTour(tourId, today);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[TourApplicationController] 가이드 투어 시작 성공");

        return guidePressTourStartResponse;
    }

    @ApiOperation("TourApplication - 투어리스트가 시작 버튼을 누릅니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "TourApplication - 투어리스트 시작 버튼 클릭 성공"),
            @ApiResponse(code = 402, message = "투어 신청서 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(code = 404, message = "해당 투어를 찾을 수 없습니다."),
            @ApiResponse(code = 404, message = "해당 투어 신청서를 찾을 수 없습니다."),
            @ApiResponse(code = 404, message = "탈퇴했거나 존재하지 않는 유저입니다."),
            @ApiResponse(code = 406, message = "해당 투어의 정원을 초과했습니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PatchMapping("/start/tourist/{tourId}/{tourDate}/{touristId}")
    public SuperResponse guidePressTourStart(
            @PathVariable("tourId") Long tourId,
            @PathVariable("tourDate") String tourDate,
            @PathVariable("touristId") Long touristId
    ) {
        LOGGER.info("[TourApplicationController] 투어리스트 투어 시작 시도");
        SuperResponse touristPressTourStartResponse;
        try {
            touristPressTourStartResponse = tourApplicationService.touristStartTour(tourId, tourDate ,touristId);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[TourApplicationController] 투어리스트 투어 시작 성공");

        return touristPressTourStartResponse;
    }

    @ApiOperation("TourApplication - 투어를 시작 합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "TourApplication - 투어 시작 성공"),
            @ApiResponse(code = 402, message = "투어 신청서 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(code = 404, message = "해당 투어를 찾을 수 없습니다."),
            @ApiResponse(code = 404, message = "해당 투어 신청서를 찾을 수 없습니다."),
            @ApiResponse(code = 404, message = "탈퇴했거나 존재하지 않는 유저입니다."),
            @ApiResponse(code = 406, message = "해당 투어의 정원을 초과했습니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PatchMapping("/start/tour/{tourId}/{tourDate}")
    public SuperResponse checkTourStart(
            @PathVariable("tourId") Long tourId,
            @PathVariable("tourDate") String tourDate
    ) {
        LOGGER.info("[TourApplicationController] 투어 시작 시도");
        SuperResponse startTourResponse;
        try {
            startTourResponse = tourApplicationService.checkStartTour(tourId, tourDate);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[TourApplicationController] 투어 시작 성공");

        return startTourResponse;
    }
}
