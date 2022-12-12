package com.koing.server.koing_server.domain.tour.repository;

import com.koing.server.koing_server.domain.tour.TourCategory;

public interface TourCategoryRepositoryCustom {

    TourCategory findTourCategoryByCategoryName(String categoryName);

}
