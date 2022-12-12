package com.koing.server.koing_server.service.tour;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.enums.TourStatus;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.tour.TourCategory;
import com.koing.server.koing_server.domain.tour.repository.TourCategoryRepositoryImpl;
import com.koing.server.koing_server.domain.tour.repository.TourRepository;
import com.koing.server.koing_server.domain.tour.repository.TourRepositoryImpl;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.domain.user.repository.UserRepository;
import com.koing.server.koing_server.domain.user.repository.UserRepositoryImpl;
import com.koing.server.koing_server.service.tour.dto.TourDto;
import com.koing.server.koing_server.service.tour.dto.TourListResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TourService {

    private final Logger LOGGER = LoggerFactory.getLogger(TourService.class);
    private final TourRepository tourRepository;
    private final TourRepositoryImpl tourRepositoryImpl;
    private final TourCategoryRepositoryImpl tourCategoryRepositoryImpl;
    private final UserRepositoryImpl userRepositoryImpl;

    public SuperResponse getTours() {

        LOGGER.info("[TourService] Tour list 요청");

        // 모집중이거나 모집이 완료되었지만 아직 시작안한(대기 가능하게) tour list 반환
        List<Tour> tourList = tourRepositoryImpl.findTourByStatusRecruitmentAndStandby();
        LOGGER.info("[TourService] Tour list 요청 완료");

        TourListResponseDto tourListResponseDto = new TourListResponseDto(tourList);

        return SuccessResponse.success(SuccessCode.GET_TOURS_SUCCESS, tourListResponseDto);
    }

    public SuperResponse createTour(TourDto tourDto) {

        LOGGER.info("[TourService] Tour 생성 시도");

        if (!userRepositoryImpl.isExistUserByUserEmail(tourDto.getCreateUserEmail())) {
            return ErrorResponse.error(ErrorCode.NOT_FOUND_USER_EXCEPTION);
        }
        Tour tour = buildTour(tourDto);

        Tour savedTour = tourRepository.save(tour);

        if(savedTour.getTitle() == null) {
            return ErrorResponse.error(ErrorCode.DB_FAIL_CREATE_TOUR_FAIL_EXCEPTION);
        }

        return SuccessResponse.success(SuccessCode.TOUR_CREATE_SUCCESS, savedTour);
    }

    private Tour buildTour(TourDto tourDto) {
        Tour tour = Tour.builder()
                .createUser(getCreatUser(tourDto.getCreateUserEmail()))
                .title(tourDto.getTitle())
                .description(tourDto.getDescription())
                .tourCategories(buildTourCategories(tourDto.getTourCategoryNames()))
                .thumbnail(tourDto.getThumbnail())
                .participant(tourDto.getParticipant())
                .tourPrice(tourDto.getTourPrice())
                .hasLevy(tourDto.isHasLevy())
                .tourStatus(TourStatus.RECRUITMENT)
                .additionalPrice(buildAdditionalPrice(tourDto.getAdditionalPrice()))
                .build();

        return tour;
    }

    private User getCreatUser(String createUserEmail) {
        User createUser = userRepositoryImpl.loadUserByUserEmail(createUserEmail, true);
        return createUser;
    }

    private Set<TourCategory> buildTourCategories(List<String> tourCategoryNames) {
        Set<TourCategory> tourCategories = new HashSet<>();

        for (String tourCategoryName : tourCategoryNames) {
            tourCategories.add(tourCategoryRepositoryImpl.findTourCategoryByCategoryName(tourCategoryName));
        }

        return tourCategories;
    }


    private Set<HashMap<String, List>> buildAdditionalPrice(List<String> additionalPrice) {

        Set<HashMap<String, List>> hashMaps = new HashSet<>();

        for (String ap : additionalPrice) {
            String[] splitAp = ap.split("/");

            List<String> priceAndCheck = new ArrayList<>();
            priceAndCheck.add(splitAp[1]);
            priceAndCheck.add(splitAp[2]);

            HashMap<String, List> map = new HashMap<>();
            map.put(splitAp[0], priceAndCheck);
            hashMaps.add(map);
        }

        return hashMaps;
    }


}
