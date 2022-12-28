package com.koing.server.koing_server.controller.tour;

import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.enums.CreateStatus;
import com.koing.server.koing_server.service.tour.TourScheduleService;
import com.koing.server.koing_server.service.tour.dto.TourScheduleCreateDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@Api(tags = "TourSchedule")
@RequestMapping("/tour-schedule")
@RestController
@RequiredArgsConstructor
public class TourScheduleController {

    private final Logger LOGGER = LoggerFactory.getLogger(TourScheduleController.class);
    private final TourScheduleService tourScheduleService;

    @ApiOperation("TourSchedule - 투어 스케줄을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "TourSchedule - 투어 스케줄 생성 성공"),
            @ApiResponse(code = 401, message = "토큰이 없습니다."),
            @ApiResponse(code = 404, message = "존재하지 않는 페이지 입니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping("")
    public SuperResponse createTourSchedule(@RequestBody TourScheduleCreateDto tourScheduleCreateDto) {
        LOGGER.info("[TourScheduleController] 투어 스케줄 생성 시도");
        SuperResponse createTourScheduleResponse = tourScheduleService.createTourSchedule(
                tourScheduleCreateDto,
                CreateStatus.COMPLETE
        );
        LOGGER.info("[TourScheduleController] 투어 스케줄 생성 성공");
        return createTourScheduleResponse;
    }


    @ApiOperation("TourSchedule - 생성 중인 투어 스케줄을 임시 저장 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "TourSchedule - 투어 스케줄 임시 저장 성공"),
            @ApiResponse(code = 401, message = "토큰이 없습니다."),
            @ApiResponse(code = 404, message = "존재하지 않는 페이지 입니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping("/temporary")
    public SuperResponse createTemporaryTourSchedule(@RequestBody TourScheduleCreateDto tourScheduleCreateDto) {
        LOGGER.info("[TourScheduleController] 생성 중인 투어 스케줄 임시 저장 시도");
        SuperResponse createTourScheduleResponse = tourScheduleService.createTourSchedule(
                tourScheduleCreateDto,
                CreateStatus.CREATING
        );
        LOGGER.info("[TourScheduleController] 생성 중인 투어 스케줄 임시 저장 성공");
        return createTourScheduleResponse;
    }


    @ApiOperation("TourSchedule - 투어 스케줄을 update 합니다.(완성 tourSchedule 혹은 임시저장 tourSchedule를 업데이트)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "TourSchedule - 투어 스케줄 update 성공"),
            @ApiResponse(code = 404, message = "해당 tourSchedule이 존재하지 않습니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PatchMapping("/{tourId}")
    public SuperResponse updateTourSchedule(
            @PathVariable("tourId") Long tourId,
            @RequestBody TourScheduleCreateDto tourScheduleCreateDto) {
        // 완성 된 tourSchedule를 수정하거나, 임시 저장 중인 tourSchedule를 다시 임시 저장 할 때 사용
        LOGGER.info("[TourScheduleController] 투어 스케줄 update 시도");
        SuperResponse updateTourScheduleResponse = tourScheduleService.updateTourSchedule(
                tourId, tourScheduleCreateDto, null);
        LOGGER.info("[TourScheduleController] 투어 스케줄 update 성공");
        return updateTourScheduleResponse;
    }


    @ApiOperation("TourSchedule - 투어 스케줄을 complete 합니다.(임시저장 tourSchedule를 완성)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "TourSchedule - 투어 스케줄 complete 성공"),
            @ApiResponse(code = 404, message = "해당 tourSchedule이 존재하지 않습니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PatchMapping("/complete/{tourId}")
    public SuperResponse completeTourSchedule(
            @PathVariable("tourId") Long tourId,
            @RequestBody TourScheduleCreateDto tourScheduleCreateDto) {
        // 임시 저장 중인 tourSchedule를 complete 할 때 사용
        LOGGER.info("[TourScheduleController] 투어 스케줄 complete 시도");
        SuperResponse completeTourScheduleResponse = tourScheduleService.updateTourSchedule(
                tourId, tourScheduleCreateDto, CreateStatus.COMPLETE);
        LOGGER.info("[TourScheduleController] 투어 스케줄 complete 성공");
        return completeTourScheduleResponse;
    }

}
