package com.koing.server.koing_server.domain.image.repository;

import com.koing.server.koing_server.domain.image.Thumbnail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThumbnailRepository extends JpaRepository<Thumbnail, Long> {
}
