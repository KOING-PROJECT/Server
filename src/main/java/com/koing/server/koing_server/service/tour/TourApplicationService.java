package com.koing.server.koing_server.service.tour;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.DBFailException;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.tour.TourApplication;
import com.koing.server.koing_server.domain.tour.repository.TourApplicationRepository;
import com.koing.server.koing_server.domain.tour.repository.TourApplicationRepositoryImpl;
import com.koing.server.koing_server.domain.tour.repository.TourRepository;
import com.koing.server.koing_server.domain.tour.repository.TourRepositoryImpl;
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

    @Transactional
    public SuperResponse createTourApplication(TourApplicationCreateDto tourApplicationCreateDto) {

        Tour tour = tourRepositoryImpl.findTourByTourId(tourApplicationCreateDto.getTourId());

        if (tour == null) {
            return ErrorResponse.error(ErrorCode.NOT_FOUND_TOUR_EXCEPTION);
        }

        LOGGER.info("[TourApplicationService] TourId로 tour 찾기 성공 = " + tour);
        int tourMaxParticipant = tour.getParticipant();

        Set<String> tourDates = tour.getTourSchedule().getTourDates();

        for (String tourDate: tourDates) {
            TourApplication tourApplication = new TourApplication(tourDate, tourMaxParticipant);
            tourApplication.setTour(tour);

            TourApplication savedTourApplication = tourApplicationRepository.save(tourApplication);

            if (savedTourApplication.getId() == null) {
                return ErrorResponse.error(ErrorCode.DB_FAIL_CREATE_TOUR_APPLICATION_FAIL_EXCEPTION);
            }

            LOGGER.info("[TourApplicationService] TourApplication 생성 성공 = " + savedTourApplication);
        }

//        tour.setTourApplication(savedTourApplication);
        Tour savedTour = tourRepository.save(tour);

        if (savedTour.getTourApplications().size() != tourDates.size()) {
            return ErrorResponse.error(ErrorCode.DB_FAIL_UPDATE_TOUR_FAIL_EXCEPTION);
        }

        LOGGER.info("[TourApplicationService] 투어에 TourApplication 업데이트 성공 = " + savedTour);

        TourApplicationDto tourApplicationDto = new TourApplicationDto(savedTour);

        return SuccessResponse.success(SuccessCode.TOUR_APPLICATION_CREATE_SUCCESS, tourApplicationDto);
    }

    @Transactional
    public SuperResponse participateTour(TourApplicationParticipateDto tourApplicationParticipateDto) {
        Long tourId = tourApplicationParticipateDto.getTourId();
        String tourDate = tourApplicationParticipateDto.getTourDate();

        TourApplication tourApplication = tourApplicationRepositoryImpl
                .findTourApplicationByTourIdAndTourDate(
                        tourId,
                        tourDate
                );

        if (tourApplication == null) {
            return ErrorResponse.error(ErrorCode.NOT_FOUND_TOUR_EXCEPTION);
        }

        LOGGER.info("[TourApplicationService] TourId로 TourApplication 조회 성공 = " + tourApplication);

        int currParticipants = tourApplication.getCurrentParticipants();
        int participantSize = tourApplication.getParticipants().size();
        LOGGER.info("[TourApplicationService] 현재 currParticipants = " + currParticipants);
        LOGGER.info("[TourApplicationService] 현재 participantSize = " + participantSize);

        int numberOfParticipants = tourApplicationParticipateDto.getNumberOfParticipants();

        if (currParticipants + numberOfParticipants > tourApplication.getMaxParticipant()) {
            return ErrorResponse.error(ErrorCode.NOT_ACCEPTABLE_OVER_MAX_PARTICIPANTS_EXCEPTION);
        }

        User user = userRepositoryImpl.loadUserByUserId(
                tourApplicationParticipateDto.getUserId(), true
        );

        if (user == null) {
            return ErrorResponse.error(ErrorCode.NOT_FOUND_USER_EXCEPTION);
        }
        LOGGER.info("[TourApplicationService] userEmail로 user 조회 성공 %s = " + user);

        int beforeUpdateTourApplications = user.getTourApplication().size();
        LOGGER.info("[TourApplicationService] 현재 user의 tourApplications = " + beforeUpdateTourApplications);

        user.setTourApplication(tourApplication);
        tourApplication.setCurrentParticipants(currParticipants + numberOfParticipants);

        TourApplication updatedTourApplication = tourApplicationRepository.save(tourApplication);

        LOGGER.info("[TourApplicationService] save 후 currParticipants = " + updatedTourApplication.getCurrentParticipants());
        LOGGER.info("[TourApplicationService] save 후 participantSize = " + updatedTourApplication.getParticipants().size());
        if (updatedTourApplication.getParticipants().size() == participantSize
                || updatedTourApplication.getCurrentParticipants() == currParticipants) {
            return ErrorResponse.error(ErrorCode.DB_FAIL_UPDATE_TOUR_APPLICATION_FAIL_EXCEPTION);
//            throw new DBFailException("투어 신청서 update 과정에서 오류가 발생했습니다. 다시 시도해 주세요.",
//                    ErrorCode.DB_FAIL_UPDATE_TOUR_APPLICATION_FAIL_EXCEPTION);
        }

        LOGGER.info("[TourApplicationService] participants update 성공 = " + updatedTourApplication.getParticipants());

        User savedUser = userRepository.save(user);

        if (beforeUpdateTourApplications == savedUser.getTourApplication().size()) {
            return ErrorResponse.error(ErrorCode.DB_FAIL_UPDATE_USER_FAIL_EXCEPTION);
        }
        LOGGER.info("[TourApplicationService] user tourApplication update 성공 = " + savedUser.getTourApplication());

        TourApplicationDto tourApplicationDto = new TourApplicationDto(updatedTourApplication.getTour());

        return SuccessResponse.success(SuccessCode.TOUR_APPLICATION_UPDATE_SUCCESS, tourApplicationDto);
    }

    @Transactional
    public SuperResponse getTourApplications(Long userId) {
        LOGGER.info("[TourApplicationService] userId로 user 조회 시도");

        User user = userRepositoryImpl.loadUserByUserId(userId, true);

        if (user == null) {
            return ErrorResponse.error(ErrorCode.NOT_FOUND_USER_EXCEPTION);
        }

        LOGGER.info("[TourApplicationService] userId로 user 조회 성공");

        LOGGER.info("[TourApplicationService] user로 tourApplication 조회 시도");
        List<TourApplication> tourApplications = tourApplicationRepositoryImpl.findTourApplicationsByUser(user);

        if (tourApplications == null) {
            return ErrorResponse.error(ErrorCode.NOT_FOUND_TOUR_APPLICATION_EXCEPTION);
        }

        LOGGER.info("[TourApplicationService] user로 tourApplication 조회 성공 = " + tourApplications);

        List<TourApplicationDto> tourApplicationDtos = new ArrayList<>();

        for (TourApplication tourApplication : tourApplications) {
            tourApplicationDtos.add(new TourApplicationDto(tourApplication.getTour()));
        }

        return SuccessResponse.success(SuccessCode.GET_TOUR_APPLICATIONS_SUCCESS, new TourApplicationResponseDto(tourApplicationDtos));
    }

}
