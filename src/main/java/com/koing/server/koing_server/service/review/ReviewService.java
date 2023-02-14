package com.koing.server.koing_server.service.review;

import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
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
import com.koing.server.koing_server.domain.tour.repository.TourParticipant.TourParticipantRepositoryImpl;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.domain.user.repository.UserRepositoryImpl;
import com.koing.server.koing_server.service.review.dto.ReviewToGuideCreateDto;
import com.koing.server.koing_server.service.review.dto.ReviewToGuideRequestDto;
import com.koing.server.koing_server.service.review.dto.ReviewToTouristCreateDto;
import com.koing.server.koing_server.service.s3.component.AWSS3Component;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
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
    private final AWSS3Component awss3Component;

    public SuperResponse reviewToGuide(ReviewToGuideCreateDto reviewToGuideCreateDto, List<MultipartFile> multipartFiles) {
        LOGGER.info("[ReviewService] reviewToGuide 작성 시도");

        User writeUser = getUser(reviewToGuideCreateDto.getWriteTouristId());
        LOGGER.info("[ReviewService] writeTouristId로 작성자 조회 성공");

        TourApplication tourApplication = getTourApplication(reviewToGuideCreateDto.getTourId(), reviewToGuideCreateDto.getTourDate());
        LOGGER.info("[ReviewService] tourId와 tourDate로 tourApplication 조회 성공");

        List<String> reviewPhotos = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            try {
                reviewPhotos.add(awss3Component.convertAndUploadFiles(multipartFile, "review/image"));
            } catch (IOException ioException) {
                throw new IOFailException("이미지 저장 과정에서 오류가 발생했습니다.", ErrorCode.DB_FAIL_UPLOAD_IMAGE_FAIL_EXCEPTION);
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

        TourParticipant tourParticipant = getTourParticipant(reviewToTouristCreateDto.getTourParticipantId());
        LOGGER.info("[ReviewService] tourParticipantId로 투어 신청 내용 조회 성공");

        ReviewToTourist reviewToTourist = new ReviewToTourist(reviewToTouristCreateDto, writeGuide, tourParticipant);
        LOGGER.info("[ReviewService] ReviewToTourist 생성");

        ReviewToTourist savedReviewToTourist = reviewToTouristRepository.save(reviewToTourist);

        if (savedReviewToTourist == null) {
            throw new DBFailException("ReviewToTourist 저장과정에서 오류가 발생했습니다.", ErrorCode.DB_FAIL_CREATE_REVIEW_TO_TOURIST_FAIL_EXCEPTION);
        }
        LOGGER.info("[ReviewService] ReviewToTourist 저장 완료");

        return SuccessResponse.success(SuccessCode.REVIEW_TO_TOURIST_CREATE_SUCCESS, null);
    }

//    public SuperResponse getReviewToGuide(ReviewToGuideRequestDto reviewToGuideRequestDto) {
//        LOGGER.info("[ReviewService] ReviewToGuide 조회 시도");
//
//        TourApplication tourApplication = getTourApplication(reviewToGuideRequestDto.getTourId(), reviewToGuideRequestDto.getTourDate());
//        LOGGER.info("[ReviewService] tourId와 tourDate로 tourApplication 조회 성공");
//
//        List<TourParticipant> tourParticipants = tourApplication.getTourParticipants();
//        LOGGER.info("[ReviewService] 조회된 tourApplication의 tourParticipant 조회");
//
//
//
//
//    }

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
            throw new NotFoundException("해당 투어 신청서를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_USER_EXCEPTION);
        }

        return tourApplication;
    }

    private TourParticipant getTourParticipant(Long tourParticipantId) {
        TourParticipant tourParticipant = tourParticipantRepositoryImpl.findTourParticipantByTourParticipantId(tourParticipantId);
        if (tourParticipant == null) {
            throw new NotFoundException("해당 투어 신청 내용을 찾을 수 없습니다.", ErrorCode.NOT_FOUND_TOUR_PARTICIPANT_EXCEPTION);
        }

        return tourParticipant;
    }

}
