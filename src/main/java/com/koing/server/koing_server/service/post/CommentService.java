package com.koing.server.koing_server.service.post;

import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.DBFailException;
import com.koing.server.koing_server.common.exception.NotAcceptableException;
import com.koing.server.koing_server.common.exception.NotFoundException;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.domain.post.Comment;
import com.koing.server.koing_server.domain.post.Post;
import com.koing.server.koing_server.domain.post.repository.CommentRepository;
import com.koing.server.koing_server.domain.post.repository.CommentRepositoryImpl;
import com.koing.server.koing_server.domain.post.repository.PostRepositoryImpl;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.domain.user.repository.UserRepository;
import com.koing.server.koing_server.domain.user.repository.UserRepositoryImpl;
import com.koing.server.koing_server.service.post.dto.comment.CommentCreateDto;
import com.koing.server.koing_server.service.post.dto.comment.CommentListResponseDto;
import com.koing.server.koing_server.service.post.dto.comment.CommentResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final Logger LOGGER = LoggerFactory.getLogger(PostService.class);
    private final UserRepositoryImpl userRepositoryImpl;
    private final CommentRepository commentRepository;
    private final CommentRepositoryImpl commentRepositoryImpl;
    private final PostRepositoryImpl postRepositoryImpl;

    @Transactional
    public SuperResponse createComment(CommentCreateDto commentCreateDto) {

        LOGGER.info("[CommentService] Comment 생성 시도");

        Comment comment = new Comment(commentCreateDto);

        User createUser = getUser(commentCreateDto.getWriteUserId());

        comment.setCreateUser(createUser);

        Post commendedPost = getPost(commentCreateDto.getPostId());

        comment.setCommendedPost(commendedPost);

        Comment savedComment = commentRepository.save(comment);

        if (savedComment == null) {
            throw new DBFailException("댓글 저장 과정에서 오류가 발생했습니다.", ErrorCode.DB_FAIL_CREATE_COMMENT_EXCEPTION);
        }

        LOGGER.info("[CommentService] Comment 생성 성공");

        return SuccessResponse.success(SuccessCode.COMMENT_CREATE_SUCCESS, null);
    }

    @Transactional
    public SuperResponse getComments(Long postId) {

        LOGGER.info("[CommentService] PostId로 comment 리스트 조회 시도");

        if (!postRepositoryImpl.checkExistPostByPostId(postId)) {
            throw new NotFoundException("해당 게시글을 찾을 수 없습니다.", ErrorCode.NOT_FOUND_POST_EXCEPTION);
        }

        List<Comment> comments = commentRepositoryImpl.findCommentByPostId(postId);

        List<Comment> sortedByCreatedAtComments = comments.stream().sorted((c1, c2) -> c1.getCreatedAt().compareTo(c2.getCreatedAt())).collect(Collectors.toList());

        List<CommentResponseDto> commentResponseDtos = new ArrayList<>();

        for (Comment comment : sortedByCreatedAtComments) {
            commentResponseDtos.add(new CommentResponseDto(comment));
        }

        LOGGER.info("[CommentService] PostId로 comment 리스트 조회 성공");

        return SuccessResponse.success(SuccessCode.GET_COMMENTS_SUCCESS, new CommentListResponseDto(commentResponseDtos));
    }

    public SuperResponse deleteComment(final Long userId, final Long commentId) {
        LOGGER.info("[CommentService] comment 삭제 시도");
        final Comment comment = commentRepositoryImpl.findCommentById(commentId);

        if (comment == null) {
            throw new NotFoundException("해당 댓글을 찾을 수 없습니다.", ErrorCode.NOT_FOUND_COMMENT_EXCEPTION);
        }

        if (comment.getCommendUser().getId() != userId) {
            throw new NotAcceptableException("댓글을 단 유저가 아닙니다.", ErrorCode.NOT_ACCEPTABLE_NOT_COMMENTED_USER_EXCEPTION);
        }

        comment.delete();
        LOGGER.info("[CommentService] comment 삭제 성공");

        return SuccessResponse.success(SuccessCode.DELETE_COMMENT_SUCCESS, null);
    }

    private User getUser(Long userId) {
        User user = userRepositoryImpl.loadUserByUserId(userId, true);
        if (user == null) {
            throw new NotFoundException("해당 유저를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_USER_EXCEPTION);
        }

        return user;
    }

    private Post getPost(Long postId) {
        Post post = postRepositoryImpl.findPostByPostId(postId);

        if (post == null) {
            throw new NotFoundException("해당 게시글을 찾을 수 없습니다.", ErrorCode.NOT_FOUND_POST_EXCEPTION);
        }

        return post;
    }
}
