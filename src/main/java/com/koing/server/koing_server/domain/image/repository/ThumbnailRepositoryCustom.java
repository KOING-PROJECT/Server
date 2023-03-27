package com.koing.server.koing_server.domain.image.repository;

import com.koing.server.koing_server.domain.image.Thumbnail;

import java.util.List;

public interface ThumbnailRepositoryCustom {

    List<Thumbnail> findThumbnailByTourId(Long tourId);

    Thumbnail findThumbnailByFilePath(String filePath);

}
