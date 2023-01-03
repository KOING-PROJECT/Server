package com.koing.server.koing_server.domain.tour.repository.TourCategory;

import com.koing.server.koing_server.domain.tour.TourCategory;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TourCategoryRepositoryCustom {

    TourCategory findTourCategoryByCategoryName(String categoryName);

    List<TourCategory> findAll();

}
