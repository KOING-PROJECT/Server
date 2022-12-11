package com.koing.server.koing_server.domain.tour.repository;

import com.koing.server.koing_server.domain.tour.Tour;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourRepository extends JpaRepository<Tour, Long> {
}
