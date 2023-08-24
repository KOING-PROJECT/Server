package com.koing.server.koing_server.domain.post.repository;

import com.koing.server.koing_server.domain.post.Post;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.koing.server.koing_server.domain.image.QPostPhoto.postPhoto;
import static com.koing.server.koing_server.domain.post.QPost.post;
import static com.koing.server.koing_server.domain.post.QComment.comment1;
import static com.koing.server.koing_server.domain.tour.QTour.tour;
import static com.koing.server.koing_server.domain.user.QUser.user;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPQLQueryFactory jpqlQueryFactory;

    @Override
    public List<Post> findAllPosts() {
        return jpqlQueryFactory
                .selectFrom(post)
                .leftJoin(post.createUser, user)
                .fetchJoin()
                .leftJoin(post.comments, comment1)
                .fetchJoin()
//                .leftJoin(post.photos, postPhoto)
//                .fetchJoin()
                .where(
                        isNotDeleted()
                )
                .distinct()
                .fetch();
    }

    @Override
    public Post findPostByPostId(Long postId) {
        return jpqlQueryFactory
                .selectFrom(post)
                .leftJoin(post.createUser, user)
                .fetchJoin()
                .leftJoin(post.comments, comment1)
                .fetchJoin()
                .where(
                        post.id.eq(postId),
                        isNotDeleted()
                )
                .distinct()
                .fetchOne();
    }

    @Override
    public boolean checkExistPostByPostId(Long postId) {
        return jpqlQueryFactory
                .selectFrom(post)
                .where(
                        post.id.eq(postId),
                        isNotDeleted()
                )
                .distinct()
                .fetchOne() != null;
    }

    @Override
    public List<Post> findPostByUserId(Long userId) {
        return jpqlQueryFactory
                .selectFrom(post)
                .leftJoin(post.createUser, user)
                .fetchJoin()
                .leftJoin(post.comments, comment1)
                .fetchJoin()
                .where(
                        post.createUser.id.eq(userId),
                        isNotDeleted()
                )
                .distinct()
                .fetch();
    }

    private BooleanExpression isNotDeleted() {
        return post.isDeleted.eq(Boolean.FALSE);
    }
}
