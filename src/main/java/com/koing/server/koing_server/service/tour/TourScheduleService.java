package com.koing.server.koing_server.service.tour;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.tour.TourDetailSchedule;
import com.koing.server.koing_server.domain.tour.TourSchedule;
import com.koing.server.koing_server.domain.tour.repository.TourDetailScheduleRepository;
import com.koing.server.koing_server.domain.tour.repository.TourRepository;
import com.koing.server.koing_server.domain.tour.repository.TourRepositoryImpl;
import com.koing.server.koing_server.domain.tour.repository.TourScheduleRepository;
import com.koing.server.koing_server.service.tour.dto.TourScheduleDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Transactional
    public SuperResponse createTourSchedule(TourScheduleDto tourScheduleDto) {

        LOGGER.info("[TourCategoryService] TourSchedule 생성 시도");

        TourSchedule tourSchedule = new TourSchedule(tourScheduleDto);

        HashMap<String, HashMap<String, String>> tourDetailSchedulesHashMaps = tourScheduleDto.getTourDetailScheduleHashMap();

        for (Map.Entry<String, HashMap<String, String>> tds : tourDetailSchedulesHashMaps.entrySet()) {
            LOGGER.info("[TourCategoryService] TourDetailSchedule 생성 시도");
            TourDetailSchedule tourDetailSchedule = new TourDetailSchedule(tds);

            tourDetailSchedule.setTourSchedule(tourSchedule);

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

        if (savedTourSchedule.getTourDetailScheduleList() == null) {
            return ErrorResponse.error(ErrorCode.DB_FAIL_UPDATE_TOUR_SCHEDULE_FAIL_EXCEPTION);
        }

        LOGGER.info("[TourCategoryService] TourSchedule에 tourDetailSchedule update 성공");

        Tour tour = tourRepositoryImpl.findTourByTourId(tourScheduleDto.getTourId());
        tour.setTourSchedule(savedTourSchedule);

        LOGGER.info("[TourCategoryService] Tour에 TourSchedule update 시도");

        Tour savedTour = tourRepository.save(tour);

        if (savedTour.getTourSchedule() == null) {
            return ErrorResponse.error(ErrorCode.DB_FAIL_UPDATE_TOUR_FAIL_EXCEPTION);
        }

        LOGGER.info("[TourCategoryService] Tour에 TourSchedule update 성공 = " + savedTour);

        return SuccessResponse.success(SuccessCode.TOUR_SCHEDULE_CREATE_SUCCESS, savedTourSchedule);
    }

}
