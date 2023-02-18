package com.koing.server.koing_server.service.tour;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.enums.CreateStatus;
import com.koing.server.koing_server.common.enums.TourCategoryIndex;
import com.koing.server.koing_server.common.enums.TourStatus;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.DBFailException;
import com.koing.server.koing_server.common.exception.IOFailException;
import com.koing.server.koing_server.common.exception.NotAcceptableException;
import com.koing.server.koing_server.common.exception.NotFoundException;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.domain.tour.*;
import com.koing.server.koing_server.domain.tour.repository.TourApplication.TourApplicationRepository;
import com.koing.server.koing_server.domain.tour.repository.TourApplication.TourApplicationRepositoryImpl;
import com.koing.server.koing_server.domain.tour.repository.TourCategory.TourCategoryRepositoryImpl;
import com.koing.server.koing_server.domain.tour.repository.Tour.TourRepository;
import com.koing.server.koing_server.domain.tour.repository.Tour.TourRepositoryImpl;
import com.koing.server.koing_server.domain.tour.repository.TourParticipant.TourParticipantRepository;
import com.koing.server.koing_server.domain.tour.repository.TourParticipant.TourParticipantRepositoryImpl;
import com.koing.server.koing_server.domain.tour.repository.TourSchedule.TourScheduleRepositoryImpl;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.domain.user.repository.UserRepository;
import com.koing.server.koing_server.domain.user.repository.UserRepositoryImpl;
import com.koing.server.koing_server.service.s3.component.AWSS3Component;
import com.koing.server.koing_server.service.tour.dto.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private final TourApplicationRepository tourApplicationRepository;
    private final TourApplicationRepositoryImpl tourApplicationRepositoryImpl;
    private final TourParticipantRepository tourParticipantRepository;
    private final TourParticipantRepositoryImpl tourParticipantRepositoryImpl;
    private final AWSS3Component awss3Component;

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

    public SuperResponse createTour(TourCreateDto tourCreateDto, List<MultipartFile> thumbnails, CreateStatus createStatus) {
        // tour create 완료 시 호출
        LOGGER.info("[TourService] Tour 생성 시도");

        if (!userRepositoryImpl.isExistUserByUserId(tourCreateDto.getCreateUserId())) {
            throw new NotFoundException("탈퇴했거나 존재하지 않는 유저입니다.", ErrorCode.NOT_FOUND_USER_EXCEPTION);
        }

        Tour tour = buildTour(tourCreateDto, thumbnails, createStatus);

        Tour savedTour = tourRepository.save(tour);

        if(savedTour.getTitle() == null) {
            throw new DBFailException("투어 생성과정에서 오류가 발생했습니다. 다시 시도해 주세요.", ErrorCode.DB_FAIL_CREATE_TOUR_FAIL_EXCEPTION);
        }

        TourDto tourDto = new TourDto(savedTour);
        LOGGER.info("[TourService] Tour 생성 성공");

        return SuccessResponse.success(SuccessCode.TOUR_CREATE_SUCCESS, tourDto);
    }

    @Transactional
    public SuperResponse deleteTour(Long tourId) {

        LOGGER.info("[TourService] Tour 삭제 시도");

        Tour tour = getTourAtDB(tourId);
        LOGGER.info("[TourService] 삭제할 Tour 조회 성공");

        List<TourApplication> tourApplications = getTourApplicationsAtDB(tourId);
        LOGGER.info("[TourService] Tour의 TourApplication 조회");

        for (TourApplication tourApplication : tourApplications) {
            if (tourApplication.getTourParticipants() != null &&
                    tourApplication.getTourParticipants().size() > 0) {
                TourParticipant tourParticipant = getTourParticipantAtDB(tourApplication.getId());
                tourParticipantRepository.delete(tourParticipant);
            }
            tourApplication.deleteTour(tour);
        }

        tourApplicationRepository.deleteAll(tourApplications);
        LOGGER.info("[TourService] Tour의 TourApplication 삭제 성공");

        tourRepository.delete(tour);
        LOGGER.info("[TourService] Tour 삭제 성공");

        return SuccessResponse.success(SuccessCode.DELETE_TOUR_SUCCESS, null);
    }

    @Transactional
    public SuperResponse updateTour(Long tourId, TourCreateDto tourCreateDto, List<MultipartFile> thumbnails, CreateStatus createStatus) {
        LOGGER.info("[TourService] Tour update 시도");

        Tour tour = tourRepositoryImpl.findTourByTourId(tourId);

        if(tour == null) {
            throw new NotFoundException("해당 투어를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_TOUR_EXCEPTION);
        }

        if (tour.getCreateUser().getId() != tourCreateDto.getCreateUserId()) {
            throw new NotAcceptableException("해당 투어의 생성자가 아닙니다.", ErrorCode.NOT_ACCEPTABLE_NOT_TOUR_CREATOR_EXCEPTION);
        }

        LOGGER.info("[TourService] update할 Tour 조회 성공");

        tour = updateTour(tour, tourCreateDto, thumbnails, createStatus);

        Tour updatedTour = tourRepository.save(tour);
        TourDto tourDto = new TourDto(updatedTour);

        LOGGER.info("[TourService] Tour update 성공");
        return SuccessResponse.success(SuccessCode.TOUR_UPDATE_SUCCESS, tourDto);
    }

    @Transactional
    public SuperResponse getTourDetailInfo(Long tourId, Long userId) {
        LOGGER.info("[TourService] 투어 세부 정보 조회 시도");

        Tour tour = getTourAtDB(tourId);

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

        Tour tour = getTourAtDB(tourId);
        LOGGER.info("[TourService] 해당 투어 조회 성공");

        int beforeTourLikeUser;

        if (tour.getPressLikeUsers() == null) {
            beforeTourLikeUser = 0;
        }
        else {
            beforeTourLikeUser = tour.getPressLikeUsers().size();
        }

        User user = getUserAtDB(userId);
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

        User user = getUserAtDB(userId);
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

    @Transactional
    public SuperResponse getTemporaryTour(Long temporaryTourId) {
        LOGGER.info("[TourService] 임시저장한 투어 정보 조회 시도");

        Tour temporaryTour = getTemporaryTourAtDB(temporaryTourId);
        LOGGER.info("[TourService] 임시저장한 투어 정보 조회 성공");

        TourTemporaryTourDto tourTemporaryTourDto = new TourTemporaryTourDto(temporaryTour);
        LOGGER.info("[TourService] tourTemporaryTourDto 생성 성공");

        return SuccessResponse.success(SuccessCode.GET_TEMPORARY_TOUR_SUCCESS, tourTemporaryTourDto);
    }

    private Tour getTourAtDB(Long temporaryTourId) {
        Tour temporaryTour = tourRepositoryImpl.findTourByTourId(temporaryTourId);
        if (temporaryTour == null) {
            throw new NotFoundException("해당 투어를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_TOUR_EXCEPTION);
        }

        return temporaryTour;
    }

    private Tour getTemporaryTourAtDB(Long tourId) {
        Tour tour = tourRepositoryImpl.findTemporaryTourByTourId(tourId);
        if (tour == null) {
            throw new NotFoundException("이어서 만들 투어를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_TEMPORARY_TOUR_EXCEPTION);
        }

        return tour;
    }

    private User getUserAtDB(Long userId) {
        User user = userRepositoryImpl.loadUserByUserId(userId, true);
        if (user == null) {
            throw new NotFoundException("해당 유저를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_USER_EXCEPTION);
        }

        return user;
    }

    private TourParticipant getTourParticipantAtDB(Long tourApplicationId) {
        TourParticipant tourParticipant = tourParticipantRepositoryImpl.findTourParticipantByTourApplicationId(tourApplicationId);
        if (tourParticipant == null) {
            throw new NotFoundException("해당 투어 신청 내용을 찾을 수 없습니다.", ErrorCode.NOT_FOUND_TOUR_PARTICIPANT_EXCEPTION);
        }

        return tourParticipant;
    }

    private List<TourApplication> getTourApplicationsAtDB(Long tourId) {
        List<TourApplication> tourApplications = tourApplicationRepositoryImpl.findTourApplicationsByTourId(tourId);

        return tourApplications;
    }

    private Tour buildTour(TourCreateDto tourCreateDto, List<MultipartFile> thumbnails, CreateStatus createStatus) {
        Tour tour = Tour.builder()
                .createUser(getUserAtDB(tourCreateDto.getCreateUserId()))
                .title(tourCreateDto.getTitle())
                .description(tourCreateDto.getDescription())
                .tourCategories(buildTourCategories(tourCreateDto.getTourCategoryNames()))
                .thumbnails(uploadThumbnails(tourCreateDto.getUploadedThumbnailUrls(), thumbnails))
                .participant(tourCreateDto.getParticipant())
                .tourPrice(tourCreateDto.getTourPrice())
                .hasLevy(tourCreateDto.isHasLevy())
                .temporarySavePage(tourCreateDto.getTemporarySavePage())
                .additionalPrice(buildAdditionalPrice(tourCreateDto.getAdditionalPrice()))
                .tourStatus(TourStatus.RECRUITMENT)
                .createStatus(createStatus)
                .build();
        return tour;
    }

    public Tour updateTour(Tour tour, TourCreateDto tourCreateDto, List<MultipartFile> thumbnails, CreateStatus createStatus) {
        tour.setTitle(tourCreateDto.getTitle());
        tour.setDescription(tourCreateDto.getDescription());
        if (tour.getTourCategories() != null) {
            Set<TourCategory> beforeTourCategories = tour.getTourCategories();
            tour.deleteTourCategories(beforeTourCategories);
        }
        tour.setTourCategories(buildTourCategories(tourCreateDto.getTourCategoryNames()));
        tour.setThumbnails(uploadThumbnails(tourCreateDto.getUploadedThumbnailUrls(), thumbnails));
        tour.setParticipant(tourCreateDto.getParticipant());
        tour.setTourPrice(tourCreateDto.getTourPrice());
        tour.setHasLevy(tourCreateDto.isHasLevy());
        tour.setTemporarySavePage(tourCreateDto.getTemporarySavePage());
        tour.setAdditionalPrice(buildAdditionalPrice(tourCreateDto.getAdditionalPrice()));
        if (CreateStatus.COMPLETE.equals(createStatus)) {
            tour.setCreateStatus(createStatus);
        }
        return tour;
    }

    private Set<String> uploadThumbnails(List<String> uploadedThumbnailUrls, List<MultipartFile> multipartFiles) {
        LOGGER.info("[TourService] 투어 썸네일 s3에 upload 시도");
        Set<String> thumbnails = new HashSet<>();

        thumbnails.addAll(uploadedThumbnailUrls);

        if (multipartFiles != null) {
            for (MultipartFile multipartFile : multipartFiles) {
                try {
                    thumbnails.add(awss3Component.convertAndUploadFiles(multipartFile, "tour/thumbnail"));
                } catch (IOException ioException) {
                    throw new IOFailException("이미지 저장 과정에서 오류가 발생했습니다.", ErrorCode.DB_FAIL_UPLOAD_IMAGE_FAIL_EXCEPTION);
                }
            }
        }

        LOGGER.info("[TourService] 투어 썸네일 s3에 upload 완료 = " + thumbnails);

        return thumbnails;
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
