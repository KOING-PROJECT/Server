package com.koing.server.koing_server.domain.review.repository;

import com.koing.server.koing_server.domain.review.ReviewToGuide;

import java.util.List;

public interface ReviewToGuideRepositoryCustom {

    List<ReviewToGuide> findReviewToGuideByTourId(Long tourId);

}
