package com.koing.server.koing_server.service.survey;

import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.NotFoundException;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.tour.TourCategory;
import com.koing.server.koing_server.domain.tour.repository.Tour.TourRepositoryImpl;
import com.koing.server.koing_server.domain.tour.repository.TourCategory.TourCategoryRepositoryImpl;
import com.koing.server.koing_server.domain.tour.repository.TourSchedule.TourScheduleRepositoryImpl;
import com.koing.server.koing_server.service.survey.dto.RecommendTourListResponseDto;
import com.koing.server.koing_server.service.survey.dto.RecommendTourResponseDto;
import com.koing.server.koing_server.service.survey.dto.SurveyInfoDto;
import com.koing.server.koing_server.service.tour.dto.TourDto;
import com.koing.server.koing_server.service.tour.dto.TourListResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final Logger LOGGER = LoggerFactory.getLogger(SurveyService.class);
    private final TourRepositoryImpl tourRepositoryImpl;
    private final TourCategoryRepositoryImpl tourCategoryRepositoryImpl;


    @Transactional
    public SuperResponse recommendSurvey(SurveyInfoDto surveyInfoDto) {

//        1. 날짜
//        2. 카테고리 맞는 개수
//        3. 스타일
//        4. ABC 순서? 가나다 순서

        LOGGER.info("[SurveyService] 추천 투어 요청");

        List<String> keywords = createKeywords(surveyInfoDto);

        boolean checkThroughAllFilter = true;

        List<Tour> tours = tourRepositoryImpl.findTourByStatusRecruitment();
        Set<Tour> tourSets = tours.stream().collect(Collectors.toSet());

        LOGGER.info("[SurveyService] 모집 중인 투어 조회 성공");

        Set<Tour> allFilteredResult = new HashSet<>();
//        Set<Tour> similarResult = new HashSet<>();
//        Set<Tour> filteredByScheduleTours = new HashSet<>();

        if (surveyInfoDto.getHasDate() == 0) {
            // 날짜로 tour 찾아서 recommendTours에 추가

            for (String tourDate : surveyInfoDto.getExpeditionDates()) {
                Set<Tour> filteredTemp = tours.stream().filter(tour -> tour.getTourSchedule().getTourDates().contains(tourDate)).collect(Collectors.toSet());
                allFilteredResult.addAll(filteredTemp);
//                similarResult.addAll(filteredTemp);
//                filteredByScheduleTours.addAll(filteredTemp);
            };

            if (allFilteredResult == null || allFilteredResult.size() < 1) {
                checkThroughAllFilter = false;
            }
        }

//        for (Tour tour : allFilteredResult) {
//            System.out.println("tourDateFiltered = " + tour.getId());
//        }

        // filteredTemp 에 계속 filter 정보 담아주기

//        Set<Tour> filteredByCategoryTours = new HashSet<>();
//        Set<Tour> filteredByTourDetailTours = new HashSet<>();

        if (surveyInfoDto.getExpeditionCategory() != 7) {
            // category filter
            if (allFilteredResult != null && allFilteredResult.size() > 0) {
                // 이전 filter를 통과한 투어가 있을 때 현재 filter를 통과한 투어가 없으면 값을 return
                Set<Tour> filterByTourCategoryTemp = filterByTourCategory(surveyInfoDto.getExpeditionCategory(), allFilteredResult);
                if (filterByTourCategoryTemp == null || filterByTourCategoryTemp.size() < 1) {
                    List<RecommendTourResponseDto> result = setToSortedList(allFilteredResult);
                    return SuccessResponse.success(SuccessCode.SIMILAR_TOUR_RECOMMEND_SUCCESS, new RecommendTourListResponseDto(keywords, result));
                }

                // 이전 filter를 통과한 tour가 있을 때 현재 filter를 통과한 tour가 있으면 allFilteredResult에 값 저장
                allFilteredResult = filterByTourCategoryTemp;
//                similarResult = filterByTourCategoryTemp;

//                for (Tour tour : allFilteredResult) {
//                    System.out.println("tourCategoryFiltered = " + tour.getId());
//                }

                // category detail filter
                // 이전 filter를 통과한 투어가 있을 때
                // tourCategory가 맞는게 없으면 tourDetail로 조회가 불가능 하므로 tourSets를 넣는 경우는 필요없음
                if (allFilteredResult != null && allFilteredResult.size() > 0) {
                    String cagetoryName = getTourCategoryName(surveyInfoDto.getExpeditionCategory());
                    int tourDetailType = getTourDetailType(cagetoryName, surveyInfoDto.getExpeditionDetailType());

                    if (tourDetailType != -1) {
                        allFilteredResult = filterByTourDetail(tourDetailType, allFilteredResult);
                    }
//                filteredByTourDetailTours = filterByTourDetail(filteredByCategoryTours, tourDetailType);
                }
            }
            else {
                // 이전 filter를 통과한 투어 없을 때
                allFilteredResult = filterByTourCategory(surveyInfoDto.getExpeditionCategory(), tourSets);
                checkThroughAllFilter = false;
//                filteredByCategoryTours = filterByTourCategoryTemp;
            }
        }

//        for (Tour tour : allFilteredResult) {
//            System.out.println("tourDetailFiltered = " + tour.getId());
//        }
//        Set<Tour> filteredBtStyleTours = new HashSet<>();

        if (surveyInfoDto.getExpeditionStyle() != 2) {
            String style = getStyle(surveyInfoDto.getExpeditionStyle());

            if (allFilteredResult != null && allFilteredResult.size() > 0) {
                Set<Tour> filterByTourStyleTemp = filterByTourStyle(style, allFilteredResult);

                if (filterByTourStyleTemp == null || filterByTourStyleTemp.size() < 1) {
                    List<RecommendTourResponseDto> result = setToSortedList(allFilteredResult);
                    return SuccessResponse.success(SuccessCode.SIMILAR_TOUR_RECOMMEND_SUCCESS, new RecommendTourListResponseDto(keywords, result));
                }

                allFilteredResult = filterByTourStyleTemp;
//                filteredBtStyleTours = filterByTourStyle(style, filteredByTourDetailTours);
            }
            else {
                allFilteredResult = filterByTourStyle(style, tourSets);
                checkThroughAllFilter = false;
            }
        }

//        for (Tour tour : allFilteredResult) {
//            System.out.println("tourStyleFiltered = " + tour.getId());
//        }
//        Set<Tour> filteredByCharacterTours = new HashSet<>();

        if (surveyInfoDto.getExpeditionCharacter() != 3) {
            String character = getCharacter(surveyInfoDto.getExpeditionCharacter());

            if (allFilteredResult != null && allFilteredResult.size() > 0) {
                Set<Tour> filteredByCharacterToursTemp = filterByTourCharacter(character, allFilteredResult);

                if (filteredByCharacterToursTemp == null || filteredByCharacterToursTemp.size() < 1) {
                    List<RecommendTourResponseDto> result = setToSortedList(allFilteredResult);
                    return SuccessResponse.success(SuccessCode.SIMILAR_TOUR_RECOMMEND_SUCCESS, new RecommendTourListResponseDto(keywords, result));
                }

                allFilteredResult = filteredByCharacterToursTemp;
            }
            else {
                allFilteredResult = filterByTourCharacter(character, tourSets);
                checkThroughAllFilter = false;
            }
        }

//        for (Tour tour : allFilteredResult) {
//            System.out.println("tourCharacterFiltered = " + tour.getId());
//        }

        if (surveyInfoDto.getNeedMoveSupport() == 0) {
            if (allFilteredResult != null && allFilteredResult.size() > 0) {
                Set<Tour> filteredByMovingSupportTemp = filterByMovingSupport(allFilteredResult);

                if (filteredByMovingSupportTemp == null || filteredByMovingSupportTemp.size() < 1) {
                    List<RecommendTourResponseDto> result = setToSortedList(allFilteredResult);
                    return SuccessResponse.success(SuccessCode.SIMILAR_TOUR_RECOMMEND_SUCCESS, new RecommendTourListResponseDto(keywords, result));
                }

                allFilteredResult = filteredByMovingSupportTemp;
            }
            else {
                allFilteredResult = filterByMovingSupport(tourSets);
                checkThroughAllFilter = false;
            }
        }

//        for (Tour tour : allFilteredResult) {
//            System.out.println("tourMovingSupportFiltered = " + tour.getId());
//        }

        List<RecommendTourResponseDto> result = setToSortedList(allFilteredResult);

        if (checkThroughAllFilter) {
            return SuccessResponse.success(SuccessCode.RECOMMEND_TOUR_SUCCESS, new RecommendTourListResponseDto(keywords, result));
        }
        else {
            return SuccessResponse.success(SuccessCode.SIMILAR_TOUR_RECOMMEND_SUCCESS, new RecommendTourListResponseDto(keywords, result));
        }
    }

    private Set<Tour> filterByTourCategory(int inputTourCategory, Set<Tour> filteredTours) {

        LOGGER.info("[SurveyService] 투어 카테고리로 filter 시도");

        String tourCategoryName = getTourCategoryName(inputTourCategory);

        TourCategory tourCategory = tourCategoryRepositoryImpl.findTourCategoryByCategoryName(tourCategoryName);

        if (tourCategory == null) {
            throw new NotFoundException("존재하지 않는 투어 카테고리 입니다.", ErrorCode.NOT_FOUND_TOUR_CATEGORY_EXCEPTION);
        }

        return filteredTours.stream().filter(tour -> tour.getTourCategories().contains(tourCategory)).collect(Collectors.toSet());
    }

    private Set<Tour> filterByTourStyle(String inputTourStyle, Set<Tour> filteredTours) {

        LOGGER.info("[SurveyService] 투어 스타일로 filter 시도");

        return filteredTours.stream().filter(tour -> tour.getTourSurvey().getStyle().equals(inputTourStyle)).collect(Collectors.toSet());
    }

    private Set<Tour> filterByTourCharacter(String inputTourCharacter, Set<Tour> filteredTours) {
        LOGGER.info("[SurveyService] 투어 character로 filter 시도");

        return filteredTours.stream().filter(tour -> tour.getTourSurvey().getType().equals(inputTourCharacter)).collect(Collectors.toSet());

    }

    private Set<Tour> filterByTourDetail(int inputTourDetail, Set<Tour> filteredTours) {
        LOGGER.info("[SurveyService] 투어 detail type으로 filter 시도");

        Set<Tour> filteredByTourDetail = new HashSet<>();
        for (Tour tour : filteredTours) {
            if (tour.getTourDetailTypes().contains(Integer.toString(inputTourDetail))) {
                filteredByTourDetail.add(tour);
            }
        }

        return filteredByTourDetail;
    }

    private Set<Tour> filterByMovingSupport(Set<Tour> filteredTours) {
        LOGGER.info("[SurveyService] 이동 지원으로 filter 시도");

        return filteredTours.stream().filter(tour -> tour.getTourSurvey().isMovingSupport()).collect(Collectors.toSet());
    }

    private List<RecommendTourResponseDto> setToSortedList(Set<Tour> allFilteredTours) {

        List<RecommendTourResponseDto> recommendTourDtos = new ArrayList<>();
        for (Tour tour : allFilteredTours) {
            recommendTourDtos.add(new RecommendTourResponseDto(tour));
        }

        return recommendTourDtos.stream().sorted((tour1, tour2) -> -tour1.getTourTitle().compareTo(tour2.getTourTitle())).collect(Collectors.toList());
    }

    private String getTourCategoryName(int inputTourCategory) {
        if (inputTourCategory == 0) {
            return "0";
        }
        else if (inputTourCategory == 1) {
            return "2";
        }
        else if (inputTourCategory == 2) {
            return "1";
        }
        else if (inputTourCategory == 3) {
            return "4";
        }
        else if (inputTourCategory == 4) {
            return "3";
        }
        else if (inputTourCategory == 5) {
            return "5";
        }
        else {
            return "6";
        }
    }

    private String getStyle(int inputStyle) {
        if (inputStyle == 0) {
            return "0";
        }
        else {
            return "1";
        }
    }

    private String getCharacter(int inputCharacter) {
        if (inputCharacter == 0) {
            return "0";
        }
        else if (inputCharacter == 1){
            return "1";
        }
        else {
            return "2";
        }
    }

    private int getTourDetailType(String inputCategoryName, int inputDetailType) {

        if (inputCategoryName.equals("0")) {
            return getShoppingDetailType(inputDetailType);
        }
        else if (inputCategoryName.equals("1")) {
            return getFoodDetailType(inputDetailType);
        }
        else if (inputCategoryName.equals("2")) {
            return getHealingDetailType(inputDetailType);
        }
        else if (inputCategoryName.equals("3")) {
            return getLandmarkDetailType(inputDetailType);
        }
        else if (inputCategoryName.equals("4")) {
            return getHistoryDetailType(inputDetailType);
        }
        else if (inputCategoryName.equals("5")) {
            return getEntertainmentDetailType(inputDetailType);
        }
        else if (inputCategoryName.equals("6")){
            return getActivityDetailType(inputDetailType);
        }
        else {
            return -1;
        }
    }

    private int getHealingDetailType(int inputDetailType) {

        if (inputDetailType == 0) {
            return 13;
        }
        else if (inputDetailType == 1) {
            return 14;
        }
        else if (inputDetailType == 2) {
            return 15;
        }
        else if (inputDetailType == 3) {
            return 16;
        }
        else {
            return -1;
        }
    }

    private int getFoodDetailType(int inputDetailType) {

        if (inputDetailType == 0) {
            return 7;
        }
        else if (inputDetailType == 1) {
            return 8;
        }
        else if (inputDetailType == 2) {
            return 9;
        }
        else if (inputDetailType == 3) {
            return 10;
        }
        else if (inputDetailType == 4) {
            return 11;
        }
        else if (inputDetailType == 5) {
            return 12;
        }
        else {
            return -1;
        }
    }

    private int getShoppingDetailType(int inputDetailType) {

        if (inputDetailType == 0) {
            return 0;
        }
        else if (inputDetailType == 1) {
            return 2;
        }
        else if (inputDetailType == 2) {
            return 4;
        }
        else if (inputDetailType == 3) {
            return 3;
        }
        else if (inputDetailType == 4) {
            return 5;
        }
        else if (inputDetailType == 5) {
            return 1;
        }
        else {
            return -1;
        }
    }

    private int getHistoryDetailType(int inputDetailType) {

        if (inputDetailType == 0) {
            return 19;
        }
        else if (inputDetailType == 1) {
            return 20;
        }
        else if (inputDetailType == 2) {
            return 21;
        }
        else {
            return -1;
        }
    }

    private int getActivityDetailType(int inputDetailType) {

        if (inputDetailType == 0) {
            return 28;
        }
        else if (inputDetailType == 1) {
            return 29;
        }
        else if (inputDetailType == 2) {
            return 30;
        }
        else if (inputDetailType == 3) {
            return 31;
        }
        else if (inputDetailType == 4) {
            return 32;
        }
        else {
            return -1;
        }
    }

    private int getEntertainmentDetailType(int inputDetailType) {

        if (inputDetailType == 0) {
            return 23;
        }
        else if (inputDetailType == 1) {
            return 22;
        }
        else if (inputDetailType == 2) {
            return 24;
        }
        else if (inputDetailType == 3) {
            return 26;
        }
        else if (inputDetailType == 4) {
            return 27;
        }
        else if (inputDetailType == 5) {
            return 25;
        }
        else {
            return -1;
        }
    }

    private int getLandmarkDetailType(int inputDetailType) {

        if (inputDetailType == 0) {
            return 17;
        }
        else if (inputDetailType == 1) {
            return 18;
        }
        else {
            return -1;
        }
    }

    private List<String> createKeywords(SurveyInfoDto surveyInfoDto) {

        List<String> keywords = new ArrayList<>();

        if (surveyInfoDto.getHasDate() == 0) {
            List<String> expeditionDates = surveyInfoDto.getExpeditionDates();

            if (expeditionDates.size() > 1) {
                expeditionDates = expeditionDates.stream().sorted((date1, date2) -> date1.compareTo(date2)).collect(Collectors.toList());
                keywords.add(expeditionDates.get(0) + " 외 다수");
            }
            else {
                expeditionDates.add(expeditionDates.get(0));
            }
        }

        keywords.add(Integer.toString(surveyInfoDto.getNumberOfTourist()));

        int categoryIdx = surveyInfoDto.getExpeditionCategory();

        if (categoryIdx == 0) {
            keywords.add("쇼핑");
        }
        else if (categoryIdx == 1) {
            keywords.add("힐링");
        }
        else if (categoryIdx == 2) {
            keywords.add("먹방");
        }
        else if (categoryIdx == 3) {
            keywords.add("역사/전통");
        }
        else if (categoryIdx == 4) {
            keywords.add("관광명소");
        }
        else if (categoryIdx == 5) {
            keywords.add("엔터테인먼트");
        }
        else if (categoryIdx == 6) {
            keywords.add("액티비티");
        }
        else if (categoryIdx == 7) {
            keywords.add("다 좋아요:)");
        }

        if (categoryIdx != 7) {
            int tourDetailTypeIdx = getTourDetailType(getTourCategoryName(categoryIdx), surveyInfoDto.getExpeditionDetailType());

            keywords.add(getTourDetailTypeName(tourDetailTypeIdx));
        }

        return keywords;
    }

    private String getTourDetailTypeName(int tourDetailTypeIdx) {

        if (tourDetailTypeIdx == 0) {
            return "뷰티";
        }
        else if (tourDetailTypeIdx == 1) {
            return "패션";
        }
        else if (tourDetailTypeIdx == 2) {
            return "전통시장";
        }
        else if (tourDetailTypeIdx == 3) {
            return "전자";
        }
        else if (tourDetailTypeIdx == 4) {
            return "굿즈";
        }
        else if (tourDetailTypeIdx == 5) {
            return "쇼핑몰투어";
        }
        else if (tourDetailTypeIdx == 7) {
            return "한식";
        }
        else if (tourDetailTypeIdx == 8) {
            return "디저트";
        }
        else if (tourDetailTypeIdx == 9) {
            return "매콤매콤";
        }
        else if (tourDetailTypeIdx == 10) {
            return "술";
        }
        else if (tourDetailTypeIdx == 11) {
            return "유튜버 맛집";
        }
        else if (tourDetailTypeIdx == 12) {
            return "길거리 음식";
        }
        else if (tourDetailTypeIdx == 13) {
            return "산책";
        }
        else if (tourDetailTypeIdx == 14) {
            return "등산";
        }
        else if (tourDetailTypeIdx == 15) {
            return "한강 투어";
        }
        else if (tourDetailTypeIdx == 16) {
            return "테라피";
        }
        else if (tourDetailTypeIdx == 17) {
            return "뷰 맛집";
        }
        else if (tourDetailTypeIdx == 18) {
            return "핫플레이스";
        }
        else if (tourDetailTypeIdx == 19) {
            return "유적지";
        }
        else if (tourDetailTypeIdx == 20) {
            return "박물관";
        }
        else if (tourDetailTypeIdx == 21) {
            return "전통미술";
        }
        else if (tourDetailTypeIdx == 22) {
            return "e스포츠";
        }
        else if (tourDetailTypeIdx == 23) {
            return "아이돌/연예";
        }
        else if (tourDetailTypeIdx == 24) {
            return "드라마/영화";
        }
        else if (tourDetailTypeIdx == 25) {
            return "웹툰";
        }
        else if (tourDetailTypeIdx == 26) {
            return "전시";
        }
        else if (tourDetailTypeIdx == 27) {
            return "공연";
        }
        else if (tourDetailTypeIdx == 28) {
            return "놀이공원/테마파크";
        }
        else if (tourDetailTypeIdx == 29) {
            return "스포츠/액티비티";
        }
        else if (tourDetailTypeIdx == 30) {
            return "스포츠 경기 관람";
        }
        else if (tourDetailTypeIdx == 31) {
            return "야외활동";
        }
        else {
            return "공예/제작";
        }
    }

}
