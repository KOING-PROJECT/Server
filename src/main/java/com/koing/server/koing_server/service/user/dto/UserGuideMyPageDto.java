package com.koing.server.koing_server.service.user.dto;

import com.koing.server.koing_server.common.enums.CreateStatus;
import com.koing.server.koing_server.common.enums.GuideGrade;
import com.koing.server.koing_server.common.enums.TourStatus;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.service.tour.dto.TourMyEndTourDto;
import com.koing.server.koing_server.service.tour.dto.TourMyTourDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserGuideMyPageDto {

    public UserGuideMyPageDto(User user) {
        this.guideName = user.getName();
        this.roles = user.getRoles();
        if (user.getUserOptionalInfo().getImageUrls() != null &&
                user.getUserOptionalInfo().getImageUrls().size() > 0) {
            this.imageUrl = user.getUserOptionalInfo().getImageUrls().get(0);
        }
        this.myTours = createMyTours(user);
        this.creatingTours = createCreatingTours(user);
        this.createdTours = createCreatedTours(user);
        this.recruitmentTours = createRecruitmentTours(user);
        this.myEndTours = createTourMyEndTourDtos(user);
        this.guideGrade = user.getGuideGrade();
    }

    private String guideName;
    private Set<String> roles;
    private String imageUrl;
    private Set<TourMyTourDto> myTours;
    private Set<TourMyTourDto> creatingTours;
    private Set<TourMyTourDto> createdTours;
    private Set<TourMyTourDto> recruitmentTours;
    private Set<TourMyEndTourDto> myEndTours;
    private GuideGrade guideGrade;

    private Set<TourMyTourDto> createMyTours(User user) {
        Set<Tour> createTours = user.getCreateTours()
                .stream()
                .filter(tour -> !tour.getTourStatus().equals(TourStatus.FINISH))
                .collect(Collectors.toSet());

        Set<TourMyTourDto> tourMyTourDtos = new HashSet<>();
        for (Tour createTour : createTours) {
            tourMyTourDtos.add(new TourMyTourDto(createTour));
        }

        return tourMyTourDtos;
    }

    private Set<TourMyTourDto> createCreatingTours(User user) {
        Set<Tour> createTours = user.getCreateTours()
                .stream()
                .filter(tour -> tour.getCreateStatus().equals(CreateStatus.CREATING))
                .collect(Collectors.toSet());

        Set<TourMyTourDto> tourMyTourDtos = new HashSet<>();
        for (Tour createTour : createTours) {
            tourMyTourDtos.add(new TourMyTourDto(createTour));
        }

        return tourMyTourDtos;
    }

    private Set<TourMyTourDto> createCreatedTours(User user) {
        Set<Tour> createTours = user.getCreateTours()
                .stream()
                .filter(tour -> tour.getCreateStatus().equals(CreateStatus.COMPLETE)
                        && tour.getTourStatus().equals(TourStatus.CREATED))
                .collect(Collectors.toSet());

        Set<TourMyTourDto> tourMyTourDtos = new HashSet<>();
        for (Tour createTour : createTours) {
            tourMyTourDtos.add(new TourMyTourDto(createTour));
        }

        return tourMyTourDtos;
    }

    private Set<TourMyTourDto> createRecruitmentTours(User user) {
        Set<Tour> createTours = user.getCreateTours()
                .stream()
                .filter(tour -> tour.getCreateStatus().equals(CreateStatus.COMPLETE)
                        && (tour.getTourStatus().equals(TourStatus.RECRUITMENT)
                        || tour.getTourStatus().equals(TourStatus.STANDBY)))
                .collect(Collectors.toSet());

        Set<TourMyTourDto> tourMyTourDtos = new HashSet<>();
        for (Tour createTour : createTours) {
            tourMyTourDtos.add(new TourMyTourDto(createTour));
        }

        return tourMyTourDtos;
    }

    private Set<TourMyEndTourDto> createTourMyEndTourDtos(User user) {
        Set<Tour> endTours = user.getCreateTours()
                .stream()
                .filter(tour -> tour.getTourStatus().equals(TourStatus.FINISH))
                .collect(Collectors.toSet());

        Set<TourMyEndTourDto> tourMyEndTourDtos = new HashSet<>();
        for (Tour endTour : endTours) {
            tourMyEndTourDtos.add(new TourMyEndTourDto(endTour));
        }

        return tourMyEndTourDtos;
    }

}
