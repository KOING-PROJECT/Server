package com.koing.server.koing_server.domain.image.repository;

import com.koing.server.koing_server.domain.image.PostPhoto;
import com.koing.server.koing_server.domain.image.Thumbnail;

public interface PostPhotoRepositoryCustom {

    PostPhoto findPostPhotoByFilePath(String filePath);

}
