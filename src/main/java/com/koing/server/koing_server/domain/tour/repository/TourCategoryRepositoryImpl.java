package com.koing.server.koing_server.domain.tour.repository;

import com.koing.server.koing_server.domain.tour.TourCategory;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.koing.server.koing_server.domain.tour.QTourCategory.tourCategory;

@RequiredArgsConstructor
public class TourCategoryRepositoryImpl implements TourCategoryRepositoryCustom {

    private final JPQLQueryFactory jpqlQueryFactory;

    @Override
    public TourCategory findTourCategoryByCategoryName(String categoryName) {
        return jpqlQueryFactory
                .selectFrom(tourCategory)
                .where(
                        tourCategory.categoryName.eq(categoryName)
                ).fetchOne();
    }
}
