package com.koing.server.koing_server.controller.tour;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.enums.CreateStatus;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.service.tour.TourApplicationService;
import com.koing.server.koing_server.service.tour.TourScheduleService;
import com.koing.server.koing_server.service.tour.TourService;
import com.koing.server.koing_server.service.tour.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Tour")
@RequestMapping("/tour")
@RestController
@RequiredArgsConstructor
public class TourController {

    private final Logger LOGGER = LoggerFactory.getLogger(TourController.class);
    private final TourService tourService;
    private final TourScheduleService tourScheduleService;
    private final TourApplicationService tourApplicationService;

    @ApiOperation("Tour - 투어 리스트를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tour - 투어 리스트 가져오기 성공"),
            @ApiResponse(code = 401, message = "토큰이 없습니다."),
            @ApiResponse(code = 404, message = "존재하지 않는 페이지 입니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @GetMapping("")
    public SuperResponse getTours() {
        LOGGER.info("[TourController] 투어 리스트 조회 시도");
        SuperResponse getTourListResponse = tourService.getTours();
        LOGGER.info("[TourController] 투어 리스트 조회 성공");
        return getTourListResponse;
    }


    @ApiOperation("Tour - 투어를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tour - 생성 성공"),
            @ApiResponse(code = 401, message = "토큰이 없습니다."),
            @ApiResponse(code = 404, message = "존재하지 않는 페이지 입니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping("")
    public SuperResponse createTour(@RequestBody TourCreateDto tourCreateDto) {
        LOGGER.info("[TourController] 투어 생성 시도");
        SuperResponse createTourResponse = tourService.createTour(tourCreateDto, CreateStatus.COMPLETE);
        LOGGER.info("[TourController] 투어 생성 성공");
        return createTourResponse;
    }


    @ApiOperation("Tour - 생성중인 투어를 임시 저장합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tour - 투어 임시 저장 성공"),
            @ApiResponse(code = 401, message = "토큰이 없습니다."),
            @ApiResponse(code = 404, message = "존재하지 않는 페이지 입니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping("/temporary")
    public SuperResponse createTemporaryTour(@RequestBody TourCreateDto tourCreateDto) {
        LOGGER.info("[TourController] 생성 중인 투어 임시 저장 시도");
        SuperResponse createTourResponse = tourService.createTour(tourCreateDto, CreateStatus.CREATING);
        LOGGER.info("[TourController] 생성 중인 투어 임시 저장 성공");
        return createTourResponse;
    }


    @ApiOperation("Tour - 투어를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tour - 투어 삭제 성공"),
            @ApiResponse(code = 401, message = "삭제할 Tour가 존재하지 않습니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @DeleteMapping("/{tourId}")
    public SuperResponse deleteTour(@PathVariable("tourId") Long tourId) {
        LOGGER.info("[TourController] 투어 삭제 시도");
        SuperResponse deleteTourResponse = tourService.deleteTour(tourId);
        LOGGER.info("[TourController] 투어 삭제 성공");
        return deleteTourResponse;
    }


    @ApiOperation("Tour - 투어를 업데이트합니다.(완성 tour 혹은 임시저장 tour를 업데이트)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tour - 투어 업데이트 성공"),
            @ApiResponse(code = 404, message = "update할 투어가 존재하지 않습니다."),
            @ApiResponse(code = 406, message = "투어 Creator가 아닙니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PatchMapping("/{tourId}")
    public SuperResponse updateTour(
            @PathVariable("tourId") Long tourId,
            @RequestBody TourCreateDto tourCreateDto
    ) {
        // 완성 된 tour를 수정하거나, 임시 저장 중인 tour를 다시 임시 저장 할 때 사용
        LOGGER.info("[TourController] 투어 update 시도");
        SuperResponse updateTourResponse = tourService.updateTour(tourId, tourCreateDto, null);
        LOGGER.info("[TourController] 투어 update 성공");
        return updateTourResponse;
    }


    @ApiOperation("Tour - 투어를 complete 합니다.(임시저장 tour를 완성)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tour - 투어 complete 성공"),
            @ApiResponse(code = 404, message = "complete할 투어가 존재하지 않습니다."),
            @ApiResponse(code = 406, message = "투어 Creator가 아닙니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PatchMapping("/complete/{tourId}")
    public SuperResponse completeTour(
            @PathVariable("tourId") Long tourId,
            @RequestBody TourCreateDto tourCreateDto
    ) {
        // 완성 된 tour를 수정하거나, 임시 저장 중인 tour를 다시 임시 저장 할 때 사용
        LOGGER.info("[TourController] 투어 complete 시도");
        SuperResponse completeTourResponse = tourService.updateTour(tourId, tourCreateDto, CreateStatus.COMPLETE);
        LOGGER.info("[TourController] 투어 complete 성공");
        return completeTourResponse;
    }

    @ApiOperation("Tour - 투어 세부 정보를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tour - 투어 세부 정보 가져오기 성공"),
            @ApiResponse(code = 404, message = "해당 투어를 찾을 수 없습니다."),
            @ApiResponse(code = 404, message = "존재하지 않는 페이지 입니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @GetMapping("/{tourId}/{userId}")
    public SuperResponse getTourDetailInfo(
            @PathVariable("tourId") Long tourId,
            @PathVariable("userId") Long userId
    ) {
        LOGGER.info("[TourController] 투어 세부 정보 조회 시도");
        SuperResponse getTourDetailInfoResponse;
        try {
            getTourDetailInfoResponse = tourService.getTourDetailInfo(tourId, userId);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[TourController] 투어 세부 정보 조회 성공");
        return getTourDetailInfoResponse;
    }


    @ApiOperation("Tour - 투어 좋아요를 누릅니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tour - 투어 좋아요 누르기 성공"),
            @ApiResponse(code = 404, message = "해당 투어를 찾을 수 없습니다."),
            @ApiResponse(code = 404, message = "해당 유저를 찾을 수 없습니다."),
            @ApiResponse(code = 404, message = "존재하지 않는 페이지 입니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PatchMapping("/like/{tourId}/{userId}")
    public SuperResponse pressLikeTour(
            @PathVariable("tourId") Long tourId,
            @PathVariable("userId") Long userId
    ) {
        LOGGER.info("[TourController] 투어 좋아요 업데이트 시도");
        SuperResponse pressLikeTourResponse;
        try {
            pressLikeTourResponse = tourService.pressLikeTour(tourId, userId);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[TourController] 투어 좋아요 업데이트 성공");
        return pressLikeTourResponse;
    }


    @ApiOperation("Tour - 좋아요 누른 투어 리스트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tour - 좋아요 누른 투어 리스트를 조회 성공"),
            @ApiResponse(code = 404, message = "해당 유저를 찾을 수 없습니다."),
            @ApiResponse(code = 404, message = "존재하지 않는 페이지 입니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @GetMapping("/like/{userId}")
    public SuperResponse pressLikeTour(@PathVariable("userId") Long userId) {
        LOGGER.info("[TourController] 좋아요 누른 투어 리스트 조회 시도");
        SuperResponse getLikeTourListResponse;
        try {
            getLikeTourListResponse = tourService.getLikeTours(userId);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[TourController] 좋아요 누른 투어 리스트 조회 성공");
        return getLikeTourListResponse;
    }

}
