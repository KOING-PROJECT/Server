package com.koing.server.koing_server.service.tour;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.tour.TourApplication;
import com.koing.server.koing_server.domain.tour.repository.TourApplicationRepository;
import com.koing.server.koing_server.domain.tour.repository.TourRepository;
import com.koing.server.koing_server.domain.tour.repository.TourRepositoryImpl;
import com.koing.server.koing_server.service.tour.dto.TourApplicationDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TourApplicationService {

    private final Logger LOGGER = LoggerFactory.getLogger(TourApplicationService.class);
    private final TourApplicationRepository tourApplicationRepository;
    private final TourRepository tourRepository;

    public SuperResponse createTourApplication(TourApplicationDto tourApplicationDto) {

        Optional<Tour> optionalTour = tourRepository.findById(tourApplicationDto.getTourId());

        if (optionalTour == null) {
            return ErrorResponse.error(ErrorCode.NOT_FOUND_TOUR_EXCEPTION);
        }

        Tour tour = optionalTour.get();

        TourApplication tourApplication = new TourApplication(tour);
        TourApplication savedTourApplication = tourApplicationRepository.save(tourApplication);

        if (savedTourApplication.getId() == null) {
            return ErrorResponse.error(ErrorCode.DB_FAIL_CREATE_TOUR_APPLICATION_FAIL_EXCEPTION);
        }

        return SuccessResponse.success(SuccessCode.TOUR_APPLICATION_CREATE_SUCCESS, savedTourApplication);
    }

}
