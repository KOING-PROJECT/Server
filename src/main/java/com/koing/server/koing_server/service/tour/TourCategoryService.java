package com.koing.server.koing_server.service.tour;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.domain.tour.TourCategory;
import com.koing.server.koing_server.domain.tour.repository.TourCategory.TourCategoryRepositoryImpl;
import com.koing.server.koing_server.domain.tour.repository.TourCategory.TourCategoryRepository;
import com.koing.server.koing_server.service.tour.dto.TourCategoryCreateDto;
import com.koing.server.koing_server.service.tour.dto.TourCategoryDto;
import com.koing.server.koing_server.service.tour.dto.TourCategoryListResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TourCategoryService {

    private final Logger LOGGER = LoggerFactory.getLogger(TourCategoryService.class);
    private final TourCategoryRepository tourCategoryRepository;
    private final TourCategoryRepositoryImpl tourCategoryRepositoryImpl;

    public SuperResponse getTourCategories() {

        LOGGER.info("[TourCategoryService] TourCategory 리스트 조회 시도");

        List<TourCategory> tourCategories = tourCategoryRepositoryImpl.findAll();

        LOGGER.info("[TourCategoryService] TourCategory 리스트 조회 성공");

        List<TourCategoryDto> tourCategoryDtos = new ArrayList<>();

        for (TourCategory tourCategory : tourCategories) {
            tourCategoryDtos.add(new TourCategoryDto(tourCategory.getCategoryName()));
        }

        return SuccessResponse.success(SuccessCode.GET_TOUR_CATEGORIES_SUCCESS, new TourCategoryListResponseDto(tourCategoryDtos));
    }

    public SuperResponse createTourCategory(TourCategoryCreateDto tourCategoryCreateDto) {

        LOGGER.info("[TourCategoryService] TourCategory 생성 시도");

        TourCategory tourCategory = new TourCategory(tourCategoryCreateDto);
        TourCategory savedTourCategory = tourCategoryRepository.save(tourCategory);

        if (savedTourCategory.getCategoryName() == null) {
            return ErrorResponse.error(ErrorCode.DB_FAIL_CREATE_TOUR_CATEGORY_FAIL_EXCEPTION);
        }
        LOGGER.info("[TourCategoryService] TourCategory 생성 성공");

        return SuccessResponse.success(SuccessCode.TOUR_CATEGORY_CREATE_SUCCESS, savedTourCategory);
    }

}
