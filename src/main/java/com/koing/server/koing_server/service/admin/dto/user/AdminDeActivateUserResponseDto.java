package com.koing.server.koing_server.service.admin.dto.user;

import com.koing.server.koing_server.common.enums.UserRole;
import com.koing.server.koing_server.domain.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class AdminDeActivateUserResponseDto {

    public AdminDeActivateUserResponseDto(User user) {
        this.userId = user.getId();
        this.userName = user.getName();
        getRoleAndGrade(user);
        this.country = user.getCountry();
        this.joinDate = createdAtFormatting(user.getCreatedAt());
        this.withdrawalDate = user.getWithdrawalAt();
        this.accumulatedReportedCount = user.getAccumulatedReportedCount();
        this.withdrawalReason = user.getWithdrawalReason();
    }

    private Long userId;
    private String userName;
    private String userRole;
    private String userGrade;
    private String country;
    private String joinDate;
    private String withdrawalDate;
    private int accumulatedReportedCount;
    private String withdrawalReason;
    
    private void getRoleAndGrade(User user) {
        if (user.getRoles().contains(UserRole.ROLE_GUIDE.getRole())) {
            this.userRole = UserRole.ROLE_GUIDE.getRole();
            this.userGrade = user.getGuideGrade().getGrade();
        }
        else if (user.getRoles().contains(UserRole.ROLE_TOURIST.getRole())){
            this.userRole = UserRole.ROLE_TOURIST.getRole();
            this.userGrade = user.getTouristGrade().getGrade();
        }
        else {
            this.userRole = "확인 불가";
            this.userGrade = "확인 불가";
        }
    }

    private String createdAtFormatting(LocalDateTime createdAt) {
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }

}
