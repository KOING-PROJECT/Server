package com.koing.server.koing_server.domain.review.repository;

import com.koing.server.koing_server.domain.review.ReviewToTourist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewToTouristRepository extends JpaRepository<ReviewToTourist, Long> {
}
