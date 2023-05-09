package com.koing.server.koing_server.service.admin;


import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.enums.TourCategoryIndex;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.tour.TourCategory;
import com.koing.server.koing_server.domain.tour.repository.Tour.TourRepositoryImpl;
import com.koing.server.koing_server.service.admin.dto.AdminTourListResponseDto;
import com.koing.server.koing_server.service.admin.dto.AdminTourResponseDto;
import com.koing.server.koing_server.service.survey.SurveyService;
import com.koing.server.koing_server.service.tour.dto.TourDto;
import com.koing.server.koing_server.service.tour.dto.TourListResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminTourService {

    private final Logger LOGGER = LoggerFactory.getLogger(AdminTourService.class);
    private final TourRepositoryImpl tourRepositoryImpl;

    @Transactional
    public SuperResponse getNotFinishTours() {

        LOGGER.info("[AdminTourService] 관리자 페이지의 진행중인 투어 조회 시도");

        List<Tour> tours = tourRepositoryImpl.findNotFinishTour();

        LOGGER.info("[AdminTourService] 관리자 페이지의 진행중인 투어 조회 성공");

        List<AdminTourResponseDto> adminTourResponseDtos = new ArrayList<>();

        for (Tour tour : tours) {
            adminTourResponseDtos.add(new AdminTourResponseDto(tour));
        }

        return SuccessResponse.success(SuccessCode.ADMIN_GET_NOT_FINISH_TOURS_SUCCESS, new AdminTourListResponseDto(adminTourResponseDtos));
    }

    @Transactional
    public SuperResponse getFinishedTours() {

        LOGGER.info("[AdminTourService] 관리자 페이지의 종료된 투어 조회 시도");

        List<Tour> tours = tourRepositoryImpl.findFinishedTour();

        LOGGER.info("[AdminTourService] 관리자 페이지의 종료된 투어 조회 성공");

        List<AdminTourResponseDto> adminTourResponseDtos = new ArrayList<>();

        for (Tour tour : tours) {
            adminTourResponseDtos.add(new AdminTourResponseDto(tour));
        }

        return SuccessResponse.success(SuccessCode.ADMIN_GET_FINISHED_TOURS_SUCCESS, new AdminTourListResponseDto(adminTourResponseDtos));
    }

}
