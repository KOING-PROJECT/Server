package com.koing.server.koing_server.controller.user;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.controller.tour.TourScheduleController;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.service.user.UserOptionalInfoService;
import com.koing.server.koing_server.service.user.dto.UserOptionalInfoCreateDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "UserOptionalInfo")
@RequestMapping("/user-optional-info")
@RestController
@RequiredArgsConstructor
public class UserOptionalInfoController {

    private final Logger LOGGER = LoggerFactory.getLogger(UserOptionalInfoController.class);
    private final UserOptionalInfoService userOptionalInfoService;

    @ApiOperation("UserOptionalInfo - 유저 선택정보를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "UserOptionalInfo - 유저 선택정보 생성 성공"),
            @ApiResponse(code = 402, message = "유저 선택정보 생성 과정에서 오류가 발생했습니다."),
            @ApiResponse(code = 402, message = "유저 선택정보 업데이트 과정에서 오류가 발생했습니다."),
            @ApiResponse(code = 404, message = "해당 유저를 찾을 수 없습니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping("")
    public SuperResponse createUserOptionalInfo(@RequestBody UserOptionalInfoCreateDto userOptionalInfoCreateDto) {
        LOGGER.info("[UserOptionalInfoController] 유저 선택정보 생성 시도");
        SuperResponse userOptionalInfoResponse;
        try {
            userOptionalInfoResponse = userOptionalInfoService.createUserOptionalInfo(userOptionalInfoCreateDto);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[UserOptionalInfoController] 유저 선택정보 생성 성공");
        return userOptionalInfoResponse;
    }

}
