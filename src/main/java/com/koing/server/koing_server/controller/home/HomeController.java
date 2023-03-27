package com.koing.server.koing_server.controller.home;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.service.sign.dto.SignUpRequestDto;
import com.koing.server.koing_server.service.tour.TourService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Home")
@RequestMapping("/home")
@RestController
@RequiredArgsConstructor
public class HomeController {

    private final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
    private final TourService tourService;

    @ApiOperation("Home : 카테고리별 투어를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Home : 카테고리별 투어 조회 성공"),
            @ApiResponse(code = 404, message = "존재하지 않는 페이지 입니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @GetMapping("/tours/{categoryIndex}")
    public SuperResponse getToursByCategory(@PathVariable("categoryIndex") String categoryIndex) {
        LOGGER.info("[HomeController] 카테고리별 투어 조회 시도");
        SuperResponse getToursByCategoryResponse;
        try {
            getToursByCategoryResponse = tourService.getToursByTourCategory(categoryIndex);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[HomeController] 카테고리별 투어 조회 성공");

        return getToursByCategoryResponse;
    }

}
