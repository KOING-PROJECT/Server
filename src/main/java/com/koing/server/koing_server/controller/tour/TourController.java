package com.koing.server.koing_server.controller.tour;

import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.service.tour.TourService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Tour")
@RequestMapping("/tour")
@RestController
@RequiredArgsConstructor
public class TourController {

    private final Logger LOGGER = LoggerFactory.getLogger(TourController.class);
    private final TourService tourService;

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


}
