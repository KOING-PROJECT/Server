package com.koing.server.koing_server.controller.admin;


import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.service.admin.AdminTourService;
import com.koing.server.koing_server.service.admin.dto.AdminChangeToursRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Admin")
@RequestMapping("/admin/tour")
@RestController
@RequiredArgsConstructor
public class AdminTourController {

    private final Logger LOGGER = LoggerFactory.getLogger(AdminTourController.class);
    private final AdminTourService adminTourService;

    @ApiOperation("AdminTour - 관리자 페이지의 진행중인 투어를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "AdminTour - 관리자 페이지의 진행 중 투어 조회 성공"),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @GetMapping("/not-finish")
    public SuperResponse adminGetNotFinishTours() {
        LOGGER.info("[AdminTourController] 관리자 페이지 진행 중 투어 조회 시도");
        SuperResponse notFinishToursResponse;
        try {
            notFinishToursResponse = adminTourService.adminGetNotFinishTours();
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[AdminTourController] 관리자 페이지 진행 중 투어 조회 성공");

        return notFinishToursResponse;
    }

    @ApiOperation("AdminTour - 관리자 페이지의 종료된 투어를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "AdminTour - 관리자 페이지의 종료된 투어 조회 성공"),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @GetMapping("/finished")
    public SuperResponse adminGetFinishedTours() {
        LOGGER.info("[AdminTourController] 관리자 페이지 종료 된 투어 조회 시도");
        SuperResponse finishedToursResponse;
        try {
            finishedToursResponse = adminTourService.adminGetFinishedTours();
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[AdminTourController] 관리자 페이지 종료 된 투어 조회 성공");

        return finishedToursResponse;
    }

    @ApiOperation("AdminTour - 관리자 페이지의 투어 세부내용을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "AdminTour - 관리자 페이지의 투어 세부내용 조회 성공"),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @GetMapping("/{tourId}")
    public SuperResponse adminGetTourDetails(@PathVariable("tourId") Long tourId) {
        LOGGER.info("[AdminTourController] 관리자 페이지 투어 세부내용 조회 시도");
        SuperResponse tourDetailResponse;
        try {
            tourDetailResponse = adminTourService.adminGetTourDetail(tourId);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[AdminTourController] 관리자 페이지 투어 세부내용 조회 성공");

        return tourDetailResponse;
    }


    @ApiOperation("AdminTour - 관리자 페이지의 미승인 투어를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "AdminTour - 관리자 페이지의 미승인 투어 조회 성공"),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @GetMapping("/created")
    public SuperResponse adminGetCreatedTours() {
        LOGGER.info("[AdminTourController] 관리자 페이지 미승인 투어 조회 시도");
        SuperResponse createdTourResponse;
        try {
            createdTourResponse = adminTourService.adminGetCreatedTour();
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[AdminTourController] 관리자 페이지 미승인 투어 조회 성공");

        return createdTourResponse;
    }


    @ApiOperation("AdminTour - 관리자 페이지의 미승인 투어 세부내용을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "AdminTour - 관리자 페이지의 미승인 투어 세부내용 조회 성공"),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @GetMapping("/created/{tourId}")
    public SuperResponse amdinGetCreatedTourDetails(@PathVariable("tourId") Long tourId) {
        LOGGER.info("[AdminTourController] 관리자 페이지 미승인 투어 세부내용 조회 시도");
        SuperResponse createdTourDetailResponse;
        try {
            createdTourDetailResponse = adminTourService.adminGetCreatedTourDetail(tourId);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[AdminTourController] 관리자 페이지 미승인 투어 세부내용 조회 성공");

        return createdTourDetailResponse;
    }


    @ApiOperation("AdminTour - 관리자 페이지에서 투어를 승인합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "AdminTour - 관리자 페이지에서 투어 승인 성공"),
            @ApiResponse(code = 402, message = "투어 승인 과정에서 오류가 발생했습니다."),
            @ApiResponse(code = 404, message = "해당 투어를 찾을 수 없습니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PatchMapping("/approval")
    public SuperResponse adminApprovalTours(@RequestBody AdminChangeToursRequestDto adminChangeToursRequestDto) {
        LOGGER.info("[AdminTourController] 관리자 페이지에서 투어 승인 시도");
        SuperResponse approvalTourResponse;
        try {
            approvalTourResponse = adminTourService.adminApprovalTours(adminChangeToursRequestDto);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[AdminTourController] 관리자 페이지에서 투어 승인 성공");

        return approvalTourResponse;
    }


    @ApiOperation("AdminTour - 관리자 페이지에서 투어를 거절합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "AdminTour - 관리자 페이지에서 투어 거절 성공"),
            @ApiResponse(code = 402, message = "투어 거절 과정에서 오류가 발생했습니다."),
            @ApiResponse(code = 404, message = "해당 투어를 찾을 수 없습니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PatchMapping("/rejection")
    public SuperResponse adminRejectionTours(@RequestBody AdminChangeToursRequestDto adminChangeToursRequestDto) {
        LOGGER.info("[AdminTourController] 관리자 페이지에서 투어 거절 시도");
        SuperResponse rejectionTourResponse;
        try {
            rejectionTourResponse = adminTourService.adminRejectionTours(adminChangeToursRequestDto);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[AdminTourController] 관리자 페이지에서 투어 거절 성공");

        return rejectionTourResponse;
    }

}
