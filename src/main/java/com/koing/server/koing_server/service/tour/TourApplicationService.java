package com.koing.server.koing_server.service.tour;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
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
import com.koing.server.koing_server.service.tour.dto.TourApplicationDto;
import com.koing.server.koing_server.service.tour.dto.TourApplicationParticipateDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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

    public SuperResponse createTourApplication(TourApplicationDto tourApplicationDto) {

        Tour tour = tourRepositoryImpl.findTourByTourId(tourApplicationDto.getTourId());

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

        return SuccessResponse.success(SuccessCode.TOUR_APPLICATION_CREATE_SUCCESS, tour.getTourApplications());
    }

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

        User user = userRepositoryImpl.loadUserByUserEmail(
                tourApplicationParticipateDto.getUserEmail(), true
        );

        if (user == null) {
            return ErrorResponse.error(ErrorCode.NOT_FOUND_USER_EXCEPTION);
        }
        LOGGER.info("[TourApplicationService] userEmail로 user 조회 성공 %s = " + user);

        List<User> participants = tourApplication.getParticipants();
        LOGGER.info("[TourApplicationService] 현재 participants = " + participants);

        Set<TourApplication> beforeUpdateTourApplications = user.getTourApplication();
        LOGGER.info("[TourApplicationService] 현재 user의 tourApplications = " + beforeUpdateTourApplications.size());

        user.setTourApplication(tourApplication);

        TourApplication updatedTourApplication = tourApplicationRepository.save(tourApplication);

        if (updatedTourApplication.getParticipants().size() == participants.size()) {
            return ErrorResponse.error(ErrorCode.DB_FAIL_UPDATE_TOUR_APPLICATION_FAIL_EXCEPTION);
        }

        LOGGER.info("[TourApplicationService] participants update 성공 = " + updatedTourApplication.getParticipants());

        User savedUser = userRepository.save(user);

        if (beforeUpdateTourApplications.size() == savedUser.getTourApplication().size()) {
            return ErrorResponse.error(ErrorCode.DB_FAIL_UPDATE_USER_FAIL_EXCEPTION);
        }
        LOGGER.info("[TourApplicationService] user tourApplication update 성공 = " + savedUser.getTourApplication());

        return SuccessResponse.success(SuccessCode.TOUR_APPLICATION_UPDATE_SUCCESS, updatedTourApplication);
    }


}
