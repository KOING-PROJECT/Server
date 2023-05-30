package com.koing.server.koing_server.domain.post;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.koing.server.koing_server.domain.common.AuditingTimeEntity;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.service.post.dto.comment.CommentCreateDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "COMMENT_TABLE")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Comment extends AuditingTimeEntity {

    public Comment(CommentCreateDto commentCreateDto) {
        this.createUser = null;
        this.comment = commentCreateDto.getComment();
        this.commenededPost = null;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User createUser;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post commenededPost;

    public void setCreateUser(User user) {
        this.createUser = user;

        if (user.getCreateComments() == null) {
            user.setCreateComments(new ArrayList<>());
        }

        user.getCreateComments().add(this);
    }

    public void deleteCreateUser(User user) {
        this.createUser = null;

        user.getCreateComments().remove(this);
    }

    public void setCommendedPost(Post post) {
        this.commenededPost = post;

        if (post.getComments() == null) {
            post.setComments(new ArrayList<>());
        }

        post.getComments().add(this);
    }

    public void deleteCommendedPost(Post post) {
        this.commenededPost = null;

        post.getComments().remove(this);
    }

}
