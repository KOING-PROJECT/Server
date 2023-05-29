package com.koing.server.koing_server.domain.image;

import com.koing.server.koing_server.domain.common.AuditingTimeEntity;
import com.koing.server.koing_server.domain.post.Post;
import com.koing.server.koing_server.domain.tour.Tour;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "POST_PHOTO_TABLE")
public class PostPhoto extends AuditingTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int postPhotoOrder;

    private String originName;

    @Column(unique = true)
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post ownedPost;

    public void setPost(Post post) {
        this.ownedPost = post;

        if (post.getPhotos() == null) {
            post.setPhotos(new ArrayList<>());
        }

        post.getPhotos().add(this);
    }

    public void deletePost(Post post) {
        this.ownedPost = null;

        post.getPhotos().remove(this);
    }

}
