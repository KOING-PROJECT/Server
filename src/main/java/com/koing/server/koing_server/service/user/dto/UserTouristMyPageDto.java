package com.koing.server.koing_server.service.user.dto;

import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.tour.TourApplication;
import com.koing.server.koing_server.domain.tour.TourParticipant;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.service.tour.dto.TourHistoryDto;
import com.koing.server.koing_server.service.tour.dto.TourLikeDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserTouristMyPageDto {

    public UserTouristMyPageDto(User user) {
        this.touristName = user.getName();
        this.roles = user.getRoles();
        this.imageUrl = user.getUserOptionalInfo().getImageUrl();
        this.following = createUserFollowDtos(user);
        this.likeTours = createTourLikeDtos(user);
        this.tourHistories = createTourHistoryDtos(user);
    }

    private String touristName;
    private Set<String> roles;
    private String imageUrl;
    private Set<UserFollowDto> following;
    private Set<TourLikeDto> likeTours;
    private Set<TourHistoryDto> tourHistories;

    private Set<UserFollowDto> createUserFollowDtos(User user) {
        Set<User> followingUsers = user.getFollowing();

        Set<UserFollowDto> userFollowDtos = new HashSet<>();
        for (User following : followingUsers) {
            userFollowDtos.add(new UserFollowDto(following));
        }

        return userFollowDtos;
    }

    private Set<TourLikeDto> createTourLikeDtos(User user) {
        Set<Tour> likeTours = user.getPressLikeTours();

        Set<TourLikeDto> tourLikeDtos = new HashSet<>();
        for (Tour likeTour : likeTours) {
            tourLikeDtos.add(new TourLikeDto(likeTour));
        }

        return tourLikeDtos;
    }

    private Set<TourHistoryDto> createTourHistoryDtos(User user) {
        Set<TourParticipant> tourParticipants = user.getTourParticipants();

        Set<TourApplication> tourApplications = new HashSet<>();
        for (TourParticipant tourParticipant : tourParticipants) {
            tourApplications.add(tourParticipant.getTourApplication());
        }

        Set<TourHistoryDto> tourHistoryDtos = new HashSet<>();
        for (TourApplication tourApplication : tourApplications) {
            tourHistoryDtos.add(new TourHistoryDto(tourApplication));
        }

        return tourHistoryDtos;
    }

}
