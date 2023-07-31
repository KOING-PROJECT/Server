package com.koing.server.koing_server.controller.tour;

import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.service.tour.TourCategoryService;
import com.koing.server.koing_server.service.tour.dto.TourCategoryCreateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@Tag(name = "TourCategory", description = "TourCategory API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/tour-category")
public class TourCategoryController {

    private final Logger LOGGER = LoggerFactory.getLogger(TourCategoryController.class);
    private final TourCategoryService tourCategoryService;


    @Operation(description = "TourCategories - 투어 카테고리 리스트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "TourCategories - 투어 카테고리 리스트 조회 성공"),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @GetMapping("")
    public SuperResponse getTourCategories() {
        LOGGER.info("[TourCategoryController] 투어 카테고리 리스트 조회 시도");
        SuperResponse getTourCategoriesResponse = tourCategoryService.getTourCategories();
        LOGGER.info("[TourCategoryController] 투어 카테고리 리스트 조회 성공");
        return getTourCategoriesResponse;
    }


    @Operation(description = "TourCategory - 투어 카테고리를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "TourCategory - 투어 카테고리 생성 성공"),
            @ApiResponse(responseCode = "402", description = "투어 카테고리 생성과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping("")
    public SuperResponse createTourCategory(@RequestBody TourCategoryCreateDto tourCategoryCreateDto) {
        LOGGER.info("[TourCategoryController] 투어 카테고리 생성 시도");
        SuperResponse createTourCategoryResponse = tourCategoryService.createTourCategory(tourCategoryCreateDto);
        LOGGER.info("[TourCategoryController] 투어 카테고리 생성 성공");
        return createTourCategoryResponse;
    }

}
