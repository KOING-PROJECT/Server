package com.koing.server.koing_server.service.user.dto;

import com.koing.server.koing_server.common.enums.CreateStatus;
import com.koing.server.koing_server.common.enums.GuideGrade;
import com.koing.server.koing_server.common.enums.TourApplicationStatus;
import com.koing.server.koing_server.common.enums.TourStatus;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.NotAcceptableException;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.tour.TourApplication;
import com.koing.server.koing_server.domain.tour.TourParticipant;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.service.tour.dto.TourMyEndTourDto;
import com.koing.server.koing_server.service.tour.dto.TourMyTourDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserGuideMyPageDto {

    public UserGuideMyPageDto(User user, String today) {
        this.guideName = user.getName();
        this.roles = user.getRoles();
        if (user.getUserOptionalInfo().getImageUrls() != null &&
                user.getUserOptionalInfo().getImageUrls().size() > 0) {
            this.imageUrl = user.getUserOptionalInfo().getImageUrls().get(0);
            setJobAndUnivAndCompany(user);
        }
        this.myTours = createMyTours(user, today);
        this.creatingTours = createCreatingTours(user, today);
        this.createdTours = createCreatedTours(user, today);
        this.recruitmentTours = createRecruitmentTours(user, today);
        this.myEndTours = createTourMyEndTourDtos(user);
        this.guideGrade = user.getGuideGrade();
        this.totalEarnAmount = user.getTotalEarnAmount();
        this.currentRemainAmount = user.getCurrentRemainAmount();
        this.totalTourists = user.getTotalTourists();
        this.country = user.getCountry();
        this.age = calculateAge(user.getBirthDate());
        this.gender = user.getGender().getGender();
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
    private String job;
    private String universityName;
    private String company;
    private int totalEarnAmount;
    private int currentRemainAmount;
    private int totalTourists;
    private String country;
    private int age;
    private String gender;

    private List<TourMyTourDto> createMyTours(User user, String today) {
        List<Tour> createTours = user.getCreateTours()
                .stream()
                .filter(tour -> !tour.getTourStatus().equals(TourStatus.FINISH)
                        && tour.getCreateStatus().equals(CreateStatus.COMPLETE))
                .collect(Collectors.toList());

        List<TourMyTourDto> tourMyTourDtos = new ArrayList<>();
        for (Tour createTour : createTours) {
            tourMyTourDtos.add(new TourMyTourDto(createTour, today));
//            List<TourApplication> tourApplications
//                    = createTour.getTourApplications()
//                    .stream()
//                    .filter((TourApplication t) -> t.getTourDate().equals(today))
//                    .collect(Collectors.toList());
//            if (tourApplications != null && tourApplications.size() > 0) {
//                tourMyTourDtos.add(new TourMyTourDto(createTour, tourApplications.get(0).getGuideProgressStatus()));
//            }
//            else {
//                tourMyTourDtos.add(new TourMyTourDto(createTour));
//            }
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
            tourMyTourDtos.add(new TourMyTourDto(creatingTour, today));
        }

        return tourMyTourDtos;
    }

    private List<TourMyTourDto> createCreatingTours(User user, String today) {
        List<Tour> createTours = user.getCreateTours()
                .stream()
                .filter(tour -> tour.getCreateStatus().equals(CreateStatus.CREATING))
                .collect(Collectors.toList());

        List<TourMyTourDto> tourMyTourDtos = new ArrayList<>();
        for (Tour createTour : createTours) {
            tourMyTourDtos.add(new TourMyTourDto(createTour, today));
        }

        return tourMyTourDtos;
    }

    private List<TourMyTourDto> createCreatedTours(User user, String today) {
        List<Tour> createTours = user.getCreateTours()
                .stream()
                .filter(tour -> tour.getCreateStatus().equals(CreateStatus.COMPLETE)
                        && tour.getTourStatus().equals(TourStatus.CREATED))
                .collect(Collectors.toList());

        List<TourMyTourDto> tourMyTourDtos = new ArrayList<>();
        for (Tour createTour : createTours) {
            tourMyTourDtos.add(new TourMyTourDto(createTour, today));
        }

        tourMyTourDtos = tourMyTourDtos
                .stream()
                .sorted(Comparator.comparing(
                        (TourMyTourDto t) -> t.getTourDates().stream().sorted().collect(Collectors.toList()).get(0)
                ).reversed())
                .collect(Collectors.toList());

        return tourMyTourDtos;
    }

    private List<TourMyTourDto> createRecruitmentTours(User user, String today) {
        List<Tour> createTours = user.getCreateTours()
                .stream()
                .filter(tour -> tour.getCreateStatus().equals(CreateStatus.COMPLETE)
                        && (tour.getTourStatus().equals(TourStatus.RECRUITMENT)
                        || tour.getTourStatus().equals(TourStatus.DE_ACTIVATE))
                )
                .collect(Collectors.toList());

        List<TourMyTourDto> tourMyTourDtos = new ArrayList<>();
        for (Tour createTour : createTours) {
            tourMyTourDtos.add(new TourMyTourDto(createTour, today));
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
                TourApplicationStatus tourApplicationStatus = tourApplication.getTourApplicationStatus();
                if (tourApplicationStatus.equals(TourApplicationStatus.GUIDE_END)
                        || tourApplicationStatus.equals(TourApplicationStatus.TOURIST_END)
                        || tourApplicationStatus.equals(TourApplicationStatus.NO_REVIEW)
                        || tourApplicationStatus.equals(TourApplicationStatus.GUIDE_REVIEWED)
                        || tourApplicationStatus.equals(TourApplicationStatus.TOURIST_REVIEWED)
                        || tourApplicationStatus.equals(TourApplicationStatus.REVIEWED)
                ) {
                    endTourApplication.add(tourApplication);
                }
            }
        }

//        List<TourMyEndTourDto> tourMyEndTourDtos = new ArrayList<>();
//        for (TourApplication tourApplication : endTourApplication) {
//            int reviewToTouristCount = 0;
//            for (TourParticipant tourParticipant : tourApplication.getTourParticipants()) {
//                if (tourParticipant.getReviewToTourist() != null) {
//                    reviewToTouristCount += 1;
//                }
//            }
//
//            if (tourApplication.getTourParticipants().size() > reviewToTouristCount) {
//                tourMyEndTourDtos.add(new TourMyEndTourDto(tourApplication, false));
//            }
//            else {
//                tourMyEndTourDtos.add(new TourMyEndTourDto(tourApplication, true));
//            }
//        }

        List<TourMyEndTourDto> tourMyEndTourDtos = new ArrayList<>();
        for (TourApplication tourApplication : endTourApplication) {
            tourMyEndTourDtos.add(new TourMyEndTourDto(tourApplication));
        }

        tourMyEndTourDtos = tourMyEndTourDtos
                .stream()
                .sorted(Comparator.comparing((TourMyEndTourDto t) -> t.getTourDate()).reversed())
                .collect(Collectors.toList());

        return tourMyEndTourDtos;
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
