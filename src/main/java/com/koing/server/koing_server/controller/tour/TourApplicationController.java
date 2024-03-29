package com.koing.server.koing_server.controller.tour;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.service.tour.TourApplicationService;
import com.koing.server.koing_server.service.tour.dto.TourApplicationCancelDto;
import com.koing.server.koing_server.service.tour.dto.TourApplicationCreateDto;
import com.koing.server.koing_server.service.tour.dto.TourApplicationParticipateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@Tag(name = "TourApplication", description = "TourApplication API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/tour-application")
public class TourApplicationController {

    private final Logger LOGGER = LoggerFactory.getLogger(TourApplicationController.class);
    private final TourApplicationService tourApplicationService;

    @Operation(description = "TourApplication - 투어 신청서를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "TourApplication - 투어 신청서 생성 성공"),
            @ApiResponse(responseCode = "402", description = "투어 카테고리 생성과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(responseCode = "402", description = "투어 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(responseCode = "404", description = "해당 투어를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
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

    @Operation(description = "TourApplication - 투어를 신청합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "TourApplication - 투어 신청 성공"),
            @ApiResponse(responseCode = "402", description = "투어 신청서 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(responseCode = "402", description = "투어 신청 내역 생성 과정에서 오류가 발생했습니다."),
            @ApiResponse(responseCode = "402", description = "투어 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(responseCode = "404", description = "해당 투어를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 투어 신청서를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "404", description = "탈퇴했거나 존재하지 않는 유저입니다."),
            @ApiResponse(responseCode = "406", description = "해당 투어의 정원을 초과했습니다."),
            @ApiResponse(responseCode = "406", description = "이미 신청한 팀이 있는 투어입니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
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
            System.out.println(exception);
            System.out.println(exception.getMessage());
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[TourApplicationController] 투어 신청 성공");

        return participateTourApplicationResponse;
    }


    @Operation(description = "TourApplication - 신청한 투어를 취소합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "TourApplication - 투어 취소 성공"),
            @ApiResponse(responseCode = "402", description = "투어 신청서 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(responseCode = "402", description = "투어 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(responseCode = "402", description = "유저 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(responseCode = "404", description = "해당 투어를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 투어 신청서를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "404", description = "탈퇴했거나 존재하지 않는 유저입니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @DeleteMapping("/cancel")
    public SuperResponse cancelTour(@RequestBody TourApplicationCancelDto tourApplicationCancelDto) {
        LOGGER.info("[TourApplicationController] 투어 신청 취소 시도");
        SuperResponse cancelTourApplicationResponse;
        try {
            cancelTourApplicationResponse = tourApplicationService.cancelTour(tourApplicationCancelDto);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            System.out.println(exception);
            System.out.println(exception.getMessage());
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[TourApplicationController] 투어 신청 취소 성공");

        return cancelTourApplicationResponse;
    }


    @Operation(description = "TourApplication - 투어 신청서 리스트를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "TourApplication - 투어 신청서 리스트 조회 성공"),
            @ApiResponse(responseCode = "402", description = "해당 유저를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "402", description = "해당 투어 신청서를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
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


    @Operation(description = "TourApplication - 투어에 참가하는 투어리스트 리스트를 가져옵니다. (날짜 형식을 20221225 이런 식으로 줘야합니다)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "TourApplication - 투어에 참가하는 투어리스트 리스트를 조회 성공"),
            @ApiResponse(responseCode = "402", description = "해당 투어 신청서를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
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


    @Operation(description = "TourApplication - 가이드가 시작 버튼을 누릅니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "TourApplication - 가이드 시작 버튼 클릭 성공"),
            @ApiResponse(responseCode = "402", description = "투어 신청서 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(responseCode = "404", description = "해당 투어를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 투어 신청서를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "404", description = "탈퇴했거나 존재하지 않는 유저입니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
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

    @Operation(description = "TourApplication - 투어리스트가 시작 버튼을 누릅니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "TourApplication - 투어리스트 시작 버튼 클릭 성공"),
            @ApiResponse(responseCode = "402", description = "투어 신청서 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(responseCode = "404", description = "해당 투어를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 투어 신청서를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "404", description = "탈퇴했거나 존재하지 않는 유저입니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PatchMapping("/start/tourist/{tourId}/{tourDate}/{touristId}")
    public SuperResponse touristPressTourStart(
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

    @Operation(description = "TourApplication - 투어를 시작 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "TourApplication - 투어 시작 성공"),
            @ApiResponse(responseCode = "402", description = "투어 신청서 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(responseCode = "404", description = "해당 투어를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 투어 신청서를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "404", description = "탈퇴했거나 존재하지 않는 유저입니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
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


    @Operation(description = "TourApplication - 가이드가 종료 버튼을 누릅니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "TourApplication - 가이드 종료 버튼 클릭 성공"),
            @ApiResponse(responseCode = "402", description = "투어 신청서 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(responseCode = "404", description = "해당 투어를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 투어 신청서를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "404", description = "탈퇴했거나 존재하지 않는 유저입니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PatchMapping("/end/guide/{tourId}")
    public SuperResponse guidePressTourEnd(
            @PathVariable("tourId") Long tourId
    ) {
        LOGGER.info("[TourApplicationController] 가이드 투어 종료 시도");
        SuperResponse guidePressTourEndResponse;
        try {
            guidePressTourEndResponse = tourApplicationService.guideEndTour(tourId);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[TourApplicationController] 가이드 투어 종료 성공");

        return guidePressTourEndResponse;
    }


    @Operation(description = "TourApplication - 투어리스트가 종료 버튼을 누릅니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "TourApplication - 투어리스트 시작 버튼 클릭 성공"),
            @ApiResponse(responseCode = "402", description = "투어 신청서 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(responseCode = "404", description = "해당 투어를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 투어 신청서를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "404", description = "탈퇴했거나 존재하지 않는 유저입니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PatchMapping("/end/tourist/{tourId}/{tourDate}/{touristId}")
    public SuperResponse touristPressTourEnd(
            @PathVariable("tourId") Long tourId,
            @PathVariable("tourDate") String tourDate,
            @PathVariable("touristId") Long touristId
    ) {
        LOGGER.info("[TourApplicationController] 투어리스트 투어 종료 시도");
        SuperResponse touristPressTourEndResponse;
        try {
            touristPressTourEndResponse = tourApplicationService.touristEndTour(tourId, tourDate ,touristId);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[TourApplicationController] 투어리스트 투어 종료 성공");

        return touristPressTourEndResponse;
    }


    @Operation(description = "TourApplication - 투어를 종료 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "TourApplication - 투어 종료 성공"),
            @ApiResponse(responseCode = "402", description = "투어 신청서 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(responseCode = "404", description = "해당 투어를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 투어 신청서를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "404", description = "탈퇴했거나 존재하지 않는 유저입니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PatchMapping("/end/tour/{tourId}/{tourDate}")
    public SuperResponse checkTourEnd(
            @PathVariable("tourId") Long tourId,
            @PathVariable("tourDate") String tourDate
    ) {
        LOGGER.info("[TourApplicationController] 투어 종료 시도");
        SuperResponse endTourResponse;
        try {
            endTourResponse = tourApplicationService.checkEndTour(tourId, tourDate);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[TourApplicationController] 투어 종료 성공");

        return endTourResponse;
    }

}
