package com.koing.server.koing_server.service.user.dto;

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

    public UserTouristMyPageDto(User user) {
        this.touristName = user.getName();
        this.roles = user.getRoles();
        if (user.getUserOptionalInfo().getImageUrls() != null &&
                user.getUserOptionalInfo().getImageUrls().size() > 0) {
            this.imageUrl = user.getUserOptionalInfo().getImageUrls().get(0);
        }
        this.following = createUserFollowDtos(user);
        this.likeTours = createTourLikeDtos(user);
        this.tourHistories = createTourHistoryDtos(user);
        this.touristGrade = user.getTouristGrade();
    }

    private String touristName;
    private Set<String> roles;
    private String imageUrl;
    private List<UserFollowDto> following;
    private List<TourLikeDto> likeTours;
    private List<TourHistoryDto> tourHistories;

    private TouristGrade touristGrade;

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

    private List<TourHistoryDto> createTourHistoryDtos(User user) {
        List<TourParticipant> tourParticipants = user.getTourParticipants().stream().collect(Collectors.toList());

        List<TourApplication> tourApplications = new ArrayList<>();
        for (TourParticipant tourParticipant : tourParticipants) {
            tourApplications.add(tourParticipant.getTourApplication());
        }

        List<TourHistoryDto> tourHistoryDtos = new ArrayList<>();
        for (TourApplication tourApplication : tourApplications) {
            tourHistoryDtos.add(new TourHistoryDto(tourApplication));
        }

        tourHistoryDtos = tourHistoryDtos
                .stream()
                .sorted(Comparator.comparing((TourHistoryDto t) -> t.getTourDate()).reversed())
                .collect(Collectors.toList());

        return tourHistoryDtos;
    }

}
