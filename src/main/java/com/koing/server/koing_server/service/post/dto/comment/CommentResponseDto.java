package com.koing.server.koing_server.service.post.dto.comment;

import com.koing.server.koing_server.common.enums.UserRole;
import com.koing.server.koing_server.domain.post.Comment;
import com.koing.server.koing_server.domain.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class CommentResponseDto {

    public CommentResponseDto(Comment comment) {
        this.writeUserName = comment.getCommendUser().getName();
        this.writeUserGrade = getUserGrade(comment.getCommendUser());

        if (comment.getCommendUser().getUserOptionalInfo() != null) {
            if (comment.getCommendUser().getUserOptionalInfo().getImageUrls() != null && comment.getCommendUser().getUserOptionalInfo().getImageUrls().size() > 0) {
                this.writeUserImage = comment.getCommendUser().getUserOptionalInfo().getImageUrls().get(0);
            }
        }
        this.content = comment.getComment();
        this.createDate = createdAtFormatting(comment.getCreatedAt());
    }

    private String writeUserGrade;
    private String writeUserName;
    private String writeUserImage;
    private String content;
    private String createDate;


    private String createdAtFormatting(LocalDateTime createdAt) {
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }

    private String getUserGrade(User createUser) {
        if (createUser.getRoles().contains(UserRole.ROLE_GUIDE.getRole())) {
            return createUser.getGuideGrade().getGrade();
        }
        else {
            return createUser.getTouristGrade().getGrade();
        }
    }

}
