package com.koing.server.koing_server.service.user.dto;

import com.koing.server.koing_server.common.enums.*;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.service.tour.dto.TourLikeDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDetailInfoFromMyPageDto {

    public UserDetailInfoFromMyPageDto(User user) {
        this.userName = user.getName();
        this.roles = user.getRoles();
        if (user.getUserOptionalInfo().getImageUrls() != null &&
                user.getUserOptionalInfo().getImageUrls().size() > 0) {
            this.imageUrl = user.getUserOptionalInfo().getImageUrls().get(0);
            this.introduction = user.getUserOptionalInfo().getDescription();
            this.language = user.getUserOptionalInfo().getLanguages();
            this.area = user.getUserOptionalInfo().getAreas();
            this.job = user.getUserOptionalInfo().getJob();
            setJobAndUnivAndCompany(user);
        }
        this.attachment = user.getAttachment();

        if (user.getRoles().contains(UserRole.ROLE_GUIDE.getRole())) {
            this.tours = createTours(user);
            this.guideGrade = user.getGuideGrade();
        } else {
            this.tours = null;
            this.touristGrade = user.getTouristGrade();
        }
    }

    private String userName;
    private Set<String> roles;
    private String imageUrl;
    private int attachment;
    private String introduction;
    private Set<String> language;
    private Set<String> area;
    private Set<TourLikeDto> tours;
    private GuideGrade guideGrade;
    private TouristGrade touristGrade;
    private String job;
    private String universityName;
    private String company;

    private Set<TourLikeDto> createTours(User user) {
        Set<Tour> tours = user.getCreateTours()
                .stream()
                .filter(t -> t.getCreateStatus().equals(CreateStatus.COMPLETE)
                        && (t.getTourStatus().equals(TourStatus.RECRUITMENT)
                        || t.getTourStatus().equals(TourStatus.STANDBY))
                )
                .collect(Collectors.toSet());

        Set<TourLikeDto> tourLikeDtos = new HashSet<>();
        for (Tour t : tours) {
            tourLikeDtos.add(new TourLikeDto(t));
        }

        return tourLikeDtos;
    }

    private void setJobAndUnivAndCompany(User user) {
        String job = user.getUserOptionalInfo().getJob();

        this.job = job;

        if (job.equals("대학생")) {
            this.universityName = user.getUserOptionalInfo().getUniversityEmail();
            this.company = "";
        }
        else {
            this.company = user.getUserOptionalInfo().getCompany();
            this.universityName = "";
        }
    }

}
