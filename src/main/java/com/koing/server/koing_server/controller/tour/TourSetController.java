package com.koing.server.koing_server.controller.tour;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.enums.CreateStatus;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.success.SuccessCode;
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

@Api(tags = "TourSet")
@RequestMapping("/tour-set")
@RestController
@RequiredArgsConstructor
public class TourSetController {

    private final Logger LOGGER = LoggerFactory.getLogger(TourSetController.class);
    private final TourService tourService;
    private final TourScheduleService tourScheduleService;
    private final TourApplicationService tourApplicationService;

    @ApiOperation("Tour - 투어, 투어 스케줄, 투어 신청서를 생성 합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tour - 투어, 투어 스케줄, 투어 신청서 생성 성공"),
            @ApiResponse(code = 404, message = "존재하지 않는 페이지 입니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping("")
    @Transactional
    public SuperResponse createTourSet(@RequestBody TourSetCreateDto tourSetCreateDto) {
        // 전체 tour set(투어, 투어 스케줄, 투어 신청서)을 생성
        LOGGER.info("[TourController] 투어 세트 생성 시도");

        TourCreateDto tourCreateDto = new TourCreateDto(tourSetCreateDto);
        LOGGER.info("[TourController] 투어 createDto 생성");
        SuperResponse tourResponse = tourService.createTour(tourCreateDto, CreateStatus.COMPLETE);

        if (tourResponse instanceof ErrorResponse) {
            return tourResponse;
        }

        if (!(tourResponse.getData() instanceof TourDto)) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        Long tourId = ((TourDto) tourResponse.getData()).getTourId();

        TourScheduleCreateDto tourScheduleCreateDto = new TourScheduleCreateDto(tourId, tourSetCreateDto);
        LOGGER.info("[TourController] 투어 createScheduleDto 생성");
        SuperResponse tourScheduleResponse = tourScheduleService.createTourSchedule(
                tourScheduleCreateDto, CreateStatus.COMPLETE
        );

        if (tourScheduleResponse instanceof ErrorResponse) {
            return tourScheduleResponse;
        }

        TourApplicationCreateDto tourApplicationCreateDto = new TourApplicationCreateDto(tourId);
        LOGGER.info("[TourController] 투어 createApplicationDto 생성");
        SuperResponse tourApplicationResponse = tourApplicationService.createTourApplication(tourApplicationCreateDto);

        if (tourApplicationResponse instanceof ErrorResponse) {
            return tourApplicationResponse;
        }

        LOGGER.info("[TourController] 투어 세트 생성 성공");
        return SuccessResponse.success(
                SuccessCode.TOUR_SET_CREATE_SUCCESS,
                tourResponse.getData()
        );
    }


    @ApiOperation("Tour - 투어, 투어 스케줄, 투어 신청서를 업데이트 합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tour - 투어, 투어 스케줄, 투어 신청서 업데이트 성공"),
            @ApiResponse(code = 404, message = "존재하지 않는 페이지 입니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PatchMapping("/{tourId}")
    @Transactional
    public SuperResponse updateTourSet(
            @PathVariable("tourId") Long tourId,
            @RequestBody TourSetCreateDto tourSetCreateDto
    ) {
        // 전체 tour set(투어, 투어 스케줄, 투어 신청서)을 업데이트
        LOGGER.info("[TourController] 투어 세트 업데이트 시도");

        if (tourApplicationService.checkDateExistParticipant(tourId, tourSetCreateDto.getTourDates())) {
            return ErrorResponse.error(ErrorCode.NOT_ACCEPTABLE_TOUR_APPLICATION_HAVE_PARTICIPANT_EXCEPTION);
        }

        LOGGER.info("[TourController] 원래 투어 날짜들의 신청서 삭제 시도");
        SuperResponse tourApplicationDeleteResponse =
                tourApplicationService.updateTourApplication(
                        tourId,
                        tourSetCreateDto.getTourDates(),
                        tourSetCreateDto.getParticipant()
                );
        if (tourApplicationDeleteResponse instanceof ErrorResponse) {
            return tourApplicationDeleteResponse;
        }
        LOGGER.info("[TourController] 원래 투어 날짜들의 신청서 삭제 성공");

        TourScheduleCreateDto tourScheduleUpdateDto = new TourScheduleCreateDto(tourId, tourSetCreateDto);
        LOGGER.info("[TourController] 투어 set update createScheduleDto 생성");
        SuperResponse tourScheduleUpdateResponse = tourScheduleService.updateTourSchedule(
                tourId, tourScheduleUpdateDto, null
        );

        if (tourScheduleUpdateResponse instanceof ErrorResponse) {
            return tourScheduleUpdateResponse;
        }

        TourCreateDto tourUpdateDto = new TourCreateDto(tourSetCreateDto);
        LOGGER.info("[TourController] 투어 set update createDto 생성");
        SuperResponse tourUpdateResponse = tourService.updateTour(tourId, tourUpdateDto, null);

        if (tourUpdateResponse instanceof ErrorResponse) {
            return tourUpdateResponse;
        }

        if (!(tourUpdateResponse.getData() instanceof TourDto)) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }

        LOGGER.info("[TourController] 투어 세트 업데이트 성공");
        return SuccessResponse.success(
                SuccessCode.TOUR_SET_UPDATE_SUCCESS,
                tourUpdateResponse.getData()
        );
    }


    @ApiOperation("Tour - 투어, 투어 스케줄을 임시 저장합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tour - 투어, 투어 스케줄을 임시 저장 성공"),
            @ApiResponse(code = 404, message = "존재하지 않는 페이지 입니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping("/temporary")
    @Transactional
    public SuperResponse createTemporaryTourAndTourSchedule(@RequestBody TourSetCreateDto tourSetCreateDto) {
        // 투어, 투어 스케줄을 임시 저장
        LOGGER.info("[TourController] temporaryTour, temporaryTourSchedule 생성 시도");

        TourCreateDto temporaryTourCreateDto = new TourCreateDto(tourSetCreateDto);
        LOGGER.info("[TourController] temporaryTour createDto 생성");
        SuperResponse temporaryTourResponse = tourService.createTour(temporaryTourCreateDto, CreateStatus.CREATING);

        if (temporaryTourResponse instanceof ErrorResponse) {
            return temporaryTourResponse;
        }

        if (!(temporaryTourResponse.getData() instanceof TourDto)) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        Long tourId = ((TourDto) temporaryTourResponse.getData()).getTourId();

        TourScheduleCreateDto temporaryTourScheduleCreateDto = new TourScheduleCreateDto(tourId, tourSetCreateDto);
        LOGGER.info("[TourController] temporaryTourSchedule createScheduleDto 생성");
        SuperResponse temporaryTourSchedule = tourScheduleService.createTourSchedule(
                temporaryTourScheduleCreateDto, CreateStatus.CREATING
        );

        if (temporaryTourSchedule instanceof ErrorResponse) {
            return temporaryTourSchedule;
        }

        LOGGER.info("[TourController] temporaryTour, temporaryTourSchedule 생성 성공");
        return SuccessResponse.success(
                SuccessCode.TEMPORARY_TOUR_TOUR_SCHEDULE_CREATE_SUCCESS,
                temporaryTourResponse.getData()
        );
    }


    @ApiOperation("Tour - 임시 저장한 투어, 투어 스케줄을 update 합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tour - 임시 저장한 투어, 투어 스케줄을 update 성공"),
            @ApiResponse(code = 404, message = "존재하지 않는 페이지 입니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PatchMapping("/temporary/{tourId}")
    @Transactional
    public SuperResponse updateTemporaryTourAndTourSchedule(
            @PathVariable("tourId") Long tourId,
            @RequestBody TourSetCreateDto tourSetCreateDto
    ) {
        // 임시 저장한 투어, 투어 스케줄을 update
        LOGGER.info("[TourController] temporaryTour, temporaryTourSchedule update 시도");

        TourCreateDto temporaryTourCreateDto = new TourCreateDto(tourSetCreateDto);
        LOGGER.info("[TourController] temporaryTour createDto 생성");
        SuperResponse temporaryTourUpdateResponse = tourService.updateTour(tourId, temporaryTourCreateDto, null);

        if (temporaryTourUpdateResponse instanceof ErrorResponse) {
            return temporaryTourUpdateResponse;
        }

        TourScheduleCreateDto temporaryTourScheduleCreateDto = new TourScheduleCreateDto(tourId, tourSetCreateDto);
        LOGGER.info("[TourController] temporaryTourSchedule createScheduleDto 생성");
        SuperResponse temporaryTourScheduleUpdate = tourScheduleService.updateTourSchedule(
                tourId, temporaryTourScheduleCreateDto, null
        );

        if (temporaryTourScheduleUpdate instanceof ErrorResponse) {
            return temporaryTourScheduleUpdate;
        }

        LOGGER.info("[TourController] temporaryTour, temporaryTourSchedule update 성공");
        return SuccessResponse.success(
                SuccessCode.TEMPORARY_TOUR_TOUR_SCHEDULE_UPDATE_SUCCESS,
                temporaryTourUpdateResponse.getData()
        );
    }


    @ApiOperation("Tour - 임시 저장한 투어, 투어 스케줄을 완성하고 투어 신청서를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tour - 임시 저장한 투어, 투어 스케줄을 완성, 투어 신청서를 생성 성공."),
            @ApiResponse(code = 404, message = "존재하지 않는 페이지 입니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PatchMapping("/complete/{tourId}")
    @Transactional
    public SuperResponse completeTourSet(
            @PathVariable("tourId") Long tourId,
            @RequestBody TourSetCreateDto tourSetCreateDto
    ) {
        // 임시 저장한 tour set(투어, 투어 스케줄, 투어 신청서)을 완성
        LOGGER.info("[TourController] temporaryTour, temporaryTourSchedule complete 시도");

        TourCreateDto completeTourCreateDto = new TourCreateDto(tourSetCreateDto);
        LOGGER.info("[TourController] completeTour createDto 생성");
        SuperResponse completeTourUpdateResponse = tourService.updateTour(tourId, completeTourCreateDto, CreateStatus.COMPLETE);

        if (completeTourUpdateResponse instanceof ErrorResponse) {
            return completeTourUpdateResponse;
        }

        TourScheduleCreateDto completeTourScheduleCreateDto = new TourScheduleCreateDto(tourId, tourSetCreateDto);
        LOGGER.info("[TourController] completeTourSchedule createScheduleDto 생성");
        SuperResponse completeTourScheduleUpdate = tourScheduleService.updateTourSchedule(
                tourId, completeTourScheduleCreateDto, CreateStatus.COMPLETE
        );

        if (completeTourScheduleUpdate instanceof ErrorResponse) {
            return completeTourScheduleUpdate;
        }

        TourApplicationCreateDto tourApplicationCreateDto = new TourApplicationCreateDto(tourId);
        LOGGER.info("[TourController] tourApplication createApplicationDto 생성");
        SuperResponse tourApplication = tourApplicationService.createTourApplication(tourApplicationCreateDto);

        if (tourApplication instanceof ErrorResponse) {
            return tourApplication;
        }

        LOGGER.info("[TourController] temporaryTour, temporaryTourSchedule complete 성공");

        return SuccessResponse.success(
                SuccessCode.TEMPORARY_TOUR_SET_COMPLETE_SUCCESS,
                completeTourUpdateResponse.getData()
        );
    }

}
