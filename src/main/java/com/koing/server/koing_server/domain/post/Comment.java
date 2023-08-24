package com.koing.server.koing_server.domain.post;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.koing.server.koing_server.domain.common.AbstractRootEntity;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.service.post.dto.comment.CommentCreateDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "COMMENT_TABLE")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Comment extends AbstractRootEntity {

    public Comment(CommentCreateDto commentCreateDto) {
        this.commendUser = null;
        this.comment = commentCreateDto.getComment();
        this.commendedPost = null;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User commendUser;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post commendedPost;

    public void setCreateUser(User user) {
        this.commendUser = user;

        if (user.getCreateComments() == null) {
            user.setCreateComments(new HashSet<>());
        }

        user.getCreateComments().add(this);
    }

    public void deleteCreateUser(User user) {
        this.commendUser = null;

        user.getCreateComments().remove(this);
    }

    public void setCommendedPost(Post post) {
        this.commendedPost = post;

        if (post.getComments() == null) {
            post.setComments(new HashSet<>());
        }

        post.getComments().add(this);
    }

    public void deleteCommendedPost(Post post) {
        this.commendedPost = null;

        post.getComments().remove(this);
    }

}
