package com.koing.server.koing_server.service.admin;


import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.enums.TourStatus;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.DBFailException;
import com.koing.server.koing_server.common.exception.NotFoundException;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.domain.review.ReviewToGuide;
import com.koing.server.koing_server.domain.review.repository.ReviewToGuideRepositoryImpl;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.tour.repository.Tour.TourRepository;
import com.koing.server.koing_server.domain.tour.repository.Tour.TourRepositoryImpl;
import com.koing.server.koing_server.service.admin.dto.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminTourService {

    private final Logger LOGGER = LoggerFactory.getLogger(AdminTourService.class);
    private final TourRepositoryImpl tourRepositoryImpl;
    private final TourRepository tourRepository;
    private final ReviewToGuideRepositoryImpl reviewToGuideRepositoryImpl;

    @Transactional
    public SuperResponse adminGetNotFinishTours() {

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
    public SuperResponse adminGetFinishedTours() {

        LOGGER.info("[AdminTourService] 관리자 페이지의 종료된 투어 조회 시도");

        List<Tour> tours = tourRepositoryImpl.findFinishedTour();

        LOGGER.info("[AdminTourService] 관리자 페이지의 종료된 투어 조회 성공");

        List<AdminTourResponseDto> adminTourResponseDtos = new ArrayList<>();

        for (Tour tour : tours) {
            adminTourResponseDtos.add(new AdminTourResponseDto(tour));
        }

        return SuccessResponse.success(SuccessCode.ADMIN_GET_FINISHED_TOURS_SUCCESS, new AdminTourListResponseDto(adminTourResponseDtos));
    }

    @Transactional
    public SuperResponse adminGetTourDetail(Long tourId) {

        LOGGER.info("[AdminTourService] 관리자 페이지의 투어 세부내용 조회 시도");

        Tour tour = tourRepositoryImpl.findTourByTourId(tourId);

        LOGGER.info("[AdminTourService] 관리자 페이지의 투어 세부내용 조회 성공");

        LOGGER.info("[AdminTourService] 관리자 페이지의 투어 reviewToGuide 조회 시도");

        List<ReviewToGuide> reviewToGuides = reviewToGuideRepositoryImpl.findReviewToGuideByTourId(tourId);

        LOGGER.info("[AdminTourService] 관리자 페이지의 투어 reviewToGuide 조회 성공");

        AdminTourDetailResponseDto adminTourDetailResponseDto = new AdminTourDetailResponseDto(tour, reviewToGuides);

        return SuccessResponse.success(SuccessCode.ADMIN_GET_TOUR_DETAIL_SUCCESS, new AdminTourDetailResponseWrapperDto(adminTourDetailResponseDto));
    }

    @Transactional
    public SuperResponse adminGetCreatedTour() {

        LOGGER.info("[AdminTourService] 관리자 페이지의 미승인 투어 조회 시도");

        List<Tour> createdTours = tourRepositoryImpl.findCreatedTour();

        LOGGER.info("[AdminTourService] 관리자 페이지의 미승인 투어 조회 성공");


        List<AdminCreatedTourResponseDto> adminCreatedTourResponseDtos = new ArrayList<>();

        for (Tour createdTour : createdTours) {
            adminCreatedTourResponseDtos.add(new AdminCreatedTourResponseDto(createdTour));
        }

        return SuccessResponse.success(SuccessCode.ADMIN_GET_CREATED_TOUR_SUCCESS, new AdminCreatedTourListResponseDto(adminCreatedTourResponseDtos));
    }

    @Transactional
    public SuperResponse adminGetCreatedTourDetail(Long tourId) {

        LOGGER.info("[AdminTourService] 관리자 페이지의 미승인 투어 세부내용 조회 시도");

        Tour createdTour = tourRepositoryImpl.findTourByTourId(tourId);

        LOGGER.info("[AdminTourService] 관리자 페이지의 미승인 투어 세부내용 조회 성공");

        AdminCreatedTourDetailResponseDto adminCreatedTourDetailResponseDtos = new AdminCreatedTourDetailResponseDto(createdTour);

        return SuccessResponse.success(SuccessCode.ADMIN_GET_CREATED_TOUR_DETAIL_SUCCESS, new AdminCreatedTourDetailResponseWrapperDto(adminCreatedTourDetailResponseDtos));
    }

    @Transactional
    public SuperResponse adminApprovalTours(AdminChangeToursRequestDto adminChangeToursRequestDto) {
        LOGGER.info("[AdminTourService] Tour 승인 시도");

        for (Long tourId : adminChangeToursRequestDto.getTourIds()) {
            Tour tour = getTourAtDB(tourId);

            LOGGER.info("[AdminTourService] 승인 할 Tour 조회 성공");

            tour.setTourStatus(TourStatus.APPROVAL);

            Tour approvalTour = tourRepository.save(tour);

            if (!approvalTour.getTourStatus().equals(TourStatus.APPROVAL)) {
                throw new DBFailException("투어 승인 과정에서 오류가 발생했습니다.", ErrorCode.DB_FAIL_APPROVAL_TOUR_EXCEPTION);
            }
        }

        LOGGER.info("[AdminTourService] Tour 승인 성공");

        return SuccessResponse.success(SuccessCode.TOUR_APPROVAL_SUCCESS, null);
    }

    @Transactional
    public SuperResponse adminRejectionTours(AdminChangeToursRequestDto adminChangeToursRequestDto) {
        LOGGER.info("[AdminTourService] Tour 승인 거절 시도");

        for (Long tourId : adminChangeToursRequestDto.getTourIds()) {
            Tour tour = getTourAtDB(tourId);

            LOGGER.info("[AdminTourService] 승인 거절 할 Tour 조회 성공");

            tour.setTourStatus(TourStatus.REJECTION);

            Tour rejectionTour = tourRepository.save(tour);

            if (!rejectionTour.getTourStatus().equals(TourStatus.REJECTION)) {
                throw new DBFailException("투어 승인 거절 과정에서 오류가 발생했습니다.", ErrorCode.DB_FAIL_REJECTION_TOUR_EXCEPTION);
            }
        }

        LOGGER.info("[AdminTourService] Tour 승인 거절 성공");

        return SuccessResponse.success(SuccessCode.ADMIN_TOUR_REJECTION_SUCCESS, null);
    }

    private Tour getTourAtDB(Long temporaryTourId) {
        Tour temporaryTour = tourRepositoryImpl.findTourByTourId(temporaryTourId);
        if (temporaryTour == null) {
            throw new NotFoundException("해당 투어를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_TOUR_EXCEPTION);
        }

        return temporaryTour;
    }

}
