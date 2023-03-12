package com.koing.server.koing_server.service.user.dto;

import com.koing.server.koing_server.common.enums.ProgressStatus;
import com.koing.server.koing_server.common.enums.TouristGrade;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.tour.TourApplication;
import com.koing.server.koing_server.domain.tour.TourParticipant;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.service.tour.dto.TourHistoryDto;
import com.koing.server.koing_server.service.tour.dto.TourLikeDto;
import com.koing.server.koing_server.service.tour.dto.TourMyTourDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserTouristMyPageDto {

    public UserTouristMyPageDto(User user, String today) {
        this.touristName = user.getName();
        this.roles = user.getRoles();
        if (user.getUserOptionalInfo().getImageUrls() != null &&
                user.getUserOptionalInfo().getImageUrls().size() > 0) {
            this.imageUrl = user.getUserOptionalInfo().getImageUrls().get(0);
            setJobAndUnivAndCompany(user);
        }
        this.following = createUserFollowDtos(user);
        this.likeTours = createTourLikeDtos(user);
        this.tourHistories = createTourHistoryDtos(user, today);
        this.touristGrade = user.getTouristGrade();
    }

    private String touristName;
    private Set<String> roles;
    private String imageUrl;
    private List<UserFollowDto> following;
    private List<TourLikeDto> likeTours;
    private List<TourHistoryDto> tourHistories;
    private TouristGrade touristGrade;
    private String job;
    private String universityName;
    private String company;

    private List<UserFollowDto> createUserFollowDtos(User user) {
        List<User> followingUsers = user.getFollowing().stream().collect(Collectors.toList());

        List<UserFollowDto> userFollowDtos = new ArrayList<>();
        for (User following : followingUsers) {
            userFollowDtos.add(new UserFollowDto(following));
        }

        return userFollowDtos;
    }

    private List<TourLikeDto> createTourLikeDtos(User user) {
        List<Tour> likeTours = user.getPressLikeTours().stream().collect(Collectors.toList());

        List<TourLikeDto> tourLikeDtos = new ArrayList<>();
        for (Tour likeTour : likeTours) {
            tourLikeDtos.add(new TourLikeDto(likeTour));
        }

        return tourLikeDtos;
    }

    private List<TourHistoryDto> createTourHistoryDtos(User user, String today) {
        List<TourParticipant> tourParticipants = user.getTourParticipants().stream().collect(Collectors.toList());

        List<TourApplication> tourApplications = new ArrayList<>();
//        List<ProgressStatus> touristProgressStatus = new ArrayList<>();

        for (TourParticipant tourParticipant : tourParticipants) {
            tourApplications.add(tourParticipant.getTourApplication());
//            touristProgressStatus.add(tourParticipant.getTouristProgressStatus());
        }

        List<TourHistoryDto> tourHistoryDtos = new ArrayList<>();
        for (int i = 0; i < tourApplications.size(); i++) {
            tourHistoryDtos.add(new TourHistoryDto(tourApplications.get(i), today));
        }

//        for (TourApplication tourApplication : tourApplications) {
//            tourHistoryDtos.add()
//        }

        tourHistoryDtos = tourHistoryDtos
                .stream()
                .sorted(Comparator.comparing((TourHistoryDto t) -> t.getTourDate()).reversed())
                .collect(Collectors.toList());

        return tourHistoryDtos;
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
