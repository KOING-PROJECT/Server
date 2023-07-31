package com.koing.server.koing_server.controller.post;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.service.post.CommentService;
import com.koing.server.koing_server.service.post.dto.comment.CommentCreateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Post", description = "Post API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final Logger LOGGER = LoggerFactory.getLogger(PostController.class);
    private final CommentService commentService;

    @Operation(description = "Comment - comment를 작성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment - comment 작성 성공"),
            @ApiResponse(responseCode = "402", description = "Comment 저장과정에서 오류가 발생했습니다."),
            @ApiResponse(responseCode = "404", description = "해당 유저를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "404", description = "해당 게시글을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
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

    @Operation(description = "Comment - comment 리스트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment - comment 리스트 조회 성공"),
            @ApiResponse(responseCode = "404", description = "해당 게시글을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
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

}
