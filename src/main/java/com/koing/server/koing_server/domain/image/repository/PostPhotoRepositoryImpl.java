package com.koing.server.koing_server.domain.image.repository;

import com.koing.server.koing_server.domain.image.PostPhoto;
import com.koing.server.koing_server.domain.image.Thumbnail;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.koing.server.koing_server.domain.image.QPostPhoto.postPhoto;


@RequiredArgsConstructor
public class PostPhotoRepositoryImpl implements PostPhotoRepositoryCustom {

    private final JPQLQueryFactory jpqlQueryFactory;
    @Override
    public PostPhoto findPostPhotoByFilePath(String filePath) {
        return jpqlQueryFactory
                .selectFrom(postPhoto)
                .where(
                        postPhoto.filePath.eq(filePath)
                )
                .fetchOne();
    }

}
