package com.koing.server.koing_server.service.post;

import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.enums.TourCategoryIndex;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.service.post.dto.PostCreateDto;
import com.koing.server.koing_server.service.review.ReviewService;
import com.koing.server.koing_server.service.tour.dto.TourDto;
import com.koing.server.koing_server.service.tour.dto.TourListResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final Logger LOGGER = LoggerFactory.getLogger(PostService.class);

//    @Transactional
//    public SuperResponse createPost(PostCreateDto postCreateDto) {
//
//        LOGGER.info("[PostService] POST 생성 시도");
//
//        // 모집중이거나 모집이 완료되었지만 아직 시작안한(대기 가능하게) tour list 반환
//        List<Tour> tours;
//        if (categories.contains(TourCategoryIndex.ALL.getCategoryIndex())) {
//            tours = tourRepositoryImpl.findTourByStatusRecruitment();
//        }
//        else {
//            tours = tourRepositoryImpl.findTourByStatusRecruitment()
//                    .stream()
//                    .filter(tour -> tour.checkCategories(categories))
//                    .collect(Collectors.toList());
//        }
//        LOGGER.info("[PostService] POST 생성 성공");
//
//        List<TourDto> tourDtos = new ArrayList<>();
//        for (Tour tour : tours) {
//            tourDtos.add(new TourDto(tour));
//        }
//
//        tourDtos = tourDtos
//                .stream()
//                .sorted(Comparator.comparing(
//                        (TourDto t) -> t.getTourDates().stream().sorted().collect(Collectors.toList()).get(0)
//                ).reversed().reversed())
//                .collect(Collectors.toList());
//
//        TourListResponseDto tourListResponseDto = new TourListResponseDto(tourDtos);
//
//        return SuccessResponse.success(SuccessCode.GET_TOURS_SUCCESS, tourListResponseDto);
//    }


}
