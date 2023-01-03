package com.koing.server.koing_server.domain.tour.repository.TourSchedule;

import com.koing.server.koing_server.domain.tour.TourSchedule;

public interface TourScheduleRepositoryCustom {

    TourSchedule findTourScheduleByTourId(Long tourId);

}
