package com.koing.server.koing_server.domain.tour.repository;

import com.koing.server.koing_server.domain.tour.TourSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourScheduleRepository extends JpaRepository<TourSchedule, Long> {
}
