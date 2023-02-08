package com.koing.server.koing_server.controller.review;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.service.review.ReviewService;
import com.koing.server.koing_server.service.review.dto.ReviewToGuideCreateDto;
import com.koing.server.koing_server.service.review.dto.ReviewToTouristCreateDto;
import com.koing.server.koing_server.service.sign.dto.SignUpSetCreateDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags = "Review")
@RequestMapping("/review")
@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final Logger LOGGER = LoggerFactory.getLogger(ReviewController.class);
    private final ReviewService reviewService;

    @ApiOperation("Review - ReviewToGuide를 작성합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Review - ReviewToGuide 작성 성공"),
            @ApiResponse(code = 402, message = "ReviewToGuide 저장과정에서 오류가 발생했습니다."),
            @ApiResponse(code = 402, message = "이미지 저장 과정에서 오류가 발생했습니다."),
            @ApiResponse(code = 404, message = "해당 유저를 찾을 수 없습니다."),
            @ApiResponse(code = 404, message = "해당 투어 신청서를 찾을 수 없습니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping(value = "/guide",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public SuperResponse reviewToGuide(
            @RequestPart("reviewPhotos") List<MultipartFile> reviewPhotos,
            @RequestPart ReviewToGuideCreateDto reviewToGuideCreateDto
    ) {
        LOGGER.info("[ReviewController] ReviewToGuide 작성 시도");
        SuperResponse reviewToGuideCreateResponse;
        try {
            reviewToGuideCreateResponse = reviewService.reviewToGuide(reviewToGuideCreateDto, reviewPhotos);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println(exception);
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[ReviewController] ReviewToGuide 작성 성공");

        return reviewToGuideCreateResponse;
    }


    @ApiOperation("Review - ReviewToTourist를 작성합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Review - ReviewToTourist 작성 성공"),
            @ApiResponse(code = 402, message = "ReviewToTourist 저장과정에서 오류가 발생했습니다."),
            @ApiResponse(code = 404, message = "해당 유저를 찾을 수 없습니다."),
            @ApiResponse(code = 404, message = "해당 투어 신청 내용을 찾을 수 없습니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
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

}
