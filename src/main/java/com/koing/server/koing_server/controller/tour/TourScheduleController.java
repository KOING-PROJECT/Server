package com.koing.server.koing_server.controller.tour;

import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.service.tour.TourScheduleService;
import com.koing.server.koing_server.service.tour.TourService;
import com.koing.server.koing_server.service.tour.dto.TourScheduleDto;
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
    public SuperResponse createTourSchedule(@RequestBody TourScheduleDto tourScheduleDto) {
        LOGGER.info("[TourScheduleController] 투어 스케줄 생성 시도");
        SuperResponse createTourScheduleResponse = tourScheduleService.createTourSchedule(tourScheduleDto);
        LOGGER.info("[TourScheduleController] 투어 스케줄 생성 성공");
        return createTourScheduleResponse;
    }

}
