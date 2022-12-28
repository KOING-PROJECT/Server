package com.koing.server.koing_server.service.tour;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.enums.CreateStatus;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.tour.TourDetailSchedule;
import com.koing.server.koing_server.domain.tour.TourSchedule;
import com.koing.server.koing_server.domain.tour.repository.*;
import com.koing.server.koing_server.service.tour.dto.TourScheduleCreateDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TourScheduleService {

    private final Logger LOGGER = LoggerFactory.getLogger(TourScheduleService.class);
    private final TourDetailScheduleRepository tourDetailScheduleRepository;
    private final TourScheduleRepository tourScheduleRepository;
    private final TourRepositoryImpl tourRepositoryImpl;
    private final TourRepository tourRepository;
    private final TourScheduleRepositoryImpl tourScheduleRepositoryImpl;

    @Transactional
    public SuperResponse createTourSchedule(
            TourScheduleCreateDto tourScheduleCreateDto,
            CreateStatus createStatus
    ) {
        LOGGER.info("[TourCategoryService] TourSchedule 생성 시도");

        TourSchedule tourSchedule = new TourSchedule(tourScheduleCreateDto, createStatus);

        HashMap<String, HashMap<String, String>> tourDetailSchedulesHashMaps = tourScheduleCreateDto.getTourDetailScheduleHashMap();

        for (Map.Entry<String, HashMap<String, String>> tds : tourDetailSchedulesHashMaps.entrySet()) {
            LOGGER.info("[TourCategoryService] TourDetailSchedule 생성 시도");
            TourDetailSchedule tourDetailSchedule = new TourDetailSchedule(tds);

            tourDetailSchedule.setNewTourSchedule(tourSchedule);

            TourDetailSchedule savedTourDetailSchedule = tourDetailScheduleRepository.save(tourDetailSchedule);

            if (savedTourDetailSchedule == null) {
                return ErrorResponse.error(ErrorCode.DB_FAIL_CREATE_TOUR_DETAIL_SCHEDULE_FAIL_EXCEPTION);
            }

            LOGGER.info("[TourCategoryService] TourDetailSchedule 생성 성공");
        }

        TourSchedule savedTourSchedule = tourScheduleRepository.save(tourSchedule);

        if (savedTourSchedule == null) {
            return ErrorResponse.error(ErrorCode.DB_FAIL_CREATE_TOUR_SCHEDULE_FAIL_EXCEPTION);
        }

        LOGGER.info("[TourCategoryService] TourSchedule 생성 성공");

        if (createStatus.equals(CreateStatus.COMPLETE) && savedTourSchedule.getTourDetailScheduleList() == null) {
            return ErrorResponse.error(ErrorCode.DB_FAIL_CREATE_TOUR_SCHEDULE_FAIL_EXCEPTION);
        }

        LOGGER.info("[TourCategoryService] TourSchedule에 tourDetailSchedule update 성공");

        Tour tour = tourRepositoryImpl.findTourByTourId(tourScheduleCreateDto.getTourId());
        if (tour == null) {
            return ErrorResponse.error(ErrorCode.NOT_FOUND_TOUR_EXCEPTION);
        }
        tour.setTourSchedule(savedTourSchedule);

        LOGGER.info("[TourCategoryService] Tour에 TourSchedule update 시도");

        Tour savedTour = tourRepository.save(tour);

        if (savedTour.getTourSchedule() == null) {
            return ErrorResponse.error(ErrorCode.DB_FAIL_UPDATE_TOUR_FAIL_EXCEPTION);
        }

        LOGGER.info("[TourCategoryService] Tour에 TourSchedule update 성공 = " + savedTour);

        return SuccessResponse.success(SuccessCode.TOUR_SCHEDULE_CREATE_SUCCESS, savedTourSchedule);
    }

    @Transactional
    public SuperResponse updateTourSchedule(Long tourId, TourScheduleCreateDto tourScheduleCreateDto, CreateStatus createStatus) {
        LOGGER.info("[TourCategoryService] TourSchedule update 시도");

        TourSchedule tourSchedule = tourScheduleRepositoryImpl.findTourScheduleByTourId(tourId);

        if (tourSchedule == null) {
            return ErrorResponse.error(ErrorCode.NOT_FOUND_TOUR_SCHEDULE_EXCEPTION);
        }

        LOGGER.info("[TourCategoryService] TourSchedule 임시 저장 된 TourDetailSchedule 삭제 시도");
        if (tourSchedule.getTourDetailScheduleList().size() > 0) {
            tourDetailScheduleRepository.deleteAll(tourSchedule.getTourDetailScheduleList());
            tourSchedule.deleteTourDetailSchedule();
        }
        LOGGER.info("[TourCategoryService] TourSchedule 임시 저장 된 TourDetailSchedule 삭제 성공");

        HashMap<String, HashMap<String, String>> tourDetailSchedulesHashMaps = tourScheduleCreateDto.getTourDetailScheduleHashMap();

        for (Map.Entry<String, HashMap<String, String>> tds : tourDetailSchedulesHashMaps.entrySet()) {
            LOGGER.info("[TourCategoryService] TourDetailSchedule 생성 시도");
            TourDetailSchedule tourDetailSchedule = new TourDetailSchedule(tds);

            tourDetailSchedule.setNewTourSchedule(tourSchedule);

            TourDetailSchedule savedTourDetailSchedule = tourDetailScheduleRepository.save(tourDetailSchedule);

            if (savedTourDetailSchedule == null) {
                return ErrorResponse.error(ErrorCode.DB_FAIL_CREATE_TOUR_DETAIL_SCHEDULE_FAIL_EXCEPTION);
            }

            LOGGER.info("[TourCategoryService] TourDetailSchedule 생성 성공");
        }

        if (tourSchedule.getTourDetailScheduleList() != null) {
            System.out.println("getTourDetailScheduleList = " + tourSchedule.getTourDetailScheduleList().size());
        }

        if (CreateStatus.COMPLETE.equals(createStatus)) {
            tourSchedule.setCreateStatus(createStatus);
        }
        tourSchedule.setTourDates(tourScheduleCreateDto.getTourDates());
        tourSchedule.setDateNegotiation(tourScheduleCreateDto.isDateNegotiation());

        TourSchedule savedTourSchedule = tourScheduleRepository.save(tourSchedule);

        if (savedTourSchedule == null) {
            return ErrorResponse.error(ErrorCode.DB_FAIL_CREATE_TOUR_SCHEDULE_FAIL_EXCEPTION);
        }

        LOGGER.info("[TourCategoryService] TourSchedule update 성공");

        Tour tour = tourRepositoryImpl.findTourByTourId(tourId);
        tour.setTourSchedule(savedTourSchedule);

        LOGGER.info("[TourCategoryService] Tour에 TourSchedule update 시도");

        Tour savedTour = tourRepository.save(tour);

        if (savedTour.getTourSchedule() == null) {
            return ErrorResponse.error(ErrorCode.DB_FAIL_UPDATE_TOUR_FAIL_EXCEPTION);
        }

        LOGGER.info("[TourCategoryService] Tour에 TourSchedule update 성공 = " + savedTour);

        return SuccessResponse.success(SuccessCode.TOUR_SCHEDULE_UPDATE_SUCCESS, savedTourSchedule);
    }

}
