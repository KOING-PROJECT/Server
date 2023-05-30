package com.koing.server.koing_server.service.post.dto.post;

import com.koing.server.koing_server.domain.image.PostPhoto;
import com.koing.server.koing_server.domain.post.Comment;
import com.koing.server.koing_server.domain.post.Post;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class PostResponseDto {

    public PostResponseDto(Post post) {
        this.postId = post.getId();
        if (post.getCreateUser().getUserOptionalInfo() != null) {
            if (post.getCreateUser().getUserOptionalInfo().getImageUrls() != null && post.getCreateUser().getUserOptionalInfo().getImageUrls().size() > 0) {
                this.writeUserImage = post.getCreateUser().getUserOptionalInfo().getImageUrls().get(0);
            }
        }
        this.writeUserName = post.getCreateUser().getName();
        this.createdDate = createdAtFormatting(post.getCreatedAt());
        this.postPhotos = getPostPhotoUrls(post);
        this.postContent = post.getContent();
        this.likeCount = post.getLikeCount();
        this.commentCount = post.getCommentCount();
    }

    private Long postId;
    private String writeUserImage;
    private String writeUserName;
    private String createdDate;
    private List<String> postPhotos;
    private String postContent;
    private int likeCount;
    private int commentCount;

    private String createdAtFormatting(LocalDateTime createdAt) {
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }

    private List<String> getPostPhotoUrls(Post post) {
        List<PostPhoto> postPhotos = post.getPhotos()
                .stream()
                .sorted(Comparator.comparing((PostPhoto p) -> p.getPostPhotoOrder()))
                .collect(Collectors.toList());

        List<String> postPhotoUrls = new ArrayList<>();

        for (PostPhoto postPhoto : postPhotos) {
            postPhotoUrls.add(postPhoto.getFilePath());
        }

        return postPhotoUrls;
    }

}
