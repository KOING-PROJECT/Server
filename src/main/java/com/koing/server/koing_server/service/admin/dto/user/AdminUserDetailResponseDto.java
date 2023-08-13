package com.koing.server.koing_server.service.admin.dto.user;

import com.koing.server.koing_server.common.enums.CreateStatus;
import com.koing.server.koing_server.common.enums.UserRole;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class AdminUserDetailResponseDto {

    public AdminUserDetailResponseDto(User user) {
        this.userName = user.getName();
        this.country = user.getCountry();
        if (user.getUserOptionalInfo() != null) {
            if (user.getUserOptionalInfo().getImageUrls() != null && user.getUserOptionalInfo().getImageUrls().size() > 0) {
                this.userImage = user.getUserOptionalInfo().getImageUrls().get(0);
            }
            this.job = user.getUserOptionalInfo().getJob();
            this.universityEmail = user.getUserOptionalInfo().getUniversityEmail();
            this.areas = user.getUserOptionalInfo().getAreas();
            this.ageRange = user.getUserOptionalInfo().getAgeRange();
            this.gender = user.getUserOptionalInfo().getGender().getGender();
            this.languages = user.getUserOptionalInfo().getLanguages();
        }
        getRoleAndGrade(user);
        this.userStatus = user.getUserStatus().getStatus();
        this.accumulatedReportedCount = user.getAccumulatedReportedCount();
        this.attachment = user.getAttachment();
        this.tourProgressCount = user.getTourProgressCount();
        getCreateToursAndCreateTourCount(user.getCreateTours());
        this.joinDate = createdAtFormatting(user.getCreatedAt());
        this.withdrawalDate = user.getWithdrawalAt();
    }

    private String userName;
    private String country;
    private String userImage;
    private String userRole;
    private String userGrade;
    private String userStatus;
    private int accumulatedReportedCount;
    private double attachment;
    private String gender;
    private String ageRange;
    private String job;
    private String universityEmail;
    private int tourProgressCount;
    private int createTourCount;
    private List<AdminUserCreateTourDto> createTours;
    private Set<String> areas;
    private Set<String> languages;
    private String joinDate;
    private String withdrawalDate;

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
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    }

    private void getCreateToursAndCreateTourCount(Set<Tour> createTours) {

        List<AdminUserCreateTourDto> createTourDtos = new ArrayList<>();

        List<Tour> completeTours = createTours.stream().filter(tour -> tour.getCreateStatus().equals(CreateStatus.COMPLETE)).collect(Collectors.toList());

        for (Tour tour : completeTours) {
            createTourDtos.add(new AdminUserCreateTourDto(tour));
        }

        this.createTours = createTourDtos;
        this.createTourCount = createTourDtos.size();
    }

}
