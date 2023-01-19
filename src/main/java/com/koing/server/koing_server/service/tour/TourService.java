package com.koing.server.koing_server.service.tour;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.enums.CreateStatus;
import com.koing.server.koing_server.common.enums.TourCategoryIndex;
import com.koing.server.koing_server.common.enums.TourStatus;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.DBFailException;
import com.koing.server.koing_server.common.exception.NotFoundException;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.tour.TourCategory;
import com.koing.server.koing_server.domain.tour.TourSchedule;
import com.koing.server.koing_server.domain.tour.repository.TourCategory.TourCategoryRepositoryImpl;
import com.koing.server.koing_server.domain.tour.repository.Tour.TourRepository;
import com.koing.server.koing_server.domain.tour.repository.Tour.TourRepositoryImpl;
import com.koing.server.koing_server.domain.tour.repository.TourSchedule.TourScheduleRepositoryImpl;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.domain.user.repository.UserRepository;
import com.koing.server.koing_server.domain.user.repository.UserRepositoryImpl;
import com.koing.server.koing_server.service.tour.dto.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TourService {

    private final Logger LOGGER = LoggerFactory.getLogger(TourService.class);
    private final TourRepository tourRepository;
    private final TourRepositoryImpl tourRepositoryImpl;
    private final TourCategoryRepositoryImpl tourCategoryRepositoryImpl;
    private final UserRepositoryImpl userRepositoryImpl;
    private final TourScheduleRepositoryImpl tourScheduleRepositoryImpl;
    private final UserRepository userRepository;

    @Transactional
    public SuperResponse getTours(List<String> categories) {

        LOGGER.info("[TourService] Tour list 요청");

        // 모집중이거나 모집이 완료되었지만 아직 시작안한(대기 가능하게) tour list 반환
        List<Tour> tours;
        if (categories.contains("전체")) {
            tours = tourRepositoryImpl.findTourByStatusRecruitmentAndStandby();
        }
        else {
            tours = tourRepositoryImpl.findTourByStatusRecruitmentAndStandby()
                    .stream()
                    .filter(tour -> tour.checkCategories(categories))
                    .collect(Collectors.toList());
        }
        LOGGER.info("[TourService] Tour list 요청 완료");

        List<TourDto> tourDtos = new ArrayList<>();
        for (Tour tour : tours) {
            tourDtos.add(new TourDto(tour));
        }

        TourListResponseDto tourListResponseDto = new TourListResponseDto(tourDtos);

        return SuccessResponse.success(SuccessCode.GET_TOURS_SUCCESS, tourListResponseDto);
    }

    public SuperResponse createTour(TourCreateDto tourCreateDto, CreateStatus createStatus) {
        // tour create 완료 시 호출
        LOGGER.info("[TourService] Tour 생성 시도");

        if (!userRepositoryImpl.isExistUserByUserId(tourCreateDto.getCreateUserId())) {
            return ErrorResponse.error(ErrorCode.NOT_FOUND_USER_EXCEPTION);
        }

        Tour tour = buildTour(tourCreateDto, createStatus);

        Tour savedTour = tourRepository.save(tour);

        if(savedTour.getTitle() == null) {
            return ErrorResponse.error(ErrorCode.DB_FAIL_CREATE_TOUR_FAIL_EXCEPTION);
        }

        TourDto tourDto = new TourDto(savedTour);
        LOGGER.info("[TourService] Tour 생성 성공");

//        if (isSet) {
//            return SuccessResponse.success(SuccessCode.TOUR_CREATE_SUCCESS, savedTour.getId());
//        }
        return SuccessResponse.success(SuccessCode.TOUR_CREATE_SUCCESS, tourDto);
    }

    public SuperResponse deleteTour(Long tourId) {

        LOGGER.info("[TourService] Tour 삭제 시도");

        Tour tour = tourRepositoryImpl.findTourByTourId(tourId);

        if(tour == null) {
            return ErrorResponse.error(ErrorCode.NOT_FOUND_TOUR_EXCEPTION);
        }
        LOGGER.info("[TourService] 삭제할 Tour 조회 성공");

        tourRepository.delete(tour);
        LOGGER.info("[TourService] Tour 삭제 성공");

        return SuccessResponse.success(SuccessCode.DELETE_TOUR_SUCCESS, null);
    }

    @Transactional
    public SuperResponse updateTour(Long tourId, TourCreateDto tourCreateDto, CreateStatus createStatus) {
        LOGGER.info("[TourService] Tour update 시도");

        Tour tour = tourRepositoryImpl.findTourByTourId(tourId);

        if(tour == null) {
            return ErrorResponse.error(ErrorCode.NOT_FOUND_TOUR_EXCEPTION);
        }

        if (tour.getCreateUser().getId() != tourCreateDto.getCreateUserId()) {
            return ErrorResponse.error(ErrorCode.NOT_ACCEPTABLE_NOT_TOUR_CREATOR_EXCEPTION);
        }

        LOGGER.info("[TourService] update할 Tour 조회 성공");

        tour = updateTour(tour, tourCreateDto, createStatus);

        Tour updatedTour = tourRepository.save(tour);
        TourDto tourDto = new TourDto(updatedTour);

        LOGGER.info("[TourService] Tour update 성공");
        return SuccessResponse.success(SuccessCode.TOUR_UPDATE_SUCCESS, tourDto);
    }

    @Transactional
    public SuperResponse getTourDetailInfo(Long tourId, Long userId) {
        LOGGER.info("[TourService] 투어 세부 정보 조회 시도");

        Tour tour = getTour(tourId);

        TourSchedule tourSchedule = tourScheduleRepositoryImpl.findTourScheduleByTourId(tourId);

        LOGGER.info("[TourService] 투어 세부 정보 조회 성공");

        TourDetailDto tourDetailDto = new TourDetailDto(userId, tour, tourSchedule);
        LOGGER.info("[TourService] 투어 세부 정보 dto 생성 성공");

        User user = userRepositoryImpl.loadUserByUserId(userId, true);

        if (tour.getPressLikeUsers().contains(user)) {
            // 같은 유저
            System.out.println("같은 유저");
            tourDetailDto.setUserPressTourLike(true);
        }

        return SuccessResponse.success(SuccessCode.GET_TOUR_DETAIL_INFO_SUCCESS, new TourDetailResponseDto(tourDetailDto));
    }

    @Transactional
    public SuperResponse pressLikeTour(Long tourId, Long userId) {
        LOGGER.info("[TourService] 투어 좋아요 업데이트 시도");

        Tour tour = getTour(tourId);
        LOGGER.info("[TourService] 해당 투어 조회 성공");

        int beforeTourLikeUser;

        if (tour.getPressLikeUsers() == null) {
            beforeTourLikeUser = 0;
        }
        else {
            beforeTourLikeUser = tour.getPressLikeUsers().size();
        }

        User user = getUser(userId);
        LOGGER.info("[TourService] 해당 유저 조회 성공");
        int beforeUserLikeTour;

        if (user.getPressLikeTours() == null) {
            beforeUserLikeTour = 0;
        }
        else {
            beforeUserLikeTour = user.getPressLikeTours().size();
        }

        tour.pressLikeTour(user);

        Tour updatedTour = tourRepository.save(tour);
        User updatedUser = userRepository.save(user);

        if (beforeTourLikeUser == updatedTour.getPressLikeUsers().size()) {
            throw new DBFailException("좋아요 처리과정에서 오류가 발생했습니다.", ErrorCode.DB_FAIL_PRESS_LIKE_TOUR_FAIL_EXCEPTION);
        }

        if (beforeUserLikeTour == updatedUser.getPressLikeTours().size()) {
            throw new DBFailException("좋아요 처리과정에서 오류가 발생했습니다.", ErrorCode.DB_FAIL_PRESS_LIKE_TOUR_FAIL_EXCEPTION);
        }

        LOGGER.info("[TourService] 투어 좋아요 업데이트 성공");
        return SuccessResponse.success(SuccessCode.PRESS_LIKE_TOUR_UPDATE_SUCCESS, new TourDto(updatedTour));
    }

    @Transactional
    public SuperResponse getLikeTours(Long userId) {
        LOGGER.info("[TourService] 좋아요 누른 투어 조회 시도");

        User user = getUser(userId);
        LOGGER.info("[TourService] 해당 유저 조회 성공");

        Set<Tour> pressLikeTours = user.getPressLikeTours();

        List<TourDto> tourDtos = new ArrayList<>();
        for (Tour pressLikeTour : pressLikeTours) {
            tourDtos.add(new TourDto(pressLikeTour));
        }
        LOGGER.info("[TourService] 좋아요 누른 투어 조회 성공");

        return SuccessResponse.success(SuccessCode.GET_LIKE_TOURS_SUCCESS, new TourListResponseDto(tourDtos));
    }

    @Transactional
    public SuperResponse getToursByTourCategory(int categoryIndex) {

        LOGGER.info("[TourService] Home화면 Tour list 조회 시도");

        // 모집중이거나 모집이 완료되었지만 아직 시작안한(대기 가능하게) tour list 반환

        String categoryName = getCategoryName(categoryIndex);

        List<Tour> tours = tourRepositoryImpl.findTourByStatusRecruitmentAndStandby()
                .stream()
                .filter(tour -> tour.checkCategories(Arrays.asList(categoryName)))
                .collect(Collectors.toList());

        LOGGER.info("[TourService] Home화면 Tour list 조회 성공");

        List<TourHomeToursDto> tourHomeToursDtos = new ArrayList<>();
        for (Tour tour : tours) {
            tourHomeToursDtos.add(new TourHomeToursDto(tour));
        }

        return SuccessResponse.success(SuccessCode.GET_TOURS_SUCCESS, new TourHomeToursResponseDto(tourHomeToursDtos));
    }

    private Tour getTour(Long tourId) {
        Tour tour = tourRepositoryImpl.findTourByTourId(tourId);
        if (tour == null) {
            throw new NotFoundException("해당 투어를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_TOUR_EXCEPTION);
        }

        return tour;
    }

    private User getUser(Long userId) {
        User user = userRepositoryImpl.loadUserByUserId(userId, true);
        if (user == null) {
            throw new NotFoundException("해당 유저를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_USER_EXCEPTION);
        }

        return user;
    }

    private Tour buildTour(TourCreateDto tourCreateDto, CreateStatus createStatus) {
        Tour tour = Tour.builder()
                .createUser(getUser(tourCreateDto.getCreateUserId()))
                .title(tourCreateDto.getTitle())
                .description(tourCreateDto.getDescription())
                .tourCategories(buildTourCategories(tourCreateDto.getTourCategoryNames()))
                .thumbnail(tourCreateDto.getThumbnail())
                .participant(tourCreateDto.getParticipant())
                .tourPrice(tourCreateDto.getTourPrice())
                .hasLevy(tourCreateDto.isHasLevy())
                .additionalPrice(buildAdditionalPrice(tourCreateDto.getAdditionalPrice()))
                .tourStatus(TourStatus.RECRUITMENT)
                .createStatus(createStatus)
                .build();
        return tour;
    }

    public Tour updateTour(Tour tour, TourCreateDto tourCreateDto, CreateStatus createStatus) {
        tour.setTitle(tourCreateDto.getTitle());
        tour.setDescription(tourCreateDto.getDescription());
        Set<TourCategory> beforeTourCategories = tour.getTourCategories();
        tour.deleteTourCategories(beforeTourCategories);
        tour.setTourCategories(buildTourCategories(tourCreateDto.getTourCategoryNames()));
        tour.setThumbnail(tourCreateDto.getThumbnail());
        tour.setParticipant(tourCreateDto.getParticipant());
        tour.setTourPrice(tourCreateDto.getTourPrice());
        tour.setHasLevy(tourCreateDto.isHasLevy());
        tour.setAdditionalPrice(buildAdditionalPrice(tourCreateDto.getAdditionalPrice()));
        if (CreateStatus.COMPLETE.equals(createStatus)) {
            tour.setCreateStatus(createStatus);
        }
        return tour;
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

    private String getCategoryName(int categoryIndex) {

        for (TourCategoryIndex tourCategoryIndex : TourCategoryIndex.values()) {
            if (tourCategoryIndex.getCategoryIndex() == categoryIndex) {
                return tourCategoryIndex.getCategoryName();
            }
        }

        return "역사";
    }


}
