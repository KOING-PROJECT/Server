package com.koing.server.koing_server.service.tour.dto;

import com.koing.server.koing_server.common.enums.CurrentStatus;
import com.koing.server.koing_server.common.enums.ProgressStatus;
import com.koing.server.koing_server.common.enums.TourApplicationStatus;
import com.koing.server.koing_server.common.enums.TourStatus;
import com.koing.server.koing_server.domain.image.Thumbnail;
import com.koing.server.koing_server.domain.payment.Payment;
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
public class TourHistoryDto {

    public TourHistoryDto(TourApplication tourApplication, String today, Long touristId) {
        this.tourId = tourApplication.getTour().getId();
        this.title = tourApplication.getTour().getTitle();
        this.guideName = tourApplication.getTour().getCreateUser().getName();
        this.guideId = tourApplication.getTour().getCreateUser().getId();
        this.thumbnails = getThumbnails(tourApplication.getTour());
        this.tourDate = tourApplication.getTourDate();
        this.currentStatus = decideCurrentStatus(tourApplication, today);
        setImpUid(tourApplication, touristId);
    }

    private Long tourId;
    private String title;
    private String guideName;
    private Long guideId;
    private List<String> thumbnails;
    private String tourDate;
    private CurrentStatus currentStatus;
    private String impUid;

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

    private void setImpUid(TourApplication tourApplication, Long touristId) {
        Set<Payment> payments = tourApplication.getPayments();

        for (Payment payment : payments) {
            if (payment.getTourist().getId() == touristId) {
                this.impUid = payment.getImp_uid();
            }
        }
    }

    private CurrentStatus decideCurrentStatus(TourApplication tourApplication, String today) {
        if (tourApplication.getTourApplicationStatus().equals(TourApplicationStatus.STANDBY)
                && tourApplication.getTourDate().equals(today)) {
            return CurrentStatus.TODAY;
        }

        if (tourApplication.getTourApplicationStatus().equals(TourApplicationStatus.STANDBY)
                && Integer.parseInt(tourApplication.getTourDate()) < Integer.parseInt(today)) {
            return CurrentStatus.PASSED;
        }

        if (tourApplication.getTourApplicationStatus().equals(TourApplicationStatus.GUIDE_START)
                && tourApplication.getTourDate().equals(today)) {
            return CurrentStatus.GUIDE_START;
        }

        if (tourApplication.getTourApplicationStatus().equals(TourApplicationStatus.TOURIST_START)
                && tourApplication.getTourDate().equals(today)) {
            return CurrentStatus.TOURIST_START;
        }

        if (tourApplication.getTourApplicationStatus().equals(TourApplicationStatus.ONGOING)) {
            return CurrentStatus.ONGOING;
        }

        if (tourApplication.getTourApplicationStatus().equals(TourApplicationStatus.GUIDE_END)) {
            return CurrentStatus.GUIDE_END;
        }

        if (tourApplication.getTourApplicationStatus().equals(TourApplicationStatus.TOURIST_END)) {
            return CurrentStatus.TOURIST_END;
        }

        if (tourApplication.getTourApplicationStatus().equals(TourApplicationStatus.NO_REVIEW)) {
            return CurrentStatus.NO_REVIEW;
        }

        if (tourApplication.getTourApplicationStatus().equals(TourApplicationStatus.GUIDE_REVIEWED)) {
            return CurrentStatus.GUIDE_REVIEWED;
        }

        if (tourApplication.getTourApplicationStatus().equals(TourApplicationStatus.TOURIST_REVIEWED)) {
            return CurrentStatus.TOURIST_REVIEWED;
        }

        if (tourApplication.getTourApplicationStatus().equals(TourApplicationStatus.REVIEWED)) {
            return CurrentStatus.REVIEWED;
        }
        return CurrentStatus.NOT_TODAY;
    }
}
