package com.koing.server.koing_server.service.post.dto.post;

import com.koing.server.koing_server.common.enums.UserRole;
import com.koing.server.koing_server.domain.image.PostPhoto;
import com.koing.server.koing_server.domain.post.Comment;
import com.koing.server.koing_server.domain.post.Post;
import com.koing.server.koing_server.domain.user.User;
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

    public PostResponseDto(Post post, User loginUser) {
        this.postId = post.getId();

        if (post.getCreateUser().getUserOptionalInfo() != null) {
            if (post.getCreateUser().getUserOptionalInfo().getImageUrls() != null && post.getCreateUser().getUserOptionalInfo().getImageUrls().size() > 0) {
                this.writeUserImage = post.getCreateUser().getUserOptionalInfo().getImageUrls().get(0);
            }
        }

        getUserRoleAndUserGrade(post.getCreateUser());
        this.writeUserName = post.getCreateUser().getName();
        this.createdDate = createdAtFormatting(post.getCreatedAt());
        this.postPhotos = getPostPhotoUrls(post);
        this.postContent = post.getContent();
        this.tags = post.getTags();
        this.checkLike = checkLike(post, loginUser);
        this.likeCount = post.getLikedUsers().size();
        this.commentCount = post.getComments().size();
    }

    private Long postId;
    private String writeUserImage;
    private String userRole;
    private String userGrade;
    private String writeUserName;
    private String createdDate;
    private List<String> postPhotos;
    private String postContent;
    private List<String> tags;
    private boolean checkLike;
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

        System.out.println(postPhotos);

        List<String> postPhotoUrls = new ArrayList<>();

        for (PostPhoto postPhoto : postPhotos) {
            postPhotoUrls.add(postPhoto.getFilePath());
        }
        return postPhotoUrls;
    }

    private void getUserRoleAndUserGrade(User createUser) {
        if (createUser.getRoles().contains(UserRole.ROLE_GUIDE.getRole())) {
            this.userRole = UserRole.ROLE_GUIDE.getRole();
            this.userGrade = createUser.getGuideGrade().getGrade();
        }
        else {
            this.userRole = UserRole.ROLE_TOURIST.getRole();
            this.userGrade = createUser.getTouristGrade().getGrade();
        }
    }

    private boolean checkLike(Post post, User loginUser) {
        if (post.getLikedUsers().contains(loginUser)) {
            return true;
        }
        else {
            return false;
        }
    }

}
