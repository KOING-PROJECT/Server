package com.koing.server.koing_server.service.review;

import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.enums.TourApplicationStatus;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.DBFailException;
import com.koing.server.koing_server.common.exception.IOFailException;
import com.koing.server.koing_server.common.exception.NotFoundException;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.domain.review.ReviewToGuide;
import com.koing.server.koing_server.domain.review.ReviewToTourist;
import com.koing.server.koing_server.domain.review.repository.ReviewToGuideRepository;
import com.koing.server.koing_server.domain.review.repository.ReviewToTouristRepository;
import com.koing.server.koing_server.domain.tour.TourApplication;
import com.koing.server.koing_server.domain.tour.TourParticipant;
import com.koing.server.koing_server.domain.tour.repository.TourApplication.TourApplicationRepository;
import com.koing.server.koing_server.domain.tour.repository.TourApplication.TourApplicationRepositoryImpl;
import com.koing.server.koing_server.domain.tour.repository.TourParticipant.TourParticipantRepository;
import com.koing.server.koing_server.domain.tour.repository.TourParticipant.TourParticipantRepositoryImpl;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.domain.user.repository.UserRepositoryImpl;
import com.koing.server.koing_server.service.review.dto.*;
import com.koing.server.koing_server.service.s3.component.AWSS3Component;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final Logger LOGGER = LoggerFactory.getLogger(ReviewService.class);
    private final ReviewToGuideRepository reviewToGuideRepository;
    private final ReviewToTouristRepository reviewToTouristRepository;
    private final TourApplicationRepository tourApplicationRepository;
    private final TourApplicationRepositoryImpl tourApplicationRepositoryImpl;
    private final UserRepositoryImpl userRepositoryImpl;
    private final TourParticipantRepositoryImpl tourParticipantRepositoryImpl;
    private final TourParticipantRepository tourParticipantRepository;
    private final AWSS3Component awss3Component;

    public SuperResponse reviewToGuide(ReviewToGuideCreateDto reviewToGuideCreateDto, List<MultipartFile> multipartFiles) {
        LOGGER.info("[ReviewService] reviewToGuide 작성 시도");

        User writeUser = getUser(reviewToGuideCreateDto.getWriteTouristId());
        LOGGER.info("[ReviewService] writeTouristId로 작성자 조회 성공");

        TourApplication tourApplication = getTourApplication(reviewToGuideCreateDto.getTourId(), reviewToGuideCreateDto.getTourDate());
        LOGGER.info("[ReviewService] tourId와 tourDate로 tourApplication 조회 성공");

        List<String> reviewPhotos = new ArrayList<>();
        if (multipartFiles != null) {
            for (MultipartFile multipartFile : multipartFiles) {
                try {
                    reviewPhotos.add(awss3Component.convertAndUploadFiles(multipartFile, "review/image"));
                } catch (IOException ioException) {
                    throw new IOFailException("이미지 저장 과정에서 오류가 발생했습니다.", ErrorCode.DB_FAIL_UPLOAD_IMAGE_FAIL_EXCEPTION);
                }
            }
        }

        LOGGER.info("[ReviewService] 투어 리뷰 이미지 s3에 upload 완료 = " + reviewPhotos);

        ReviewToGuide reviewToGuide = new ReviewToGuide(reviewToGuideCreateDto, writeUser, reviewPhotos);
        reviewToGuide.setRelatedTourApplication(tourApplication, writeUser.getId());
        LOGGER.info("[ReviewService] ReviewToGuide 생성");

        ReviewToGuide savedReviewToGuide = reviewToGuideRepository.save(reviewToGuide);

        if (savedReviewToGuide == null) {
            throw new DBFailException("ReviewToGuide 저장과정에서 오류가 발생했습니다.", ErrorCode.DB_FAIL_CREATE_REVIEW_TO_GUIDE_FAIL_EXCEPTION);
        }

        LOGGER.info("[ReviewService] ReviewToGuide 저장 완료");

        return SuccessResponse.success(SuccessCode.REVIEW_TO_GUIDE_CREATE_SUCCESS, null);
    }

    public SuperResponse reviewToTourist(ReviewToTouristCreateDto reviewToTouristCreateDto) {
        LOGGER.info("[ReviewService] ReviewToTourist 작성 시도");

        User writeGuide = getUser(reviewToTouristCreateDto.getWriteGuideId());
        LOGGER.info("[ReviewService] writeGuideId로 작성자 조회 성공");

        TourParticipant tourParticipant = getTourParticipant(
                reviewToTouristCreateDto.getTourId(),
                reviewToTouristCreateDto.getTourDate(),
                reviewToTouristCreateDto.getTourParticipantId()
        );
        LOGGER.info("[ReviewService] tourParticipantId로 투어 신청 내용 조회 성공");

        ReviewToTourist reviewToTourist = new ReviewToTourist(reviewToTouristCreateDto, writeGuide);
        LOGGER.info("[ReviewService] ReviewToTourist 생성");
        reviewToTourist.setRelatedTourParticipant(tourParticipant);

        ReviewToTourist savedReviewToTourist = reviewToTouristRepository.save(reviewToTourist);

        if (savedReviewToTourist == null) {
            throw new DBFailException("ReviewToTourist 저장과정에서 오류가 발생했습니다.", ErrorCode.DB_FAIL_CREATE_REVIEW_TO_TOURIST_FAIL_EXCEPTION);
        }

        TourParticipant updatedTourParticipant = tourParticipantRepository.save(tourParticipant);

        if (updatedTourParticipant == null) {
            throw new DBFailException("투어 신청 내용 업데이트 과정에서 오류가 발생했습니다.", ErrorCode.DB_FAIL_UPDATE_TOUR_PARTICIPANT_FAIL_EXCEPTION);
        }

        TourApplication tourApplication = getTourApplication(reviewToTouristCreateDto.getTourId(), reviewToTouristCreateDto.getTourDate());
        if (checkGuideReviewedAll(tourApplication)) {
            tourApplication.setTourApplicationStatus(TourApplicationStatus.GUIDE_REVIEWED);

            TourApplication updatedTourApplication = tourApplicationRepository.save(tourApplication);

            if (!updatedTourApplication.getTourApplicationStatus().equals(TourApplicationStatus.GUIDE_REVIEWED)) {
                throw new DBFailException("투어 신청서 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요.", ErrorCode.DB_FAIL_UPDATE_TOUR_APPLICATION_FAIL_EXCEPTION);
            }
        }

        LOGGER.info("[ReviewService] ReviewToTourist 저장 완료");

        return SuccessResponse.success(SuccessCode.REVIEW_TO_TOURIST_CREATE_SUCCESS, null);
    }

    @Transactional
    public SuperResponse getGuideMyPageReviewList(Long tourId, String tourDate) {
        LOGGER.info("[ReviewService] Guide의 후기 작성 리스트 조회 시도");

//        String convertTourDate = tourDate.substring(0, 4) + "/" + tourDate.substring(4,6) + "/" + tourDate.substring(6);
        TourApplication tourApplication = getTourApplication(tourId, tourDate);

        List<TourParticipant> tourParticipants = tourApplication.getTourParticipants();

        List<ReviewGuideMyPageResponseDto> reviewGuideMyPageResponseDtos = new ArrayList<>();

        for (TourParticipant tourParticipant : tourParticipants) {
            if (tourParticipant.getReviewToTourist() != null) {
                reviewGuideMyPageResponseDtos.add(new ReviewGuideMyPageResponseDto(tourParticipant, true));
            }
            else {
                reviewGuideMyPageResponseDtos.add(new ReviewGuideMyPageResponseDto(tourParticipant, false));
            }
        }
        LOGGER.info("[ReviewService] Guide의 후기 작성 리스트 조회 성공");

        return SuccessResponse.success(SuccessCode.GET_GUIDE_MY_PAGE_REVIEW_LIST_SUCCESS, new ReviewGuideMyPageListResponseDto(reviewGuideMyPageResponseDtos));
    }

    @Transactional
    public SuperResponse getGuideMyPageReviews(Long tourId, String tourDate, Long touristId) {
        LOGGER.info("[ReviewService] Guide MyPage Reviews 조회 시도");

        TourApplication tourApplication = getTourApplication(tourId, tourDate);

        LOGGER.info("[ReviewService] tourId, tourDate로 tourApplication 조회");

        List<TourParticipant> tourParticipants = tourApplication.getTourParticipants();

        ReviewGuideReviewCheckDto reviewGuideReviewCheckDto = new ReviewGuideReviewCheckDto();

        for (TourParticipant tourParticipant : tourParticipants) {
            if (tourParticipant.getParticipant().getId() == touristId) {
                if (tourParticipant.getReviewToTourist() != null) {
                    reviewGuideReviewCheckDto.setSendReview(new ReviewToTouristDto(tourParticipant.getReviewToTourist()));
                }
            }
        }
        LOGGER.info("[ReviewService] Guide가 Tourist에게 쓴 리뷰 조회");

        for (ReviewToGuide reviewToGuide : tourApplication.getReviewsToGuide()) {
            if (reviewToGuide.getWriteTourist().getId() == touristId) {
                reviewGuideReviewCheckDto.setReceiveReview(new ReviewToGuideDto(reviewToGuide));
            }
        }
        LOGGER.info("[ReviewService] Tourist들이 Guide에게 쓴 리뷰 조회");

        LOGGER.info("[ReviewService] Guide MyPage Reviews 조회 성공");
        return SuccessResponse.success(SuccessCode.GET_GUIDE_REVIEWS_SUCCESS, reviewGuideReviewCheckDto);
    }

    @Transactional
    public SuperResponse getTouristMyPageReviews(Long tourId, String tourDate, Long userId) {
        LOGGER.info("[ReviewService] Tourist MyPage Reviews 조회 시도");

        TourApplication tourApplication = getTourApplication(tourId, tourDate);

        LOGGER.info("[ReviewService] tourId, tourDate로 tourApplication 조회");

        List<TourParticipant> tourParticipants = tourApplication.getTourParticipants();

        ReviewTouristReviewCheckDto reviewTouristReviewCheckDto = new ReviewTouristReviewCheckDto();

        for (TourParticipant tourParticipant : tourParticipants) {
            if (tourParticipant.getParticipant().getId() == userId) {
                if (tourParticipant.getReviewToTourist() != null) {
                    reviewTouristReviewCheckDto.setReceiveReview(new ReviewToTouristDto(tourParticipant.getReviewToTourist()));
                }
            }
        }
        LOGGER.info("[ReviewService] Tourist(user)가 Guide에게 쓴 리뷰 조회");

        for (ReviewToGuide reviewToGuide : tourApplication.getReviewsToGuide()) {
            if (reviewToGuide.getWriteTourist().getId() == userId) {
                reviewTouristReviewCheckDto.setSendReview(new ReviewToGuideDto(reviewToGuide));
            }
        }
        LOGGER.info("[ReviewService] Guide가 Tourist(user)에게 쓴 리뷰 조회");

        LOGGER.info("[ReviewService] Tourist MyPage Reviews 조회 성공");

        return SuccessResponse.success(SuccessCode.GET_TOURIST_REVIEWS_SUCCESS, reviewTouristReviewCheckDto);
    }

    private User getUser(Long userId) {
        User user = userRepositoryImpl.loadUserByUserId(userId, true);
        if (user == null) {
            throw new NotFoundException("해당 유저를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_USER_EXCEPTION);
        }

        return user;
    }

    private TourApplication getTourApplication(Long tourId, String tourDate) {
        TourApplication tourApplication = tourApplicationRepositoryImpl.findTourApplicationByTourIdAndTourDate(tourId, tourDate);
        if (tourApplication == null) {
            throw new NotFoundException("해당 투어 신청서를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_TOUR_APPLICATION_EXCEPTION);
        }

        return tourApplication;
    }

    private TourParticipant getTourParticipant(Long tourId, String tourDate, Long tourParticipantId) {
        TourParticipant tourParticipant = tourParticipantRepositoryImpl
                .findTourParticipantByTourIdAndTourDateAndTourParticipantId(tourId, tourDate, tourParticipantId);
        if (tourParticipant == null) {
            throw new NotFoundException("해당 투어 신청 내용을 찾을 수 없습니다.", ErrorCode.NOT_FOUND_TOUR_PARTICIPANT_EXCEPTION);
        }

        return tourParticipant;
    }

    public boolean checkGuideReviewedAll(TourApplication tourApplication) {

        List<TourParticipant> tourParticipants = tourApplication.getTourParticipants();

        boolean check = true;

        for (TourParticipant tourParticipant : tourParticipants) {
            if (tourParticipant.getReviewToTourist() == null) {
                check = false;
            }
        }

        return check;
    }

}
