package com.koing.server.koing_server.domain.post.repository;

import com.koing.server.koing_server.domain.post.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
