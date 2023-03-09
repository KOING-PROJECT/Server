package com.koing.server.koing_server.service.tour.dto;

import com.koing.server.koing_server.common.enums.*;
import com.koing.server.koing_server.domain.image.Thumbnail;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.tour.TourApplication;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class TourMyTourDto {

    public TourMyTourDto(Tour tour, String date) {
        this.tourId = tour.getId();
        this.tourTitle = tour.getTitle();
        this.maxParticipant = tour.getParticipant();
        this.thumbnails = getThumbnails(tour);
        this.guideName = tour.getCreateUser().getName();
        if (tour.getCreateUser().getUserOptionalInfo() != null) {
            this.guideThumbnails = tour.getCreateUser().getUserOptionalInfo().getImageUrls();
        }
        if (tour.getTourSchedule() != null) {
            if (tour.getTourSchedule().getTourDates() != null) {
                this.tourDates = tour.getTourSchedule().getTourDates()
                        .stream()
                        .sorted()
                        .collect(Collectors.toList());
            }
        }
        this.currentStatus = decideCurrentStatus(tour, date);
//        this.tourStatus = tour.getTourStatus();
//        this.createStatus = tour.getCreateStatus();
    }

//    public TourMyTourDto(Tour tour, ProgressStatus progressStatus) {
//        this.tourId = tour.getId();
//        this.tourTitle = tour.getTitle();
//        this.maxParticipant = tour.getParticipant();
//        this.thumbnails = getThumbnails(tour);
//        this.guideName = tour.getCreateUser().getName();
//        if (tour.getCreateUser().getUserOptionalInfo() != null) {
//            this.guideThumbnails = tour.getCreateUser().getUserOptionalInfo().getImageUrls();
//        }
//        if (tour.getTourSchedule() != null) {
//            if (tour.getTourSchedule().getTourDates() != null) {
//                this.tourDates = tour.getTourSchedule().getTourDates()
//                        .stream()
//                        .sorted()
//                        .collect(Collectors.toList());
//            }
//        }
//        this.tourStatus = tour.getTourStatus();
//        this.createStatus = tour.getCreateStatus();
//        this.guideProgressStatus = progressStatus;
//    }
//
//    public TourMyTourDto(Tour tour, String date) {
//        this.tourId = tour.getId();
//        this.tourTitle = tour.getTitle();
//        this.maxParticipant = tour.getParticipant();
//        this.thumbnails = getThumbnails(tour);
//        this.guideName = tour.getCreateUser().getName();
//        if (tour.getCreateUser().getUserOptionalInfo() != null) {
//            this.guideThumbnails = tour.getCreateUser().getUserOptionalInfo().getImageUrls();
//        }
//        if (tour.getTourSchedule() != null) {
//            if (tour.getTourSchedule().getTourDates() != null) {
//                this.tourDates = tour.getTourSchedule().getTourDates()
//                        .stream()
//                        .sorted()
//                        .collect(Collectors.toList());
//            }
//        }
//        this.tourStatus = tour.getTourStatus();
//        this.createStatus = tour.getCreateStatus();
//    }

    private Long tourId;
    private String tourTitle;
    private int maxParticipant;
    private List<String> thumbnails;
    private String guideName;
    private List<String> guideThumbnails;
    private List<String> tourDates;
//    private TourStatus tourStatus;
//    private CreateStatus createStatus;
//    private ProgressStatus guideProgressStatus;
    private CurrentStatus currentStatus;

    private List<String> getThumbnails(Tour tour) {
        List<Thumbnail> thumbnails = tour.getThumbnails()
                .stream()
                .sorted(Comparator.comparing((Thumbnail t) -> t.getThumbnailOrder()))
                .collect(Collectors.toList());

        List<String> thumbnailUrls = new ArrayList<>();

        for (Thumbnail thumbnail : thumbnails) {
            thumbnailUrls.add(thumbnail.getFilePath());
        }

        return thumbnailUrls;
    }

    private CurrentStatus decideCurrentStatus(Tour tour, String date) {
        // 임시 투어인 경우
        if (tour.getCreateStatus().equals(CreateStatus.CREATING)) {
            return CurrentStatus.CREATING;
        }

        if (tour.getTourStatus().equals(TourStatus.FINISH)) {
            return CurrentStatus.FINISH;
        }

        // TourStatus.RECRUITMENT, TourStatus.FINISH 가 아닐 경우 TourStatus = CurrentStatus
        if (!tour.getTourStatus().equals(TourStatus.RECRUITMENT)) {
            if (tour.getTourStatus().equals(TourStatus.CREATED)) {
                return CurrentStatus.CREATED;
            }
            if (tour.getTourStatus().equals(TourStatus.APPROVAL)) {
                return CurrentStatus.APPROVAL;
            }
            if (tour.getTourStatus().equals(TourStatus.REJECTION)) {
                return CurrentStatus.REJECTION;
            }
            if (tour.getTourStatus().equals(TourStatus.DE_ACTIVATE)) {
                return CurrentStatus.DE_ACTIVATE;
            }
        }

        // CreateStatus.COMPLETE && TourStatus.RECRUITMENT 인 경우
        if (tour.getTourApplications() != null && tour.getTourApplications().size() > 0) {
            boolean checkParticipants = false;
            boolean hasBeforeDate = false;

            Set<TourApplication> tourApplications = tour.getTourApplications();

            for (TourApplication tourApplication : tourApplications) {
                TourApplicationStatus tourApplicationStatus = tourApplication.getTourApplicationStatus();
                String tourApplicationDate = tourApplication.getTourDate();

                // 어느 날짜의 투어라도 신청한 사람이 있고 오늘 투어가 있을 때
                if (tourApplicationStatus.equals(TourApplicationStatus.STANDBY) && tourApplicationDate.equals(date)) {
                    return CurrentStatus.TODAY;
                }

                // 가이드만 시작을 눌렀는데 날짜가 당일 일때(오늘 날짜가 투어날짜 이전이면 GUIDE_START가 나올 수가 없음)
                if (tourApplicationStatus.equals(TourApplicationStatus.GUIDE_START) && tourApplicationDate.equals(date)) {
                    return CurrentStatus.GUIDE_START;
                }

                // 투어리스트만 시작을 눌렀는데 날짜가 당일 일때(오늘 날짜가 투어날짜 이전이면 TOURIST_START가 나올 수가 없음)
                if (tourApplicationStatus.equals(TourApplicationStatus.TOURIST_START) && tourApplicationDate.equals(date)) {
                    return CurrentStatus.TOURIST_START;
                }

                if (tourApplicationStatus.equals(TourApplicationStatus.ONGOING)) {
                    return CurrentStatus.ONGOING;
                }

                if (tourApplicationStatus.equals(TourApplicationStatus.GUIDE_END)) {
                    return CurrentStatus.GUIDE_END;
                }

                if (tourApplicationStatus.equals(TourApplicationStatus.TOURIST_END)) {
                    return CurrentStatus.TOURIST_END;
                }

                if (tourApplicationStatus.equals(TourApplicationStatus.NO_REVIEW)) {
                    return CurrentStatus.NO_REVIEW;
                }

                if (tourApplicationStatus.equals(TourApplicationStatus.GUIDE_REVIEWED)) {
                    return CurrentStatus.GUIDE_REVIEWED;
                }

                if (tourApplicationStatus.equals(TourApplicationStatus.TOURIST_REVIEWED)) {
                    return CurrentStatus.TOURIST_REVIEWED;
                }

                if (tourApplicationStatus.equals(TourApplicationStatus.REVIEWED)) {
                    return CurrentStatus.REVIEWED;
                }

                // 신청한 사람이 있던 없던, tourApplication이 어떤 상태이건
                if (Integer.parseInt(tourApplicationDate) > Integer.parseInt(date)) {
                    hasBeforeDate = true;
                    if (tourApplicationStatus.equals(TourApplicationStatus.STANDBY)) {
                        checkParticipants = true;
                    }
                }
            }

            // 당일이 아니고 오늘 이후에 투어가 남아있는 경우
            if (hasBeforeDate) {
                // 남아있는 투어에 신청자가 있으면
                if (checkParticipants) {
                    return CurrentStatus.NOT_TODAY;
                }

                return CurrentStatus.RECRUITMENT;
            }

            // 당일도 아니고 오늘 이후에 투어가 남아있지 않는 경우
            return CurrentStatus.PASSED;
        }

        return CurrentStatus.UNKNOWN;
    }
}
