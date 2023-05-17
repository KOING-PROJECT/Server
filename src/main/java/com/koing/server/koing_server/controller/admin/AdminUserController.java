package com.koing.server.koing_server.controller.admin;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.service.admin.AdminUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Admin-User")
@RequestMapping("/admin/user")
@RestController
@RequiredArgsConstructor
public class AdminUserController {

    private final Logger LOGGER = LoggerFactory.getLogger(AdminUserController.class);
    private final AdminUserService adminUserService;

    @ApiOperation("AdminTour - 관리자 페이지에서 유저를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "AdminTour - 관리자 페이지에서 유저 조회 성공"),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @GetMapping("")
    public SuperResponse adminGetUsers() {
        LOGGER.info("[AdminUserController] 관리자 페이지 유저 조회 시도");
        SuperResponse usersResponse;
        try {
            usersResponse = adminUserService.adminGetUsers();
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[AdminUserController] 관리자 페이지 유저 조회 성공");

        return usersResponse;
    }

    @ApiOperation("AdminTour - 관리자 페이지에서 유저 세부내용을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "AdminTour - 관리자 페이지에서 유저 세부내용 조회 성공"),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @GetMapping("/{userId}")
    public SuperResponse adminGetUserDetail(@PathVariable("userId") Long userId) {
        LOGGER.info("[AdminUserController] 관리자 페이지 유저 세부내용 조회 시도");
        SuperResponse userDetailResponse;
        try {
            userDetailResponse = adminUserService.adminGetUserDetail(userId);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[AdminUserController] 관리자 페이지 유저 세부내용 조회 성공");

        return userDetailResponse;
    }


    @ApiOperation("AdminTour - 관리자 페이지에서 탈퇴 유저를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "AdminTour - 관리자 페이지에서 탈퇴 유저 조회 성공"),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @GetMapping("/de-activate")
    public SuperResponse adminGetDeActivateUsers() {
        LOGGER.info("[AdminUserController] 관리자 페이지 탈퇴 유저 조회 시도");
        SuperResponse deActivateUsersResponse;
        try {
            deActivateUsersResponse = adminUserService.adminGetDeActivateUsers();
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[AdminUserController] 관리자 페이지 탈퇴 유저 조회 성공");

        return deActivateUsersResponse;
    }

}
