package com.koing.server.koing_server.domain.tour.repository;

import com.koing.server.koing_server.domain.tour.Tour;

import java.util.List;

public interface TourRepositoryCustom {

    Tour findTourByTourId(Long id);

    List<Tour> findTourByStatusRecruitmentAndStandby();

}
