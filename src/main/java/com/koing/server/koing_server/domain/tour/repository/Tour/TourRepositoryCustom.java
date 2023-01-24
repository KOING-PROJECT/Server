package com.koing.server.koing_server.domain.tour.repository.Tour;

import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.user.User;

import java.util.List;
import java.util.Set;

public interface TourRepositoryCustom {

    Tour findTourByTourId(Long id);

    List<Tour> findTourByStatusRecruitmentAndStandby();

    List<Tour> findLikeTourByUser(Long userId);

}