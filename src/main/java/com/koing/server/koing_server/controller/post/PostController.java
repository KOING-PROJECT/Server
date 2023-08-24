package com.koing.server.koing_server.controller.post;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.service.post.PostService;
import com.koing.server.koing_server.service.post.dto.post.PostCreateDto;
import com.koing.server.koing_server.service.post.dto.post.PostLikeRequestDto;
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
    public SuperResponse createPost(
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
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[PostController] Post 작성 성공");

        return postCreateResponse;
    }

    @ApiOperation("Post - Post 리스트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Post - Post 리스트 조회 성공"),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @GetMapping("/{userId}")
    public SuperResponse getPosts(@PathVariable("userId") Long userId) {
        LOGGER.info("[PostController] Post 리스트 조회 시도");
        SuperResponse postsGetResponse;
        try {
            postsGetResponse = postService.getPosts(userId);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[PostController] Post 리스트 조회 성공");

        return postsGetResponse;
    }

    @ApiOperation("Post - Post 좋아요를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Post - Post 좋아요 수정 성공"),
            @ApiResponse(code = 404, message = "해당 유저를 찾을 수 없습니다."),
            @ApiResponse(code = 404, message = "해당 게시글을 찾을 수 없습니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PatchMapping("/like")
    public SuperResponse pressLikePost(@RequestBody PostLikeRequestDto postLikeRequestDto) {
        LOGGER.info("[PostController] Post 좋아요 수정 시도");
        SuperResponse postPressLikeResponse;
        try {
            postPressLikeResponse = postService.pressLikePost(postLikeRequestDto);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[PostController] Post 좋아요 수정 성공");

        return postPressLikeResponse;
    }

    @ApiOperation("Post - 생성한 post 리스트를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Post - 생성한 post 리스트 조회 성공"),
            @ApiResponse(code = 404, message = "해당 유저를 찾을 수 없습니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @GetMapping("/create/{userId}")
    public SuperResponse getCreatedPosts(@PathVariable("userId") Long userId) {
        LOGGER.info("[PostController] 생성한 post 리스트 조회 시도");
        SuperResponse getMyPostResponse;
        try {
            getMyPostResponse = postService.getMyPosts(userId);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[PostController] 생성한 post 리스트 조회 성공");

        return getMyPostResponse;
    }

    @ApiOperation("Post - 좋아요 누른 post 리스트를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Post - 좋아요 누른 post 리스트 조회 성공"),
            @ApiResponse(code = 404, message = "해당 유저를 찾을 수 없습니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @GetMapping("/like/{userId}")
    public SuperResponse getLikedPosts(@PathVariable("userId") Long userId) {
        LOGGER.info("[PostController] 좋아요 누른 post 리스트 조회 시도");
        SuperResponse getLikedPostResponse;
        try {
            getLikedPostResponse = postService.getLikePosts(userId);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[PostController] 좋아요 누른 post 리스트 조회 성공");

        return getLikedPostResponse;
    }

    @DeleteMapping("/{userId}/{postId}")
    public SuperResponse deletePost(
            @PathVariable("userId") Long userId,
            @PathVariable("postId") Long postId
    ) {
        LOGGER.info("[PostController] post 삭제 시도");
        SuperResponse deletePostResponse;
        try {
            deletePostResponse = postService.deletePost(userId, postId);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[PostController] post 삭제 성공");

        return deletePostResponse;
    }
}
