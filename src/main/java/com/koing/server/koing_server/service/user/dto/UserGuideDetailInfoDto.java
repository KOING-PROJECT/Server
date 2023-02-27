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
        }
        this.attachment = getAttachment();
        this.isFollowing = checkFollowing(guide, loginUser);
        this.introduction = guide.getUserOptionalInfo().getDescription();
        this.language = guide.getUserOptionalInfo().getLanguages();
        this.area = guide.getUserOptionalInfo().getAreas();
        this.job = guide.getUserOptionalInfo().getJob();
        this.otherTours = createOtherTours(guide, tour);
        this.guideGrade = guide.getGuideGrade();
    }

    private String guideName;
    private Set<String> roles;
    private String imageUrl;
    private int attachment;
    private boolean isFollowing;
    private String introduction;
    private Set<String> language;
    private Set<String> area;
    private String job;
//    private String university;
    private GuideGrade guideGrade;
    private Set<TourLikeDto> otherTours;

    private boolean checkFollowing(User guide, User loginUser) {
        return loginUser.getFollowing().contains(guide);
    }

    private Set<TourLikeDto> createOtherTours(User user, Tour tour) {
        Set<Tour> tours = user.getCreateTours()
                .stream()
                .filter(t -> !t.getId().equals(tour.getId())
                        && t.getCreateStatus().equals(CreateStatus.COMPLETE)
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

}
