package com.koing.server.koing_server.controller.post;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.service.post.CommentService;
import com.koing.server.koing_server.service.post.PostService;
import com.koing.server.koing_server.service.post.dto.comment.CommentCreateDto;
import com.koing.server.koing_server.service.post.dto.post.PostCreateDto;
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
@RequestMapping("/comment")
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final Logger LOGGER = LoggerFactory.getLogger(PostController.class);
    private final CommentService commentService;

    @ApiOperation("Comment - comment를 작성합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Comment - comment 작성 성공"),
            @ApiResponse(code = 402, message = "Comment 저장과정에서 오류가 발생했습니다."),
            @ApiResponse(code = 404, message = "해당 유저를 찾을 수 없습니다."),
            @ApiResponse(code = 404, message = "해당 게시글을 찾을 수 없습니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping("")
    public SuperResponse createComment(@RequestBody CommentCreateDto commentCreateDto) {
        LOGGER.info("[CommentController] Comment 작성 시도");
        SuperResponse commentCreateResponse;
        try {
            commentCreateResponse = commentService.createComment(commentCreateDto);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            System.out.println(exception);
            System.out.println(exception.getMessage());
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[CommentController] Comment 작성 성공");

        return commentCreateResponse;
    }

    @ApiOperation("Comment - comment 리스트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Comment - comment 리스트 조회 성공"),
            @ApiResponse(code = 404, message = "해당 게시글을 찾을 수 없습니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @GetMapping("/{postId}")
    public SuperResponse getComments(@PathVariable("postId") Long postId) {
        LOGGER.info("[CommentController] Comment 리스트 조회 시도");
        SuperResponse commentGetResponse;
        try {
            commentGetResponse = commentService.getComments(postId);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            System.out.println(exception);
            System.out.println(exception.getMessage());
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[CommentController] Comment 리스트 조회 성공");

        return commentGetResponse;
    }

    @DeleteMapping("/{userId}/{commentId}")
    public SuperResponse deleteComment(
            @PathVariable("userId") Long userId,
            @PathVariable("commentId") Long commendId
    ) {
        LOGGER.info("[CommentController] Comment 삭제 시도");
        SuperResponse deleteCommentResponse;
        try {
            deleteCommentResponse = commentService.deleteComment(userId, commendId);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[CommentController] Comment 삭제 성공");

        return deleteCommentResponse;
    }
}
