package com.koing.server.koing_server.domain.image.repository;

import com.koing.server.koing_server.domain.image.PostPhoto;
import com.koing.server.koing_server.domain.image.Thumbnail;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.koing.server.koing_server.domain.image.QPostPhoto.postPhoto;
import static com.koing.server.koing_server.domain.post.QPost.post;


@RequiredArgsConstructor
public class PostPhotoRepositoryImpl implements PostPhotoRepositoryCustom {

    private final JPQLQueryFactory jpqlQueryFactory;
    @Override
    public PostPhoto findPostPhotoByFilePath(String filePath) {
        return jpqlQueryFactory
                .selectFrom(postPhoto)
                .leftJoin(postPhoto.ownedPost, post)
                .fetchJoin()
                .distinct()
                .where(
                        postPhoto.filePath.eq(filePath)
                )
                .fetchOne();
    }

}
