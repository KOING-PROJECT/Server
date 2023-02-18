package com.koing.server.koing_server.service.tour;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.enums.CreateStatus;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.DBFailException;
import com.koing.server.koing_server.common.exception.NotFoundException;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.tour.TourSurvey;
import com.koing.server.koing_server.domain.tour.repository.Tour.TourRepository;
import com.koing.server.koing_server.domain.tour.repository.Tour.TourRepositoryImpl;
import com.koing.server.koing_server.domain.tour.repository.TourSurvey.TourSurveyRepository;
import com.koing.server.koing_server.domain.tour.repository.TourSurvey.TourSurveyRepositoryImpl;
import com.koing.server.koing_server.service.tour.dto.TourCreateDto;
import com.koing.server.koing_server.service.tour.dto.TourDto;
import com.koing.server.koing_server.service.tour.dto.TourSurveyCreateDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TourSurveyService {

    private final Logger LOGGER = LoggerFactory.getLogger(TourSurveyService.class);
    private final TourRepositoryImpl tourRepositoryImpl;
    private final TourSurveyRepository tourSurveyRepository;
    private final TourSurveyRepositoryImpl tourSurveyRepositoryImpl;
    private final TourRepository tourRepository;

    @Transactional
    public SuperResponse createTourSurvey(TourSurveyCreateDto tourSurveyCreateDto, CreateStatus createStatus) {
        // tour create 완료 시 호출
        LOGGER.info("[TourSurveyService] TourSurvey 생성 시도");

        Tour tour = tourRepositoryImpl.findTourByTourId(tourSurveyCreateDto.getTourId());

        if(tour == null) {
            throw new NotFoundException("해당 투어를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_TOUR_EXCEPTION);
        }
        LOGGER.info("[TourSurveyService] tourId로 tour 조회 성공");

        TourSurvey tourSurvey = new TourSurvey(tourSurveyCreateDto, createStatus);
        TourSurvey savedTourSurvey = tourSurveyRepository.save(tourSurvey);

        if (savedTourSurvey == null) {
            throw new DBFailException("투어 설문 생성에 실패했습니다.", ErrorCode.DB_FAIL_CREATE_TOUR_SURVEY_FAIL_EXCEPTION);
        }
        LOGGER.info("[TourSurveyService] TourSurvey 생성 성공");

        tour.setTourSurvey(savedTourSurvey);
        Tour updatedTour = tourRepository.save(tour);

        if (updatedTour.getTourSurvey() == null) {
            throw new DBFailException("투어에 투어 설문 업데이트를 실패했습니다.", ErrorCode.DB_FAIL_UPDATE_TOUR_FAIL_EXCEPTION);
        }
        LOGGER.info("[TourSurveyService] Tour에 TourSurvey 업데이트 성공");

        return SuccessResponse.success(SuccessCode.TOUR_SURVEY_CREATE_SUCCESS, savedTourSurvey);
    }

//    public SuperResponse completeTourSurvey(Long tourId, TourSurveyCreateDto tourSurveyCreateDto) {
//        LOGGER.info("[TourSurveyService] TourSurvey 완성 시도");
//
//        TourSurvey tourSurvey = tourSurveyRepositoryImpl.findTourSurveyByTourId(tourId);
//
//        if(tourSurvey == null) {
//            throw new NotFoundException("해당 투어 설문을 찾을 수 없습니다.", ErrorCode.NOT_FOUND_TOUR_SURVEY_EXCEPTION);
//        }
//        LOGGER.info("[TourSurveyService] tourId로 TourSurvey 조회 성공");
//
//        tourSurvey = updateTourSurvey(tourSurvey, tourSurveyCreateDto, CreateStatus.COMPLETE);
//
//        TourSurvey savedTourSurvey = tourSurveyRepository.save(tourSurvey);
//
//        if (savedTourSurvey.getCreateStatus() != CreateStatus.COMPLETE) {
//            throw new DBFailException("투어 설문 완성과정에서 오류가 발생했습니다.", ErrorCode.DB_FAIL_COMPLETE_TOUR_SURVEY_FAIL_EXCEPTION);
//        }
//        LOGGER.info("[TourSurveyService] TourSurvey 완성 성공");
//
//        return SuccessResponse.success(SuccessCode.TOUR_SURVEY_COMPLETE_SUCCESS, savedTourSurvey);
//    }

    @Transactional
    public SuperResponse updateTourSurvey(Long tourId, TourSurveyCreateDto tourSurveyCreateDto, CreateStatus createStatus) {
        // tour create 완료 시 호출
        LOGGER.info("[TourSurveyService] TourSurvey 업데이트 시도");

        if (!tourRepositoryImpl.checkExistByTourId(tourId)) {
            throw new NotFoundException("해당 투어를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_TOUR_EXCEPTION);
        }

//        TourSurvey tourSurvey = tour.getTourSurvey();

        TourSurvey tourSurvey = tourSurveyRepositoryImpl.findTourSurveyByTourId(tourId);

        if(tourSurvey == null) {
            throw new NotFoundException("해당 투어 설문을 찾을 수 없습니다.", ErrorCode.NOT_FOUND_TOUR_SURVEY_EXCEPTION);
        }
        LOGGER.info("[TourSurveyService] tour의 tourSurvey 조회 성공");

        tourSurvey = updateTourSurvey(tourSurvey, tourSurveyCreateDto, createStatus);

        TourSurvey updatedTourSurvey = tourSurveyRepository.save(tourSurvey);

        if (!updatedTourSurvey.equals(tourSurvey)) {
            throw new DBFailException("투어 설문 업데이트에 실패했습니다.", ErrorCode.DB_FAIL_UPDATE_TOUR_SURVEY_FAIL_EXCEPTION);
        }
        LOGGER.info("[TourSurveyService] TourSurvey 업데이트 성공");

        Tour tour = tourRepositoryImpl.findTourByTourId(tourId);

        if(tour == null) {
            throw new NotFoundException("해당 투어를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_TOUR_EXCEPTION);
        }
        LOGGER.info("[TourSurveyService] tourId로 tour 조회 성공");

        tour.setTemporarySavePage(3);
        tour.setTourSurvey(updatedTourSurvey);
        Tour updatedTour = tourRepository.save(tour);

        return SuccessResponse.success(SuccessCode.TOUR_SURVEY_UPDATE_SUCCESS, updatedTourSurvey);
    }

    private TourSurvey updateTourSurvey(TourSurvey tourSurvey, TourSurveyCreateDto tourSurveyCreateDto, CreateStatus createStatus) {
        tourSurvey.setStyle(tourSurveyCreateDto.getStyle());
        tourSurvey.setType(tourSurveyCreateDto.getType());
        tourSurvey.setMovingSupport(tourSurveyCreateDto.isMovingSupport());
        if (CreateStatus.COMPLETE.equals(createStatus)) {
            tourSurvey.setCreateStatus(createStatus);
        }
        return tourSurvey;
    }
}
