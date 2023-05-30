package com.koing.server.koing_server.domain.post;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.koing.server.koing_server.domain.common.AuditingTimeEntity;
import com.koing.server.koing_server.domain.image.PostPhoto;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.service.post.dto.post.PostCreateDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "POST_TABLE")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Post extends AuditingTimeEntity {

    public Post(PostCreateDto postCreateDto) {
        this.createUser = null;
        this.content = postCreateDto.getContent();
        this.tags = postCreateDto.getTags();
        this.photos = new ArrayList<>();
        this.likeCount = 0;
        this.commentCount = 0;
        this.comments = new ArrayList<>();
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

    private int likeCount;

    private int commentCount;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Comment> comments;

    public void setCreateUser(User user) {
        this.createUser = user;

        if (user.getCreatePosts() == null) {
            user.setCreatePosts(new ArrayList<>());
        }

        user.getCreatePosts().add(this);
    }

    public void deleteCreateUser(User user) {
        this.createUser = null;

        user.getCreatePosts().remove(this);
    }

}
