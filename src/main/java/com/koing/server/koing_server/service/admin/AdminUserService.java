package com.koing.server.koing_server.service.admin;

import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.domain.user.repository.UserRepositoryImpl;
import com.koing.server.koing_server.service.admin.dto.tour.AdminTourListResponseDto;
import com.koing.server.koing_server.service.admin.dto.tour.AdminTourResponseDto;
import com.koing.server.koing_server.service.admin.dto.user.AdminUserListResponseDto;
import com.koing.server.koing_server.service.admin.dto.user.AdminUserResponseDto;
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

        List<User> users = userRepositoryImpl.findAllUserByEnabled(true);

        LOGGER.info("[AdminUserService] 관리자 페이지 유저 조회 성공");

        List<AdminUserResponseDto> adminUserResponseDtos = new ArrayList<>();

        for (User user : users) {
            adminUserResponseDtos.add(new AdminUserResponseDto(user));
        }

        return SuccessResponse.success(SuccessCode.ADMIN_GET_NOT_FINISH_TOURS_SUCCESS, new AdminUserListResponseDto(adminUserResponseDtos));
    }


}
