package com.koing.server.koing_server.service.post;

import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.DBFailException;
import com.koing.server.koing_server.common.exception.NotFoundException;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.domain.post.Comment;
import com.koing.server.koing_server.domain.post.Post;
import com.koing.server.koing_server.domain.post.repository.CommentRepository;
import com.koing.server.koing_server.domain.post.repository.PostRepositoryImpl;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.domain.user.repository.UserRepository;
import com.koing.server.koing_server.domain.user.repository.UserRepositoryImpl;
import com.koing.server.koing_server.service.post.dto.comment.CommentCreateDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final Logger LOGGER = LoggerFactory.getLogger(PostService.class);
    private final UserRepositoryImpl userRepositoryImpl;
    private final CommentRepository commentRepository;
    private final PostRepositoryImpl postRepositoryImpl;

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

        return SuccessResponse.success(SuccessCode.COMMENT_CREATE_SUCCESS, null);
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
