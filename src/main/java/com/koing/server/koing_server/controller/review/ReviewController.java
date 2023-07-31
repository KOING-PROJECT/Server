package com.koing.server.koing_server.controller.review;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.service.review.ReviewService;
import com.koing.server.koing_server.service.review.dto.ReviewToGuideCreateDto;
import com.koing.server.koing_server.service.review.dto.ReviewToTouristCreateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Review", description = "Review API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final Logger LOGGER = LoggerFactory.getLogger(ReviewController.class);
    private final ReviewService reviewService;

    @Operation(description = "Review - ReviewToGuide를 작성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Review - ReviewToGuide 작성 성공"),
            @ApiResponse(responseCode = "402", description = "ReviewToGuide 저장과정에서 오류가 발생했습니다."),
            @ApiResponse(responseCode = "402", description = "이미지 저장 과정에서 오류가 발생했습니다."),
            @ApiResponse(responseCode = "404", description = "해당 유저를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 투어 신청서를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping(value = "/guide",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public SuperResponse reviewToGuide(
            @RequestPart(value = "reviewPhotos", required = false) List<MultipartFile> reviewPhotos,
            @RequestPart ReviewToGuideCreateDto reviewToGuideCreateDto
    ) {
        LOGGER.info("[ReviewController] ReviewToGuide 작성 시도");
        SuperResponse reviewToGuideCreateResponse;
        try {
            reviewToGuideCreateResponse = reviewService.reviewToGuide(reviewToGuideCreateDto, reviewPhotos);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[ReviewController] ReviewToGuide 작성 성공");

        return reviewToGuideCreateResponse;
    }


    @Operation(description = "Review - ReviewToTourist를 작성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Review - ReviewToTourist 작성 성공"),
            @ApiResponse(responseCode = "402", description = "ReviewToTourist 저장과정에서 오류가 발생했습니다."),
            @ApiResponse(responseCode = "402", description = "투어 신청 내용 업데이트 과정에서 오류가 발생했습니다."),
            @ApiResponse(responseCode = "404", description = "해당 유저를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 투어 신청 내용을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping("/tourist")
    public SuperResponse reviewToTourist(@RequestBody ReviewToTouristCreateDto reviewToTouristCreateDto) {
        LOGGER.info("[ReviewController] ReviewToTourist 작성 시도");
        SuperResponse reviewToTouristCreateResponse;
        try {
            reviewToTouristCreateResponse = reviewService.reviewToTourist(reviewToTouristCreateDto);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[ReviewController] ReviewToTourist 작성 성공");

        return reviewToTouristCreateResponse;
    }


    @Operation(description = "Review - Guide가 리뷰를 작성할 투어리스트를 조회합니다.(날짜 형식을 20221225 이런 식으로 줘야합니다)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Review - Guide가 리뷰를 작성할 투어리스트를 조회 성공"),
            @ApiResponse(responseCode = "404", description = "해당 투어 신청 내용을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @GetMapping("/guide/{tourId}/{tourDate}")
    public SuperResponse getGuideMyPageReviewList(
            @PathVariable("tourId") Long tourId,
            @PathVariable("tourDate") String tourDate
    ) {
        LOGGER.info("[ReviewController] Guide가 리뷰를 작성할 투어리스트를 조회 시도");
        SuperResponse getGuideMyPageReviewListResponse;
        try {
            getGuideMyPageReviewListResponse = reviewService.getGuideMyPageReviewList(tourId, tourDate);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            System.out.println(exception);
            System.out.println(exception.getMessage());
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[ReviewController] Guide가 리뷰를 작성할 투어리스트를 조회 성공");

        return getGuideMyPageReviewListResponse;
    }


    @Operation(description = "Review - Guide가 보낸 리뷰, 받은 리뷰를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Review - Guide가 보낸 리뷰, 받은 리뷰를 조회 성공"),
            @ApiResponse(responseCode = "404", description = "해당 투어 신청 내용을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @GetMapping("/guide/{tourId}/{tourDate}/{touristId}")
    public SuperResponse getGuideMyPageReviews(
            @PathVariable("tourId") Long tourId,
            @PathVariable("tourDate") String tourDate,
            @PathVariable("touristId") Long touristId
    ) {
        LOGGER.info("[ReviewController] Guide가 보낸 리뷰, 받은 리뷰 조회 시도");
        SuperResponse getGuideMyPageReviewsResponse;
        try {
            getGuideMyPageReviewsResponse = reviewService.getGuideMyPageReviews(tourId, tourDate, touristId);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            System.out.println(exception);
            System.out.println(exception.getMessage());
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[ReviewController] Guide가 보낸 리뷰, 받은 리뷰 조회 성공");

        return getGuideMyPageReviewsResponse;
    }


    @Operation(description = "Review - Tourist가 보낸 리뷰, 받은 리뷰를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Review - Tourist가 보낸 리뷰, 받은 리뷰를 조회 성공"),
            @ApiResponse(responseCode = "404", description = "해당 투어 신청 내용을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @GetMapping("/tourist/{tourId}/{tourDate}/{userId}")
    public SuperResponse getTouristMyPageReviews(
            @PathVariable("tourId") Long tourId,
            @PathVariable("tourDate") String tourDate,
            @PathVariable("userId") Long userId
    ) {
        LOGGER.info("[ReviewController] Tourist가 보낸 리뷰, 받은 리뷰 조회 시도");
        SuperResponse getTouristMyPageReviewsResponse;
        try {
            getTouristMyPageReviewsResponse = reviewService.getTouristMyPageReviews(tourId, tourDate, userId);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[ReviewController] Tourist가 보낸 리뷰, 받은 리뷰 조회 성공");

        return getTouristMyPageReviewsResponse;
    }

}
