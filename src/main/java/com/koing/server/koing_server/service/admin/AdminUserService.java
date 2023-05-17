package com.koing.server.koing_server.service.admin;

import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.NotFoundException;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.domain.user.repository.UserRepositoryImpl;
import com.koing.server.koing_server.service.admin.dto.user.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final Logger LOGGER = LoggerFactory.getLogger(AdminUserService.class);
    private final UserRepositoryImpl userRepositoryImpl;

    @Transactional
    public SuperResponse adminGetUsers() {

        LOGGER.info("[AdminUserService] 관리자 페이지 유저 조회 시도");

        List<User> users = userRepositoryImpl.findAllUsers();

        LOGGER.info("[AdminUserService] 관리자 페이지 유저 조회 성공");

        List<AdminUserResponseDto> adminUserResponseDtos = new ArrayList<>();

        for (User user : users) {
            adminUserResponseDtos.add(new AdminUserResponseDto(user));
        }

        return SuccessResponse.success(SuccessCode.ADMIN_GET_USERS_SUCCESS, new AdminUserListResponseDto(adminUserResponseDtos));
    }


    @Transactional
    public SuperResponse adminGetUserDetail(Long userId) {

        LOGGER.info("[AdminUserService] 관리자 페이지 유저 세부내용 조회 시도");

        User user = getUser(userId);

        LOGGER.info("[AdminUserService] 관리자 페이지 유저 세부내용 조회 성공");

        AdminUserDetailResponseDto adminUserDetailResponseDto = new AdminUserDetailResponseDto(user);

        return SuccessResponse.success(SuccessCode.ADMIN_GET_USER_DETAIL_SUCCESS, new AdminUserDetailResponseWrapperDto(adminUserDetailResponseDto));

    }


    @Transactional
    public SuperResponse adminGetDeActivateUsers() {

        LOGGER.info("[AdminUserService] 관리자 페이지 탈퇴 유저 조회 시도");

        List<User> deActivateUsers = userRepositoryImpl.findWithdrawalUsers();

        LOGGER.info("[AdminUserService] 관리자 페이지 탈퇴 유저 조회 성공");

        List<AdminDeActivateUserResponseDto> adminDeActivateUserResponseDtos = new ArrayList<>();

        for (User deActivateUser : deActivateUsers) {
            adminDeActivateUserResponseDtos.add(new AdminDeActivateUserResponseDto(deActivateUser));
        }

        return SuccessResponse.success(SuccessCode.ADMIN_GET_DEACTIVATE_USERS_SUCCESS, new AdminDeActivateUserListResponseDto(adminDeActivateUserResponseDtos));

    }


    private User getUser(Long userId) {
        User user = userRepositoryImpl.loadUserByUserId(userId, true);
        if (user == null) {
            throw new NotFoundException("해당 유저를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_USER_EXCEPTION);
        }

        return user;
    }

}
