package com.koing.server.koing_server.domain.post;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.koing.server.koing_server.domain.common.AuditingTimeEntity;
import com.koing.server.koing_server.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "POST_TABLE")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Post extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User createUser;

    private String description;

    @ElementCollection(fetch = FetchType.LAZY)
    @Column(name = "tags")
    private List<String> tags;

    @ElementCollection(fetch = FetchType.LAZY)
    @Column(name = "photos")
    private List<String> photos;

    private int likeCount;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Comment> comments;

}
