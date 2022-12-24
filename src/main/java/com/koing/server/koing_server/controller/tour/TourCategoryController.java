package com.koing.server.koing_server.controller.tour;

import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.service.tour.TourCategoryService;
import com.koing.server.koing_server.service.tour.dto.TourCategoryCreateDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@Api(tags = "TourCategory")
@RequestMapping("/tour-category")
@RestController
@RequiredArgsConstructor
public class TourCategoryController {

    private final Logger LOGGER = LoggerFactory.getLogger(TourCategoryController.class);
    private final TourCategoryService tourCategoryService;


    @ApiOperation("TourCategories - 투어 카테고리 리스트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "TourCategories - 투어 카테고리 리스트 조회 성공"),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @GetMapping("")
    public SuperResponse getTourCategories() {
        LOGGER.info("[TourCategoryController] 투어 카테고리 리스트 조회 시도");
        SuperResponse getTourCategoriesResponse = tourCategoryService.getTourCategories();
        LOGGER.info("[TourCategoryController] 투어 카테고리 리스트 조회 성공");
        return getTourCategoriesResponse;
    }


    @ApiOperation("TourCategory - 투어 카테고리를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "TourCategory - 투어 카테고리 생성 성공"),
            @ApiResponse(code = 402, message = "투어 카테고리 생성과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping("")
    public SuperResponse createTourCategory(@RequestBody TourCategoryCreateDto tourCategoryCreateDto) {
        LOGGER.info("[TourCategoryController] 투어 카테고리 생성 시도");
        SuperResponse createTourCategoryResponse = tourCategoryService.createTourCategory(tourCategoryCreateDto);
        LOGGER.info("[TourCategoryController] 투어 카테고리 생성 성공");
        return createTourCategoryResponse;
    }

}
