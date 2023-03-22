package com.koing.server.koing_server.service.user;

import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.DBFailException;
import com.koing.server.koing_server.common.exception.NotAcceptableException;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.service.tour.TourService;
import com.koing.server.koing_server.service.user.dto.UserWithdrawalDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserWithdrawalService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserWithdrawalService.class);
    private final UserService userService;
    private final TourService tourService;

    @Transactional
    public SuperResponse withdrawalUser(UserWithdrawalDto userWithdrawalDto) {
        LOGGER.info("[UserService] 유저 탈퇴 시도");

        List<Long> createdTourIds = userService.withdrawalUser(userWithdrawalDto);

        LOGGER.info("[UserService] 유저 Enable false로 변경 완료");

        for (Long id : createdTourIds) {
            SuperResponse deleteTourResponse = tourService.deleteTour(id);
        }

        LOGGER.info("[UserService] 탈퇴 유저의 투어 삭제 성공");

        return SuccessResponse.success(SuccessCode.WITHDRAWAL_USER_SUCCESS, null);
    }

}
