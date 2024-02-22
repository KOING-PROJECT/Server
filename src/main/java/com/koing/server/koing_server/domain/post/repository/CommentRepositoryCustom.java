package com.koing.server.koing_server.domain.post.repository;

import com.koing.server.koing_server.domain.post.Comment;

import java.util.List;

public interface CommentRepositoryCustom {

    List<Comment> findCommentByPostId(Long postId);

    Comment findCommentById(Long commentId);
}
