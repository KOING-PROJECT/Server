package com.koing.server.koing_server.service.user.dto;

import com.koing.server.koing_server.common.enums.CreateStatus;
import com.koing.server.koing_server.common.enums.GuideGrade;
import com.koing.server.koing_server.common.enums.TourStatus;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.tour.TourApplication;
import com.koing.server.koing_server.domain.tour.TourParticipant;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.service.tour.dto.TourMyEndTourDto;
import com.koing.server.koing_server.service.tour.dto.TourMyTourDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
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
    private List<TourMyTourDto> myTours;
    private List<TourMyTourDto> creatingTours;
    private List<TourMyTourDto> createdTours;
    private List<TourMyTourDto> recruitmentTours;
    private List<TourMyEndTourDto> myEndTours;
    private GuideGrade guideGrade;

    private List<TourMyTourDto> createMyTours(User user) {
        List<Tour> createTours = user.getCreateTours()
                .stream()
                .filter(tour -> !tour.getTourStatus().equals(TourStatus.FINISH) && tour.getCreateStatus().equals(CreateStatus.COMPLETE))
                .collect(Collectors.toList());

        List<TourMyTourDto> tourMyTourDtos = new ArrayList<>();
        for (Tour createTour : createTours) {
            tourMyTourDtos.add(new TourMyTourDto(createTour));
        }

        tourMyTourDtos = tourMyTourDtos
                .stream()
                .sorted(Comparator.comparing(
                        (TourMyTourDto t) -> t.getTourDates().stream().sorted().collect(Collectors.toList()).get(0)
                ).reversed())
                .collect(Collectors.toList());

        List<Tour> creatingTours = user.getCreateTours()
                .stream()
                .filter(tour -> tour.getCreateStatus().equals(CreateStatus.CREATING))
                .collect(Collectors.toList());

        for (Tour creatingTour : creatingTours) {
            tourMyTourDtos.add(new TourMyTourDto(creatingTour));
        }

        return tourMyTourDtos;
    }

    private List<TourMyTourDto> createCreatingTours(User user) {
        List<Tour> createTours = user.getCreateTours()
                .stream()
                .filter(tour -> tour.getCreateStatus().equals(CreateStatus.CREATING))
                .collect(Collectors.toList());

        List<TourMyTourDto> tourMyTourDtos = new ArrayList<>();
        for (Tour createTour : createTours) {
            tourMyTourDtos.add(new TourMyTourDto(createTour));
        }

        return tourMyTourDtos;
    }

    private List<TourMyTourDto> createCreatedTours(User user) {
        List<Tour> createTours = user.getCreateTours()
                .stream()
                .filter(tour -> tour.getCreateStatus().equals(CreateStatus.COMPLETE)
                        && tour.getTourStatus().equals(TourStatus.CREATED))
                .collect(Collectors.toList());

        List<TourMyTourDto> tourMyTourDtos = new ArrayList<>();
        for (Tour createTour : createTours) {
            tourMyTourDtos.add(new TourMyTourDto(createTour));
        }

        tourMyTourDtos = tourMyTourDtos
                .stream()
                .sorted(Comparator.comparing(
                        (TourMyTourDto t) -> t.getTourDates().stream().sorted().collect(Collectors.toList()).get(0)
                ).reversed())
                .collect(Collectors.toList());

        return tourMyTourDtos;
    }

    private List<TourMyTourDto> createRecruitmentTours(User user) {
        List<Tour> createTours = user.getCreateTours()
                .stream()
                .filter(tour -> tour.getCreateStatus().equals(CreateStatus.COMPLETE)
                        && (tour.getTourStatus().equals(TourStatus.RECRUITMENT)
                        || tour.getTourStatus().equals(TourStatus.STANDBY)))
                .collect(Collectors.toList());

        List<TourMyTourDto> tourMyTourDtos = new ArrayList<>();
        for (Tour createTour : createTours) {
            tourMyTourDtos.add(new TourMyTourDto(createTour));
        }

        tourMyTourDtos = tourMyTourDtos
                .stream()
                .sorted(Comparator.comparing(
                        (TourMyTourDto t) -> t.getTourDates().stream().sorted().collect(Collectors.toList()).get(0)
                ).reversed())
                .collect(Collectors.toList());

        return tourMyTourDtos;
    }

    private List<TourMyEndTourDto> createTourMyEndTourDtos(User user) {
        List<Tour> endTours = user.getCreateTours()
                .stream()
                .collect(Collectors.toList());

        List<TourApplication> endTourApplication = new ArrayList<>();
        for (Tour tour : endTours) {
            for (TourApplication tourApplication : tour.getTourApplications()) {
                if (tourApplication.getTourStatus().equals(TourStatus.FINISH)) {
                    endTourApplication.add(tourApplication);
                }
            }
        }

        List<TourMyEndTourDto> tourMyEndTourDtos = new ArrayList<>();
        for (TourApplication tourApplication : endTourApplication) {
            int reviewToTouristCount = 0;
            for (TourParticipant tourParticipant : tourApplication.getTourParticipants()) {
                if (tourParticipant.getReviewToTourist() != null) {
                    reviewToTouristCount += 1;
                }
            }

            if (tourApplication.getTourParticipants().size() > reviewToTouristCount) {
                tourMyEndTourDtos.add(new TourMyEndTourDto(tourApplication, false));
            }
            else {
                tourMyEndTourDtos.add(new TourMyEndTourDto(tourApplication, true));
            }
        }

        tourMyEndTourDtos = tourMyEndTourDtos
                .stream()
                .sorted(Comparator.comparing((TourMyEndTourDto t) -> t.getTourDate()).reversed())
                .collect(Collectors.toList());

        return tourMyEndTourDtos;
    }

}
