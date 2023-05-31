package com.koing.server.koing_server.domain.post.repository;

import com.koing.server.koing_server.domain.post.Comment;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.koing.server.koing_server.domain.post.QComment.comment1;
import static com.koing.server.koing_server.domain.post.QPost.post;
import static com.koing.server.koing_server.domain.user.QUser.user;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPQLQueryFactory jpqlQueryFactory;

    @Override
    public List<Comment> findCommentByPostId(Long postId) {
        return jpqlQueryFactory
                .selectFrom(comment1)
                .leftJoin(comment1.commendedPost, post)
                .fetchJoin()
                .leftJoin(comment1.commendUser, user)
                .fetchJoin()
                .distinct()
                .where(
                        comment1.commendedPost.id.eq(postId)
                )
                .fetch();
    }
}
