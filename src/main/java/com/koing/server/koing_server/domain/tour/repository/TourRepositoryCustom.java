package com.koing.server.koing_server.domain.tour.repository;

import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.service.tour.dto.TourListDto;

import java.util.List;

public interface TourRepositoryCustom {

    Tour findTourByTourId(Long id);

    List<TourListDto> findTourByStatusRecruitmentAndStandby();

}
