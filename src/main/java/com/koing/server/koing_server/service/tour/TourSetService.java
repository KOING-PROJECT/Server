package com.koing.server.koing_server.service.tour;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.enums.CreateStatus;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.service.tour.dto.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TourSetService {
    // createTemporaryTourSet - temporaryTour, temporaryTourSchedule, temporaryTourSurvey 임시생성
    // completeTemporaryTourSet - tourSurvey 내용 update
    //                          - temporaryTour, temporaryTourSchedule, temporaryTourSurvey 상태 complete 로 수정
    //                          - tourApplication 생성

    private final Logger LOGGER = LoggerFactory.getLogger(TourSetService.class);
    private final TourService tourService;
    private final TourScheduleService tourScheduleService;
    private final TourSurveyService tourSurveyService;
    private final TourApplicationService tourApplicationService;

    @Transactional
    public SuperResponse createTemporaryTourSet(TourSetCreateDto tourSetCreateDto, List<MultipartFile> thumbnails) {
        LOGGER.info("[TourSetService] 임시 투어 세트 생성 시도");

        TourCreateDto tourCreateDto = new TourCreateDto(tourSetCreateDto);
        LOGGER.info("[TourSetService] 투어 createDto 생성");

        SuperResponse tourResponse = tourService.createTour(tourCreateDto, thumbnails, CreateStatus.CREATING);
        // 실험해보기
        LOGGER.info("[TourSetService] Tour 저장 완료");

        if (!(tourResponse.getData() instanceof TourDto)) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        Long tourId = ((TourDto) tourResponse.getData()).getTourId();

        TourScheduleCreateDto tourScheduleCreateDto = new TourScheduleCreateDto(tourId, tourSetCreateDto);
        LOGGER.info("[TourSetService] 투어 createScheduleDto 생성");

        SuperResponse tourScheduleResponse = tourScheduleService.createTourSchedule(
                    tourScheduleCreateDto, CreateStatus.CREATING
            );
        LOGGER.info("[TourSetService] TourSchedule 저장 완료");

        TourSurveyCreateDto tourSurveyCreateDto = new TourSurveyCreateDto(tourId, tourSetCreateDto);
        LOGGER.info("[TourSetService] TourSurveyCreateDto 생성");
        SuperResponse tourSurveyResponse = tourSurveyService.createTourSurvey(tourSurveyCreateDto, CreateStatus.CREATING);

        LOGGER.info("[TourSetService] TourSurvey 저장 완료");

//        TourApplicationCreateDto tourApplicationCreateDto = new TourApplicationCreateDto(tourId);
//        LOGGER.info("[TourSetService] 투어 createApplicationDto 생성");
//
//        SuperResponse tourApplicationResponse = tourApplicationService.createTourApplication(tourApplicationCreateDto);

        LOGGER.info("[TourSetService] 임시 투어 세트 생성 성공");

        return SuccessResponse.success(
                SuccessCode.TOUR_SET_CREATE_SUCCESS,
                tourResponse.getData()
        );
    }

    @Transactional
    public SuperResponse completeTemporaryTourSet(Long tourId, TourSurveyCreateDto tourSurveyCreateDto) {
        LOGGER.info("[TourSetService] 임시 투어 세트 완성 시도");

        SuperResponse completeTourResponse = tourService.completeTour(tourId);
        LOGGER.info("[TourSetService] 임시 투어 완성 성공");

        SuperResponse completeTourScheduleResponse = tourScheduleService.completeTourSchedule(tourId);
        LOGGER.info("[TourSetService] 임시 투어 스케줄 완성 성공");

        SuperResponse completeTourSurveyResponse = tourSurveyService.updateTourSurvey(tourId, tourSurveyCreateDto, CreateStatus.COMPLETE);
        LOGGER.info("[TourSetService] 임시 투어 설문 업데이트 및 완성 성공");

        SuperResponse createTourApplicationResponse = tourApplicationService.createTourApplication(new TourApplicationCreateDto(tourId));
        LOGGER.info("[TourSetService] 투어 신청서 생성 성공");

        return SuccessResponse.success(
                SuccessCode.TOUR_SET_COMPLETE_SUCCESS,
                completeTourResponse.getData()
        );
    }


}
