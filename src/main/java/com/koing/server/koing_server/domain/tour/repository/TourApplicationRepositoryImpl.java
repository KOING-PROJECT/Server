package com.koing.server.koing_server.domain.tour.repository;

import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.tour.TourApplication;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.koing.server.koing_server.domain.tour.QTourApplication.tourApplication;

@RequiredArgsConstructor
public class TourApplicationRepositoryImpl implements TourApplicationRepositoryCustom {

    private final JPQLQueryFactory jpqlQueryFactory;

}
