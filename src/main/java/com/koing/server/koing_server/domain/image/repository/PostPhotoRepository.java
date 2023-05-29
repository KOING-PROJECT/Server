package com.koing.server.koing_server.domain.image.repository;

import com.koing.server.koing_server.domain.image.PostPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostPhotoRepository extends JpaRepository<PostPhoto, Long> {
}
