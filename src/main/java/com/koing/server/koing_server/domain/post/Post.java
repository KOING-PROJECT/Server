package com.koing.server.koing_server.domain.post;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.koing.server.koing_server.domain.common.AbstractRootEntity;
import com.koing.server.koing_server.domain.image.PostPhoto;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.service.post.dto.post.PostCreateDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "POST_TABLE")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Post extends AbstractRootEntity {

    public Post(PostCreateDto postCreateDto) {
        this.createUser = null;
        this.content = postCreateDto.getContent();
        this.tags = postCreateDto.getTags();
        this.photos = new ArrayList<>();
        this.comments = new HashSet<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User createUser;

    private String content;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "tags")
    private List<String> tags;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ownedPost")
    private List<PostPhoto> photos;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<User> likedUsers;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "commendedPost")
    private Set<Comment> comments;

    public void setCreateUser(User user) {
        this.createUser = user;

        if (user.getCreatePosts() == null) {
            user.setCreatePosts(new HashSet<>());
        }

        user.getCreatePosts().add(this);
    }

    public void deleteCreateUser(User user) {
        this.createUser = null;

        user.getCreatePosts().remove(this);
    }

    public void addLikedUser(User user) {
        if (this.likedUsers == null) {
            this.likedUsers = new HashSet<>();
        }

        if (!this.likedUsers.contains(user)) {
            this.likedUsers.add(user);
        }

        if (user.getLikePosts() == null) {
            user.setLikePosts(new HashSet<>());
        }

        if (!user.getLikePosts().contains(this)) {
            user.getLikePosts().add(this);
        }
    }

    public void deleteLikedUser(User user) {
        if (this.likedUsers.contains(user)) {
            this.likedUsers.remove(user);
        }

        if (user.getLikePosts().contains(this)) {
            user.getLikePosts().remove(this);
        }
    }

}
