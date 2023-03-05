package com.koing.server.koing_server.domain.image.repository;

import com.koing.server.koing_server.domain.image.Thumbnail;

public interface ThumbnailRepositoryCustom {

    Thumbnail findThumbnailByFilePath(String filePath);

}
