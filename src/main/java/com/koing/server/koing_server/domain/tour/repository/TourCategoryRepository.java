package com.koing.server.koing_server.domain.tour.repository;

import com.koing.server.koing_server.domain.tour.TourCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourCategoryRepository extends JpaRepository<TourCategory, Long> {
}
