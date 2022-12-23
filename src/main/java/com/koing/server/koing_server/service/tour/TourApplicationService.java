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
import java.util.Optional;
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

        TourApplication tourApplication = new TourApplication();
        tourApplication.setTour(tour);
        //////////////////////////////////////////////
        // 수정필요
        //
        TourApplication savedTourApplication = tourApplicationRepository.save(tourApplication);

        if (savedTourApplication.getId() == null) {
            return ErrorResponse.error(ErrorCode.DB_FAIL_CREATE_TOUR_APPLICATION_FAIL_EXCEPTION);
        }

        tour.setTourApplication(savedTourApplication);
        Tour savedTour = tourRepository.save(tour);

        if (savedTour.getTourApplication() == null) {
            return ErrorResponse.error(ErrorCode.DB_FAIL_UPDATE_TOUR_FAIL_EXCEPTION);
        }

        LOGGER.info("[TourApplicationService] 투어에 TourApplication 업데이트 성공 = " + tour);

        return SuccessResponse.success(SuccessCode.TOUR_APPLICATION_CREATE_SUCCESS, savedTourApplication);
    }

    public SuperResponse participateTour(TourApplicationParticipateDto tourApplicationParticipateDto) {

        Tour tour = tourRepositoryImpl.findTourByTourId(tourApplicationParticipateDto.getTourId());

        if (tour == null) {
            return ErrorResponse.error(ErrorCode.NOT_FOUND_TOUR_EXCEPTION);
        }
        System.out.println(tour.getId());
        System.out.println(tour.getDescription());
        System.out.println(tour.getTourCategories());
        System.out.println(tour.getTourApplication());

        LOGGER.info("[TourApplicationService] TourId로 투어 조회 성공 %s = " + tour);

//        TourApplication tourApplication = tourApplicationRepositoryImpl.findTourApplicationByTour(tour);
        TourApplication tourApplication = tour.getTourApplication();

        if (tourApplication == null) {
            return ErrorResponse.error(ErrorCode.NOT_FOUND_TOUR_APPLICATION_EXCEPTION);
        }
        LOGGER.info("[TourApplicationService] 투어로 투어 신청서 조회 성공 %s = " + tourApplication);

        User user = userRepositoryImpl.loadUserByUserEmail(
                tourApplicationParticipateDto.getUserEmail(), true
        );

        if (user == null) {
            return ErrorResponse.error(ErrorCode.NOT_FOUND_USER_EXCEPTION);
        }
        LOGGER.info("[TourApplicationService] userEmail로 user 조회 성공 %s = " + user);

        List<User> participants = tourApplication.getUsers();
        LOGGER.info("[TourApplicationService] 현재 participants = " + participants);

        participants.add(user);
        tourApplication.setUsers(participants);

        TourApplication updatedTourApplication = tourApplicationRepository.save(tourApplication);

        if (updatedTourApplication.getUsers().size() == participants.size()) {
            return ErrorResponse.error(ErrorCode.DB_FAIL_UPDATE_TOUR_APPLICATION_FAIL_EXCEPTION);
        }

        LOGGER.info("[TourApplicationService] participants update 성공 = " + updatedTourApplication.getUsers());

        Set<TourApplication> tourApplications = user.getTourApplication();
        tourApplications.add(updatedTourApplication);

        User savedUser = userRepository.save(user);

        if (tourApplications.size() == savedUser.getTourApplication().size()) {
            return ErrorResponse.error(ErrorCode.DB_FAIL_UPDATE_USER_FAIL_EXCEPTION);
        }

        return SuccessResponse.success(SuccessCode.TOUR_APPLICATION_UPDATE_SUCCESS, updatedTourApplication);
    }


}
