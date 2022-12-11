package com.koing.server.koing_server.service.tour;

import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.tour.repository.TourRepositoryImpl;
import com.koing.server.koing_server.service.tour.dto.TourListResponseDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TourService {

    private final Logger LOGGER = LoggerFactory.getLogger(TourService.class);
    private final TourRepositoryImpl tourRepositoryImpl;

    public SuperResponse getTours() {

        LOGGER.info("[TourService] Tour list 요청");

        // 모집중이거나 모집이 완료되었지만 아직 시작안한(대기 가능하게) tour list 반환
        List<Tour> tourList = tourRepositoryImpl.findTourByStatusRecruitmentAndStandby();
        LOGGER.info("[TourService] Tour list 요청 완료");

        TourListResponseDto tourListResponseDto = new TourListResponseDto(tourList);

        return SuccessResponse.success(SuccessCode.GET_TOURS_SUCCESS, tourListResponseDto);
    }


}
