package com.koing.server.koing_server.service.tour;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.DBFailException;
import com.koing.server.koing_server.common.exception.NotAcceptableException;
import com.koing.server.koing_server.common.exception.NotFoundException;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.tour.TourApplication;
import com.koing.server.koing_server.domain.tour.TourParticipant;
import com.koing.server.koing_server.domain.tour.TourSchedule;
import com.koing.server.koing_server.domain.tour.repository.Tour.TourRepository;
import com.koing.server.koing_server.domain.tour.repository.Tour.TourRepositoryImpl;
import com.koing.server.koing_server.domain.tour.repository.TourApplication.TourApplicationRepository;
import com.koing.server.koing_server.domain.tour.repository.TourApplication.TourApplicationRepositoryImpl;
import com.koing.server.koing_server.domain.tour.repository.TourParticipant.TourParticipantRepository;
import com.koing.server.koing_server.domain.tour.repository.TourParticipant.TourParticipantRepositoryImpl;
import com.koing.server.koing_server.domain.tour.repository.TourSchedule.TourScheduleRepositoryImpl;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.domain.user.repository.UserRepository;
import com.koing.server.koing_server.domain.user.repository.UserRepositoryImpl;
import com.koing.server.koing_server.service.tour.dto.TourApplicationCreateDto;
import com.koing.server.koing_server.service.tour.dto.TourApplicationDto;
import com.koing.server.koing_server.service.tour.dto.TourApplicationParticipateDto;
import com.koing.server.koing_server.service.tour.dto.TourApplicationResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TourApplicationService {

    private final Logger LOGGER = LoggerFactory.getLogger(TourApplicationService.class);
    private final TourApplicationRepository tourApplicationRepository;
    private final TourApplicationRepositoryImpl tourApplicationRepositoryImpl;
    private final TourRepository tourRepository;
    private final TourRepositoryImpl tourRepositoryImpl;
    private final UserRepository userRepository;
    private final UserRepositoryImpl userRepositoryImpl;
    private final TourScheduleRepositoryImpl tourScheduleRepositoryImpl;
    private final TourParticipantRepository tourParticipantRepository;
    private final TourParticipantRepositoryImpl tourParticipantRepositoryImpl;

    @Transactional
    public SuperResponse createTourApplication(TourApplicationCreateDto tourApplicationCreateDto) {

        Tour tour = tourRepositoryImpl.findTourByTourId(tourApplicationCreateDto.getTourId());

        if (tour == null) {
            throw new NotFoundException("해당 투어를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_TOUR_EXCEPTION);
        }

        LOGGER.info("[TourApplicationService] TourId로 tour 찾기 성공 = " + tour);
        int tourMaxParticipant = tour.getParticipant();

        Set<String> tourDates = tour.getTourSchedule().getTourDates();

        for (String tourDate: tourDates) {
            TourApplication tourApplication = new TourApplication(tourDate, tourMaxParticipant);
            tourApplication.setTour(tour);

            TourApplication savedTourApplication = tourApplicationRepository.save(tourApplication);

            if (savedTourApplication.getId() == null) {
                throw new DBFailException("투어 신청서 생성 과정에서 오류가 발생했습니다. 다시 시도해 주세요.", ErrorCode.DB_FAIL_CREATE_TOUR_APPLICATION_FAIL_EXCEPTION);
            }

            LOGGER.info("[TourApplicationService] TourApplication 생성 성공 = " + savedTourApplication);
        }

//        tour.setTourApplication(savedTourApplication);
        Tour savedTour = tourRepository.save(tour);

        if (savedTour.getTourApplications().size() != tourDates.size()) {
            throw new DBFailException("투어 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요.", ErrorCode.DB_FAIL_UPDATE_TOUR_FAIL_EXCEPTION);
        }

        LOGGER.info("[TourApplicationService] 투어에 TourApplication 업데이트 성공 = " + savedTour);

        TourApplicationDto tourApplicationDto = new TourApplicationDto(savedTour);

        return SuccessResponse.success(SuccessCode.TOUR_APPLICATION_CREATE_SUCCESS, tourApplicationDto);
    }

    @Transactional
    public SuperResponse participateTour(TourApplicationParticipateDto tourApplicationParticipateDto) {
        Long tourId = tourApplicationParticipateDto.getTourId();
        String tourDate = tourApplicationParticipateDto.getTourDate();

        LOGGER.info("[TourApplicationService] TourId로 TourApplication 조회 시도");
        TourApplication tourApplication = getTourApplication(tourId, tourDate);

        LOGGER.info("[TourApplicationService] TourId로 TourApplication 조회 성공 = " + tourApplication);


        int currParticipants = tourApplication.getCurrentParticipants();
        int numberOfParticipants = tourApplicationParticipateDto.getNumberOfParticipants();

        if (currParticipants + numberOfParticipants > tourApplication.getMaxParticipant()) {
            throw new NotAcceptableException("해당 투어의 정원을 초과했습니다.", ErrorCode.NOT_ACCEPTABLE_OVER_MAX_PARTICIPANTS_EXCEPTION);
        }

        LOGGER.info("[TourApplicationService] userId로 tour 신청 유저 조회 시도");
        User user = getUser(tourApplicationParticipateDto.getUserId());

        LOGGER.info("[TourApplicationService] userId로 tour 신청 유저 조회 성공" + user);

        LOGGER.info("[TourApplicationService] TourParticipant 생성 시도");
        TourParticipant tourParticipant = new TourParticipant(
                tourApplication,
                user,
                tourApplicationParticipateDto.getNumberOfParticipants()
        );
        LOGGER.info("[TourApplicationService] TourParticipant 생성 성공");

        tourApplication.setCurrentParticipants(currParticipants + numberOfParticipants);

        TourApplication updateTourApplication = tourApplicationRepository.save(tourApplication);
        User updatedUser = userRepository.save(user);

        LOGGER.info("[TourApplicationService] TourParticipant 생성 저장 시도");
        TourParticipant savedTourParticipant = tourParticipantRepository.save(tourParticipant);

        if (savedTourParticipant == null) {
            throw new DBFailException(
                    "투어 신청서 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요.",
                    ErrorCode.DB_FAIL_UPDATE_TOUR_APPLICATION_FAIL_EXCEPTION
            );
        }
        LOGGER.info("[TourApplicationService] TourParticipant 생성 저장 성공");

        return SuccessResponse.success(SuccessCode.TOUR_APPLICATION_UPDATE_SUCCESS, null);
    }

    @Transactional
    public SuperResponse getTourApplications(Long userId) {
        LOGGER.info("[TourApplicationService] userId로 user 조회 시도");

        User user = getUser(userId);
        LOGGER.info("[TourApplicationService] userId로 user 조회 성공");

        LOGGER.info("[TourApplicationService] user로 tourParticiapnts 조회 시도");
        List<TourParticipant> tourParticipants = tourParticipantRepositoryImpl.findTourParticipantByUser(user);

        LOGGER.info("[TourApplicationService] user로 tourParticiapnts 조회 성공");
        List<TourApplication> tourApplications = new ArrayList<>();

        for (TourParticipant tourParticipant : tourParticipants) {
            tourApplications.add(tourParticipant.getTourApplication());
        }
        LOGGER.info("[TourApplicationService] tourParticiapnts에서 tourApplication 가져오기 성공 = " + tourApplications);

        List<TourApplicationDto> tourApplicationDtos = new ArrayList<>();

        for (TourApplication tourApplication : tourApplications) {
            tourApplicationDtos.add(new TourApplicationDto(tourApplication));
        }

        return SuccessResponse.success(SuccessCode.GET_TOUR_APPLICATIONS_SUCCESS, new TourApplicationResponseDto(tourApplicationDtos));
    }

    @Transactional
    public boolean checkDateExistParticipant(Long tourId, Set<String> newTourDates) {

        TourSchedule tourSchedule = tourScheduleRepositoryImpl.findTourScheduleByTourId(tourId);

        List<String> removedDates = new ArrayList<>();

        Set<String> beforeDates = tourSchedule.getTourDates();
        for (String beforeDate : beforeDates) {
            if (!newTourDates.contains(beforeDate)) {
                removedDates.add(beforeDate);
            }
        }

        for (String removedDate : removedDates) {
            TourApplication tourApplication = tourApplicationRepositoryImpl.findTourApplicationByTourIdAndTourDate(
                    tourId,
                    removedDate
            );

            if (tourApplication.getTourParticipants().size() > 0) {
                return true;
            }
        }

        return false;
    }

    @Transactional
    public SuperResponse updateTourApplication(Long tourId, Set<String> newTourDates, int newMaxParticitpant) {

        LOGGER.info("[TourApplicationService] TourApplication 업데이트 시도");
        Tour tour = tourRepositoryImpl.findTourByTourId(tourId);

        Set<String> originalTourDates = tour.getTourSchedule().getTourDates();

        List<String> removedDates = new ArrayList<>();
        List<String> createDates = new ArrayList<>();
        List<String> dates = new ArrayList<>();

        for (String originalTourDate : originalTourDates) {
            if (!newTourDates.contains(originalTourDate)) {
                removedDates.add(originalTourDate);
            }
        }

        for (String newDate : newTourDates) {
            if (!originalTourDates.contains(newDate)) {
                createDates.add(newDate);
            }
            else {
                dates.add(newDate);
            }
        }

        for (String removedDate : removedDates) {
            LOGGER.info("[TourApplicationService] 변경된 날짜 TourApplication 삭제 시도");
            TourApplication tourApplication =
                    tourApplicationRepositoryImpl.findTourApplicationByTourIdAndTourDate(
                            tourId,
                            removedDate
                    );

            tourApplication.deleteTour(tour);
            tourApplicationRepository.delete(tourApplication);
            LOGGER.info("[TourApplicationService] 변경된 날짜 TourApplication 삭제 성공 = " + removedDate);
        }

        for (String createDate: createDates) {
            LOGGER.info("[TourApplicationService] 새로운 TourApplication 생성 시도");
            TourApplication tourApplication = new TourApplication(createDate, newMaxParticitpant);
            tourApplication.setTour(tour);

            TourApplication savedTourApplication = tourApplicationRepository.save(tourApplication);

            if (savedTourApplication.getId() == null) {
                throw new DBFailException("투어 신청서 생성 과정에서 오류가 발생했습니다. 다시 시도해 주세요.", ErrorCode.DB_FAIL_CREATE_TOUR_APPLICATION_FAIL_EXCEPTION);
            }

            LOGGER.info("[TourApplicationService] 새로운 TourApplication 생성 성공 = " + savedTourApplication);
        }

        for (String date : dates) {
            TourApplication tourApplication =
                    tourApplicationRepositoryImpl.findTourApplicationByTourIdAndTourDate(
                            tourId,
                            date
                    );
            tourApplication.setMaxParticipant(newMaxParticitpant);
            tourApplicationRepository.save(tourApplication);
        }

        LOGGER.info("tour = " + tour.getTourApplications());
        Tour savedTour = tourRepository.save(tour);

        LOGGER.info("[TourApplicationService] TourApplication 업데이트 성공 = " + savedTour);
        TourApplicationDto tourApplicationDto = new TourApplicationDto(savedTour);

        return SuccessResponse.success(SuccessCode.TOUR_APPLICATION_UPDATE_SUCCESS, tourApplicationDto);
    }

    private User getUser(Long userId) {
        User user = userRepositoryImpl.loadUserByUserId(userId, true);
        if (user == null) {
            throw new NotFoundException("해당 유저를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_USER_EXCEPTION);
        }

        return user;
    }

    private TourApplication getTourApplication(Long tourId, String tourDate) {
        TourApplication tourApplication = tourApplicationRepositoryImpl.findTourApplicationByTourIdAndTourDate(tourId, tourDate);
        if (tourApplication == null) {
            throw new NotFoundException("해당 투어 신청서를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_USER_EXCEPTION);
        }

        return tourApplication;
    }

}
