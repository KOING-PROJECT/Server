package com.koing.server.koing_server.service.user.dto;

import com.koing.server.koing_server.common.enums.CreateStatus;
import com.koing.server.koing_server.common.enums.GuideGrade;
import com.koing.server.koing_server.common.enums.TourStatus;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.service.tour.dto.TourLikeDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserGuideDetailInfoDto {

    public UserGuideDetailInfoDto(User guide, User loginUser, Tour tour) {
        this.guideName = guide.getName();
        this.roles = guide.getRoles();
        if (guide.getUserOptionalInfo().getImageUrls() != null &&
                guide.getUserOptionalInfo().getImageUrls().size() > 0) {
            this.imageUrl = guide.getUserOptionalInfo().getImageUrls().get(0);
            this.introduction = guide.getUserOptionalInfo().getDescription();
            this.language = guide.getUserOptionalInfo().getLanguages();
            this.area = guide.getUserOptionalInfo().getAreas();
            setJobAndUnivAndCompany(guide);
            this.ageRange = guide.getUserOptionalInfo().getAgeRange();
            this.gender = guide.getUserOptionalInfo().getGender().getGender();
        }
        this.attachment = guide.getAttachment();
        this.isFollowing = checkFollowing(guide, loginUser);
        this.otherTours = createOtherTours(guide, tour);
        this.guideGrade = guide.getGuideGrade();
        this.country = guide.getCountry();
    }

    private String guideName;
    private Set<String> roles;
    private String imageUrl;
    private double attachment;
    private boolean isFollowing;
    private String introduction;
    private Set<String> language;
    private Set<String> area;
    private GuideGrade guideGrade;
    private Set<TourLikeDto> otherTours;
    private String ageRange;
    private String job;
    private String universityName;
    private String company;
    private String country;
    private String gender;

    private boolean checkFollowing(User guide, User loginUser) {
        return loginUser.getFollowing().contains(guide);
    }

    private Set<TourLikeDto> createOtherTours(User user, Tour tour) {
        Set<Tour> tours = user.getCreateTours()
                .stream()
                .filter(t -> !t.getId().equals(tour.getId())
                        && t.getCreateStatus().equals(CreateStatus.COMPLETE)
                        && t.getTourStatus().equals(TourStatus.RECRUITMENT)
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

    private int calculateAge(String brithDay) {
        String[] yearMonthDay = brithDay.split("/");

        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();

        int age = year - Integer.parseInt(yearMonthDay[0]);

        if (Integer.parseInt(yearMonthDay[1]) > month) {
            age -= 1;
        }
        else if (Integer.parseInt(yearMonthDay[1]) == month) {
            if (Integer.parseInt(yearMonthDay[2]) >=  day) {
                age -= 1;
            }
        }

        return age;
    }
}
