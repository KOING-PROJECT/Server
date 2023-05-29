package com.koing.server.koing_server.domain.post.repository;

import com.koing.server.koing_server.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
