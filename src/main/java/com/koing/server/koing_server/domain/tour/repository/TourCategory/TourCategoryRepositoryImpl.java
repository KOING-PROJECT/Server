package com.koing.server.koing_server.domain.tour.repository.TourCategory;

import com.koing.server.koing_server.domain.tour.TourCategory;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.koing.server.koing_server.domain.tour.QTour.tour;
import static com.koing.server.koing_server.domain.tour.QTourCategory.tourCategory;

@RequiredArgsConstructor
public class TourCategoryRepositoryImpl implements TourCategoryRepositoryCustom {

    private final JPQLQueryFactory jpqlQueryFactory;

    @Override
    public TourCategory findTourCategoryByCategoryName(String categoryName) {
        return jpqlQueryFactory
                .selectFrom(tourCategory)
                .leftJoin(tourCategory.categorizedTours, tour)
                .fetchJoin()
                .leftJoin(tourCategory.detailTypes)
                .fetchJoin()
                .distinct()
                .where(
                        tourCategory.categoryName.eq(categoryName)
                ).fetchOne();
    }

    @Override
    public List<TourCategory> findAll() {
        return jpqlQueryFactory
                .selectFrom(tourCategory)
                .leftJoin(tourCategory.categorizedTours, tour)
                .fetchJoin()
                .leftJoin(tourCategory.detailTypes)
                .fetchJoin()
                .distinct()
                .fetch();
    }
}
