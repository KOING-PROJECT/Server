package com.koing.server.koing_server.controller.admin;


import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.service.admin.AdminTourService;
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
    public SuperResponse getNotFinishTours() {
        LOGGER.info("[AdminTourController] 관리자 페이지 진행 중 투어 조회 시도");
        SuperResponse notFinishToursResponse;
        try {
            notFinishToursResponse = adminTourService.getNotFinishTours();
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
    public SuperResponse getFinishedTours() {
        LOGGER.info("[AdminTourController] 관리자 페이지 종료 된 투어 조회 시도");
        SuperResponse finishedToursResponse;
        try {
            finishedToursResponse = adminTourService.getFinishedTours();
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[AdminTourController] 관리자 페이지 종료 된 투어 조회 성공");

        return finishedToursResponse;
    }

}
