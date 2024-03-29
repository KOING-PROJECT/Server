package com.koing.server.koing_server.domain.post.repository;

import com.koing.server.koing_server.domain.post.Post;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> findAllPosts();

    List<Post> findAdminPosts();

    Post findPostByPostId(Long postId);

    boolean checkExistPostByPostId(Long postId);

    List<Post> findPostByUserId(Long userId);

}
