package com.koing.server.koing_server.domain.tour.repository.TourApplication;

import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.tour.TourApplication;
import com.koing.server.koing_server.domain.user.User;

import java.util.List;

public interface TourApplicationRepositoryCustom {

    List<TourApplication> findTourApplicationsByTourId(Long tourId);

    TourApplication findTourApplicationByTourIdAndTourDate(Long tourId, String tourDate);

}
