package com.koing.server.koing_server.domain.tour.repository.Tour;

import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.user.User;

import java.util.List;
import java.util.Set;

public interface TourRepositoryCustom {

    Tour findTourByTourId(Long id);

    List<Tour> findTourByStatusRecruitment();

    List<Tour> findLikeTourByUser(Long userId);

    Tour findTemporaryTourByTourId(Long tourId);

    boolean checkExistByTourId(Long tourId);

    List<Tour> findNotFinishTour();

    List<Tour> findFinishedTour();

    List<Tour> findCreatedTour(); // 투어를 만들었지만 승인이 나지 않은 투어

}
