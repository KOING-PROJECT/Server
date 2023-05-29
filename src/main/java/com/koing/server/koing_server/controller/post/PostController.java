package com.koing.server.koing_server.controller.post;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.controller.review.ReviewController;
import com.koing.server.koing_server.service.post.PostService;
import com.koing.server.koing_server.service.post.dto.PostCreateDto;
import com.koing.server.koing_server.service.review.ReviewService;
import com.koing.server.koing_server.service.review.dto.ReviewToGuideCreateDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags = "Post")
@RequestMapping("/post")
@RestController
@RequiredArgsConstructor
public class PostController {

    private final Logger LOGGER = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;

    @ApiOperation("Post - Post를 작성합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Post - Post 작성 성공"),
            @ApiResponse(code = 402, message = "Post 저장과정에서 오류가 발생했습니다."),
            @ApiResponse(code = 402, message = "게시글 사진 저장 과정에서 오류가 발생했습니다."),
            @ApiResponse(code = 404, message = "해당 유저를 찾을 수 없습니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping(value = "",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public SuperResponse reviewToGuide(
            @RequestPart(value = "postPhotos", required = false) List<MultipartFile> postPhotos,
            @RequestPart PostCreateDto postCreateDto
    ) {
        LOGGER.info("[PostController] Post 작성 시도");
        SuperResponse postCreateResponse;
        try {
            postCreateResponse = postService.createPost(postCreateDto, postPhotos);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            System.out.println(exception);
            System.out.println(exception.getMessage());
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[PostController] Post 작성 성공");

        return postCreateResponse;
    }

}
