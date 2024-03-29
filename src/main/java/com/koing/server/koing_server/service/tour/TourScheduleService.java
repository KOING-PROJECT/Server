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
import com.koing.server.koing_server.domain.tour.TourDetailSchedule;
import com.koing.server.koing_server.domain.tour.TourSchedule;
import com.koing.server.koing_server.domain.tour.repository.Tour.TourRepository;
import com.koing.server.koing_server.domain.tour.repository.Tour.TourRepositoryImpl;
import com.koing.server.koing_server.domain.tour.repository.TourDetailSchedule.TourDetailScheduleRepository;
import com.koing.server.koing_server.domain.tour.repository.TourSchedule.TourScheduleRepository;
import com.koing.server.koing_server.domain.tour.repository.TourSchedule.TourScheduleRepositoryImpl;
import com.koing.server.koing_server.service.tour.dto.TourScheduleCreateDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
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
        LOGGER.info("[TourScheduleService] TourSchedule 생성 시도");

        TourSchedule tourSchedule = new TourSchedule(tourScheduleCreateDto, createStatus);

        HashMap<String, HashMap<String, String>> tourDetailSchedulesHashMaps = tourScheduleCreateDto.getTourDetailScheduleHashMap();

        for (Map.Entry<String, HashMap<String, String>> tds : tourDetailSchedulesHashMaps.entrySet()) {
            LOGGER.info("[TourScheduleService] TourDetailSchedule 생성 시도");
            TourDetailSchedule tourDetailSchedule = new TourDetailSchedule(tds);

            tourDetailSchedule.setNewTourSchedule(tourSchedule);

            TourDetailSchedule savedTourDetailSchedule = tourDetailScheduleRepository.save(tourDetailSchedule);

            if (savedTourDetailSchedule == null) {
                throw new DBFailException("투어 세부 스케줄 생성 과정에서 오류가 발생했습니다. 다시 시도해 주세요.", ErrorCode.DB_FAIL_CREATE_TOUR_DETAIL_SCHEDULE_FAIL_EXCEPTION);
            }

            LOGGER.info("[TourScheduleService] TourDetailSchedule 생성 성공");
        }

        TourSchedule savedTourSchedule = tourScheduleRepository.save(tourSchedule);

        if (savedTourSchedule == null) {
            throw new DBFailException("투어 스케줄 생성 과정에서 오류가 발생했습니다. 다시 시도해 주세요.", ErrorCode.DB_FAIL_CREATE_TOUR_SCHEDULE_FAIL_EXCEPTION);
        }

        LOGGER.info("[TourScheduleService] TourSchedule 생성 성공");

        if (createStatus.equals(CreateStatus.COMPLETE) && savedTourSchedule.getTourDetailScheduleList() == null) {
            throw new DBFailException("투어 스케줄 생성 과정에서 오류가 발생했습니다. 다시 시도해 주세요.", ErrorCode.DB_FAIL_CREATE_TOUR_SCHEDULE_FAIL_EXCEPTION);
        }

        LOGGER.info("[TourScheduleService] TourSchedule에 tourDetailSchedule update 성공");

        Tour tour = tourRepositoryImpl.findTourByTourId(tourScheduleCreateDto.getTourId());
        if (tour == null) {
            throw new NotFoundException("해당 투어를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_TOUR_EXCEPTION);
        }
        tour.setTourSchedule(savedTourSchedule);

        LOGGER.info("[TourScheduleService] Tour에 TourSchedule update 시도");

        Tour savedTour = tourRepository.save(tour);

        if (savedTour.getTourSchedule() == null) {
            throw new DBFailException("투어 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요.", ErrorCode.DB_FAIL_UPDATE_TOUR_FAIL_EXCEPTION);
        }

        LOGGER.info("[TourScheduleService] Tour에 TourSchedule update 성공 = " + savedTour);

        return SuccessResponse.success(SuccessCode.TOUR_SCHEDULE_CREATE_SUCCESS, savedTourSchedule);
    }

    public SuperResponse completeTourSchedule(Long tourId) {
        LOGGER.info("[TourScheduleService] TourSchedule 완성 시도");

        if (!tourRepositoryImpl.checkExistByTourId(tourId)) {
            throw new NotFoundException("해당 투어를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_TOUR_EXCEPTION);
        }

        TourSchedule tourSchedule = tourScheduleRepositoryImpl.findTourScheduleByTourId(tourId);

        if (tourSchedule == null) {
            throw new NotFoundException("해당 투어 스케줄을 찾을 수 없습니다.", ErrorCode.NOT_FOUND_TOUR_SCHEDULE_EXCEPTION);
        }

        tourSchedule.setCreateStatus(CreateStatus.COMPLETE);

        TourSchedule savedTourSchedule = tourScheduleRepository.save(tourSchedule);

        if (savedTourSchedule.getCreateStatus() != CreateStatus.COMPLETE) {
            throw new DBFailException("투어 스케줄 완성과정에서 오류가 발생했습니다.", ErrorCode.DB_FAIL_COMPLETE_TOUR_SCHEDULE_FAIL_EXCEPTION);
        }

        return SuccessResponse.success(SuccessCode.TOUR_SCHEDULE_COMPLETE_SUCCESS, savedTourSchedule);
    }

    @Transactional
    public SuperResponse updateTourSchedule(Long tourId, TourScheduleCreateDto tourScheduleCreateDto, CreateStatus createStatus) {
        LOGGER.info("[TourScheduleService] TourSchedule update 시도");

        TourSchedule tourSchedule = tourScheduleRepositoryImpl.findTourScheduleByTourId(tourId);

        if (tourSchedule == null) {
            throw new NotFoundException("해당 투어 스케줄을 찾을 수 없습니다.", ErrorCode.NOT_FOUND_TOUR_SCHEDULE_EXCEPTION);
        }

        LOGGER.info("[TourScheduleService] TourSchedule 임시 저장 된 TourDetailSchedule 삭제 시도");
        if (tourSchedule.getTourDetailScheduleList().size() > 0) {
            tourDetailScheduleRepository.deleteAll(tourSchedule.getTourDetailScheduleList());
            tourSchedule.deleteTourDetailSchedule();
        }
        LOGGER.info("[TourScheduleService] TourSchedule 임시 저장 된 TourDetailSchedule 삭제 성공");

        HashMap<String, HashMap<String, String>> tourDetailSchedulesHashMaps = tourScheduleCreateDto.getTourDetailScheduleHashMap();

        for (Map.Entry<String, HashMap<String, String>> tds : tourDetailSchedulesHashMaps.entrySet()) {
            LOGGER.info("[TourScheduleService] TourDetailSchedule 생성 시도");
            TourDetailSchedule tourDetailSchedule = new TourDetailSchedule(tds);

            tourDetailSchedule.setNewTourSchedule(tourSchedule);

            TourDetailSchedule savedTourDetailSchedule = tourDetailScheduleRepository.save(tourDetailSchedule);

            if (savedTourDetailSchedule == null) {
                throw new DBFailException("투어 세부 스케줄 생성 과정에서 오류가 발생했습니다. 다시 시도해 주세요.", ErrorCode.DB_FAIL_CREATE_TOUR_DETAIL_SCHEDULE_FAIL_EXCEPTION);
            }

            LOGGER.info("[TourScheduleService] TourDetailSchedule 생성 성공");
        }

        if (CreateStatus.COMPLETE.equals(createStatus)) {
            tourSchedule.setCreateStatus(createStatus);
        }
        tourSchedule.setTourDates(tourScheduleCreateDto.getTourDates());
        tourSchedule.setDateNegotiation(tourScheduleCreateDto.isDateNegotiation());

        TourSchedule savedTourSchedule = tourScheduleRepository.save(tourSchedule);

        if (savedTourSchedule == null) {
            throw new DBFailException("투어 스케줄 생성 과정에서 오류가 발생했습니다. 다시 시도해 주세요.", ErrorCode.DB_FAIL_CREATE_TOUR_SCHEDULE_FAIL_EXCEPTION);
        }

        LOGGER.info("[TourScheduleService] TourSchedule update 성공");

        Tour tour = tourRepositoryImpl.findTourByTourId(tourId);
        tour.setTemporarySavePage(2);
        tour.setTourSchedule(savedTourSchedule);

        LOGGER.info("[TourScheduleService] Tour에 TourSchedule update 시도");

        Tour savedTour = tourRepository.save(tour);

        if (savedTour.getTourSchedule() == null) {
            throw new DBFailException("투어 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요.", ErrorCode.DB_FAIL_UPDATE_TOUR_FAIL_EXCEPTION);
        }

        LOGGER.info("[TourScheduleService] Tour에 TourSchedule update 성공 = " + savedTour);

        return SuccessResponse.success(SuccessCode.TOUR_SCHEDULE_UPDATE_SUCCESS, savedTourSchedule);
    }

}
