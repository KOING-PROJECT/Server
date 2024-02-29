package com.koing.server.koing_server.controller.post;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.service.post.PostService;
import com.koing.server.koing_server.service.post.dto.post.PostCreateDto;
import com.koing.server.koing_server.service.post.dto.post.PostLikeRequestDto;
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

@Tag(name = "Post", description = "Post API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final Logger LOGGER = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;

    @Operation(description = "Post - Post를 작성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Post - Post 작성 성공"),
            @ApiResponse(responseCode = "402", description = "Post 저장과정에서 오류가 발생했습니다."),
            @ApiResponse(responseCode = "402", description = "게시글 사진 저장 과정에서 오류가 발생했습니다."),
            @ApiResponse(responseCode = "404", description = "해당 유저를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
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

    @Operation(description = "Post - Post 리스트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post - Post 리스트 조회 성공"),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
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

    @Operation(description = "Post - Post 좋아요를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Post - Post 좋아요 수정 성공"),
            @ApiResponse(responseCode = "404", description = "해당 유저를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 게시글을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
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

    @Operation(description = "Post - 생성한 post 리스트를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post - 생성한 post 리스트 조회 성공"),
            @ApiResponse(responseCode = "404", description = "해당 유저를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
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

    @Operation(description = "Post - 좋아요 누른 post 리스트를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post - 좋아요 누른 post 리스트 조회 성공"),
            @ApiResponse(responseCode = "404", description = "해당 유저를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
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

    @Operation(description = "Post - Post를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post - Post 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "해당 유저를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
    })
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

    @Operation(description = "Post - Admin Post 리스트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post - Admin Post 리스트 조회 성공"),
            @ApiResponse(responseCode = "404", description = "해당 유저를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @GetMapping("/admin/{userId}")
    public SuperResponse getAdminPosts(@PathVariable("userId") Long userId) {
        LOGGER.info("[PostController] Admin Post 리스트 조회 시도");
        SuperResponse getAdminPostsResponse;
        try {
            getAdminPostsResponse = postService.getAdminPosts(userId);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[PostController] Admin Post 리스트 조회 성공");

        return getAdminPostsResponse;
    }
}
