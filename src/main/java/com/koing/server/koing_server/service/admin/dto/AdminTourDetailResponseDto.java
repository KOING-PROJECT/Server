package com.koing.server.koing_server.service.admin.dto;

import com.koing.server.koing_server.common.enums.TourCategoryIndex;
import com.koing.server.koing_server.domain.image.Thumbnail;
import com.koing.server.koing_server.domain.review.ReviewToGuide;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.tour.TourCategory;
import com.koing.server.koing_server.domain.tour.TourDetailSchedule;
import com.koing.server.koing_server.domain.tour.TourSurvey;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class AdminTourDetailResponseDto {

    public AdminTourDetailResponseDto(Tour tour, List<ReviewToGuide> reviewToGuides) {
        this.tourId = tour.getId();
        this.tourTitle = tour.getTitle();
        this.tourDescription = tour.getDescription();
        this.categoryNames = getTourCategoryName(tour.getTourCategories());
        this.categoryDetails = getTourCategoryDetailTypeNames(tour.getTourDetailTypes());
        this.recruitmentNumber = tour.getParticipant();
        this.tourPrice = tour.getTourPrice();
        this.additionalPriceDetail = tour.getAdditionalPrice();
        this.additionalPrice = getTotalAdditionalPrice(tour.getAdditionalPrice());
        this.tourDates = tour.getTourSchedule().getTourDates().stream().sorted().collect(Collectors.toList());
        this.tourThumbnails = getThumbnails(tour.getThumbnails());
        this.guideName = tour.getCreateUser().getName();
        this.guideGrade = tour.getCreateUser().getGuideGrade().getGrade();
        this.accumulatedApprovalCount = 0;
        this.tourStatus = tour.getTourStatus().getStatus();
        this.createdAt = createdAtFormatting(tour.getCreatedAt());
        this.tourDetailSchedules = getSortedTourDetailSchedule(tour.getTourSchedule().getTourDetailScheduleList());
        this.tourSurvey = new AdminTourSurveyDto(tour.getTourSurvey());
        this.tourReviews = getTourReviewDtos(reviewToGuides);
    }

    private Long tourId;
    private String tourTitle;
    private String tourDescription;
    private List<String> categoryNames;
    private List<String> categoryDetails;
    private int recruitmentNumber;
    private int tourPrice;
    private int additionalPrice;
    private Set<HashMap<String, List>> additionalPriceDetail;
    private List<String> tourDates;
    private List<String> tourThumbnails;
    private String guideName;
    private String guideGrade;
    private int accumulatedApprovalCount;
    private String tourStatus;
    private String createdAt;
    private List<TourDetailSchedule> tourDetailSchedules;
    private AdminTourSurveyDto tourSurvey;
    private List<AdminTourReviewDto> tourReviews;

    private List<String> getTourCategoryName(Set<TourCategory> tourCategories) {
        List<String> tourCategoryNames = new ArrayList<>();
        for (TourCategory tourCategory : tourCategories) {
            for (TourCategoryIndex category : TourCategoryIndex.values()) {
                if (category.getCategoryIndex().equals(tourCategory.getCategoryName())) {
                    tourCategoryNames.add(category.getCategoryName());
                }
            }
        }

        return tourCategoryNames;
    }

    private List<String> getTourCategoryDetailTypeNames(Set<String> detailTypes) {

        List<String> tourDetailTypeNames = new ArrayList<>();

        for (String detailType: detailTypes) {
            tourDetailTypeNames.add(detailTypeNumberToName(detailType));
        }

        return tourDetailTypeNames;
    }

    private int getTotalAdditionalPrice(Set<HashMap<String, List>> additionalPriceDetails) {

        int totalAdditionalPrice = 0;

        for (HashMap<String, List> additionalPriceDetail : additionalPriceDetails) {
            for (String key : additionalPriceDetail.keySet()) {
                List<String> values = additionalPriceDetail.get(key);
                totalAdditionalPrice += Integer.parseInt(values.get(0));
            }
        }

        return totalAdditionalPrice;
    }

    private List<String> getThumbnails(List<Thumbnail> thumbnails) {
        List<Thumbnail> tempThumbnails = thumbnails
                .stream()
                .sorted(Comparator.comparing((Thumbnail t) -> t.getThumbnailOrder()))
                .collect(Collectors.toList());

        List<String> thumbnailUrls = new ArrayList<>();

        for (Thumbnail thumbnail : tempThumbnails) {
            thumbnailUrls.add(thumbnail.getFilePath());
        }

        return thumbnailUrls;
    }

    private String createdAtFormatting(LocalDateTime createdAt) {
        return createdAt.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    }

    private List<AdminTourReviewDto> getTourReviewDtos(List<ReviewToGuide> reviewToGuides) {
        List<AdminTourReviewDto> adminTourReviewDtos = new ArrayList<>();

        for (ReviewToGuide reviewToGuide : reviewToGuides) {
            adminTourReviewDtos.add(new AdminTourReviewDto(reviewToGuide));
        }

        return adminTourReviewDtos;
    }

    private String detailTypeNumberToName(String detailTypeNumber) {
        if (detailTypeNumber.equals("0")) {
            return "뷰티";
        }
        else if (detailTypeNumber.equals("1")) {
            return "패션";
        }
        else if (detailTypeNumber.equals("2")) {
            return "전통시장";
        }
        else if (detailTypeNumber.equals("3")) {
            return "전자";
        }
        else if (detailTypeNumber.equals("4")) {
            return "굿즈";
        }
        else if (detailTypeNumber.equals("5")) {
            return "쇼핑몰투어";
        }
        else if (detailTypeNumber.equals("7")) {
            return "한식";
        }
        else if (detailTypeNumber.equals("8")) {
            return "디저트";
        }
        else if (detailTypeNumber.equals("9")) {
            return "매콤매콤";
        }
        else if (detailTypeNumber.equals("10")) {
            return "술";
        }
        else if (detailTypeNumber.equals("11")) {
            return "유튜버 맛집";
        }
        else if (detailTypeNumber.equals("12")) {
            return "길거리 음식";
        }
        else if (detailTypeNumber.equals("13")) {
            return "산책";
        }
        else if (detailTypeNumber.equals("14")) {
            return "등산";
        }
        else if (detailTypeNumber.equals("15")) {
            return "한강 투어";
        }
        else if (detailTypeNumber.equals("16")) {
            return "테라피";
        }
        else if (detailTypeNumber.equals("17")) {
            return "뷰 맛집";
        }
        else if (detailTypeNumber.equals("18")) {
            return "핫플레이스";
        }
        else if (detailTypeNumber.equals("19")) {
            return "유적지";
        }
        else if (detailTypeNumber.equals("20")) {
            return "박물관";
        }
        else if (detailTypeNumber.equals("21")) {
            return "전통미술";
        }
        else if (detailTypeNumber.equals("22")) {
            return "e스포츠";
        }
        else if (detailTypeNumber.equals("23")) {
            return "아이돌/연예";
        }
        else if (detailTypeNumber.equals("24")) {
            return "드라마/영화";
        }
        else if (detailTypeNumber.equals("25")) {
            return "웹툰";
        }
        else if (detailTypeNumber.equals("26")) {
            return "전시";
        }
        else if (detailTypeNumber.equals("27")) {
            return "공연";
        }
        else if (detailTypeNumber.equals("28")) {
            return "놀이공원/테마파크";
        }
        else if (detailTypeNumber.equals("29")) {
            return "스포츠/액티비티";
        }
        else if (detailTypeNumber.equals("30")) {
            return "스포츠/경기 관람";
        }
        else if (detailTypeNumber.equals("31")) {
            return "야외활동";
        }
        else if (detailTypeNumber.equals("32")) {
            return "공예/제작";
        }
        else {
            return "확인 불가";
        }
    }

    private List<TourDetailSchedule> getSortedTourDetailSchedule(List<TourDetailSchedule> tourDetailSchedules) {
        if (tourDetailSchedules == null || tourDetailSchedules.size() < 1) {
            return new ArrayList<>();
        }

        return tourDetailSchedules.stream().sorted((tds1, tds2) -> tds1.getStartTime().compareTo(tds2.getStartTime())).collect(Collectors.toList());
    }

}
