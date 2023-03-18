package com.koing.server.koing_server.controller.user;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.common.success.SuccessStatusCode;
import com.koing.server.koing_server.controller.tour.TourSurveyController;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.service.user.UserService;
import com.koing.server.koing_server.service.user.dto.UserCategoryIndexesUpdateDto;
import com.koing.server.koing_server.service.user.dto.UserPasswordChangeDto;
import com.koing.server.koing_server.service.user.dto.UserWithdrawalDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = "User")
@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    @ApiOperation("User - 유저 리스트를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User - 유저 정보 가져오기 성공"),
            @ApiResponse(code = 401, message = "토큰이 없습니다."),
            @ApiResponse(code = 410, message = "만료된 토큰입니다."),
            @ApiResponse(code = 411, message = "잘못된 토큰 형식입니다."),
            @ApiResponse(code = 412, message = "알 수 없는 권한이므로 접근이 불가능합니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @GetMapping("/users")
    public SuperResponse getUser() {

        LOGGER.info("[UserController] 유저 리스트 조회 시도");

        SuperResponse getUserListResponse;
        try {
            getUserListResponse = userService.getUsers();
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[UserController] 유저 리스트 조회 성공");

        return getUserListResponse;
    }


    @ApiOperation("User - 가이드 리스트를 가져옵니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User - 가이드 정보 가져오기 성공"),
            @ApiResponse(code = 401, message = "토큰이 없습니다."),
            @ApiResponse(code = 410, message = "만료된 토큰입니다."),
            @ApiResponse(code = 411, message = "잘못된 토큰 형식입니다."),
            @ApiResponse(code = 412, message = "알 수 없는 권한이므로 접근이 불가능합니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @GetMapping("/users/guides")
    public SuperResponse getGuides() {

        LOGGER.info("[UserController] 가이드 리스트 조회 시도");

        SuperResponse getGuideListResponse;
        try {
            getGuideListResponse = userService.getGuides();
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[UserController] 가이드 리스트 조회 성공");

        return getGuideListResponse;
    }


    @ApiOperation("User - 유저를 팔로우를 누릅니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User - 유저 팔로우 누르기 성공"),
            @ApiResponse(code = 402, message = "팔로우 처리과정에서 오류가 발생했습니다."),
            @ApiResponse(code = 404, message = "해당 유저를 찾을 수 없습니다."),
            @ApiResponse(code = 404, message = "존재하지 않는 페이지 입니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PatchMapping("/follow/{targetUserId}/{loginUserId}")
    public SuperResponse pressFollowUser(
            @PathVariable("targetUserId") Long targetUserId,
            @PathVariable("loginUserId") Long loginUserId
    ) {
        LOGGER.info("[UserController] 유저 팔로우 업데이트 시도");
        SuperResponse pressFollowUserResponse;
        try {
            pressFollowUserResponse = userService.pressFollow(targetUserId, loginUserId);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[UserController] 유저 팔로우 업데이트 성공");

        return pressFollowUserResponse;
    }


    @ApiOperation("User - 팔로우한 유저 리스트를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User - 팔로우한 유저 리스트를 조회 성공"),
            @ApiResponse(code = 404, message = "해당 유저를 찾을 수 없습니다."),
            @ApiResponse(code = 404, message = "존재하지 않는 페이지 입니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @GetMapping("/follow/{userId}")
    public SuperResponse pressLikeTour(@PathVariable("userId") Long userId) {
        LOGGER.info("[UserController] 팔로우한 유저 리스트 조회 시도");
        SuperResponse getFollowUserListResponse;
        try {
            getFollowUserListResponse = userService.getFollowing(userId);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[UserController] 팔로우한 유저 리스트 조회 성공");

        return getFollowUserListResponse;
    }


    @ApiOperation("User - User의 My page를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User - Tourist의 My page를 조회 성공"),
            @ApiResponse(code = 404, message = "해당 유저를 찾을 수 없습니다."),
            @ApiResponse(code = 404, message = "존재하지 않는 페이지 입니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @GetMapping("/mypage/{userId}/{today}")
    public SuperResponse getMyPage(
            @PathVariable("userId") Long userId,
            @PathVariable("today") String today
    ) {
        LOGGER.info("[UserController] My page 조회 시도");
        SuperResponse getMyPageResponse;
        try {
            getMyPageResponse = userService.getMyInfo(userId, today);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            System.out.println(exception);
            System.out.println(exception.getMessage());
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[UserController] My page 조회 성공");

        return getMyPageResponse;
    }


    @ApiOperation("User - Tour Guide의 세부정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User - Tour Guide의 세부정보를 조회 성공"),
            @ApiResponse(code = 404, message = "해당 유저를 찾을 수 없습니다."),
            @ApiResponse(code = 404, message = "해당 투어를 찾을 수 없습니다."),
            @ApiResponse(code = 404, message = "존재하지 않는 페이지 입니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @GetMapping("/{guideId}/{loginUserId}/{tourId}")
    public SuperResponse getGuideDetailInfo(
            @PathVariable("guideId") Long guideId,
            @PathVariable("loginUserId") Long loginUserId,
            @PathVariable("tourId") Long tourId
    ) {
        LOGGER.info("[UserController] 투어 Guide 세부 정보 조회 시도");
        SuperResponse getGuideDetailInfoResponse;
        try {
            getGuideDetailInfoResponse = userService.getGuideDetailInfo(guideId, loginUserId, tourId);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[UserController] 투어 Guide 세부 정보 조회 성공");

        return getGuideDetailInfoResponse;
    }


    @ApiOperation("User - 마이페이지에서 User의 세부정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User - 마이페이지에서 User의 세부정보를 조회 성공"),
            @ApiResponse(code = 404, message = "해당 유저를 찾을 수 없습니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @GetMapping("/{userId}")
    public SuperResponse getUserDetailInfoFromMyPage(
            @PathVariable("userId") Long userId
    ) {
        LOGGER.info("[UserController] 마이페이지에서 User 세부 정보 조회 시도");
        SuperResponse getUserDetailInfoFromMyPageResponse;
        try {
            getUserDetailInfoFromMyPageResponse = userService.getUserDetailInfoFromMyPage(userId);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[UserController] 마이페이지에서 User 세부 정보 조회 성공");

        return getUserDetailInfoFromMyPageResponse;
    }


    @ApiOperation("User - User의 선호 카테고리를 업데이트 합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User - User 선호 카테고리 업데이트 성공"),
            @ApiResponse(code = 402, message = "유저 선호 카테고리 업데이트 과정에서 오류가 발생했습니다."),
            @ApiResponse(code = 404, message = "해당 유저를 찾을 수 없습니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PatchMapping("/category/{userId}")
    public SuperResponse updateUserCategoryIndexes(
            @RequestBody UserCategoryIndexesUpdateDto userCategoryIndexesUpdateDto
            ) {
        LOGGER.info("[UserController] User 선호 카테고리 업데이트 시도");
        SuperResponse updateUserCategoryIndexesResponse;
        try {
            updateUserCategoryIndexesResponse = userService.updateUserCategoryIndexes(userCategoryIndexesUpdateDto);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[UserController] User 선호 카테고리 업데이트 성공");

        return updateUserCategoryIndexesResponse;
    }


    @ApiOperation("User - User의 선호 카테고리를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User - User 선호 카테고리 조회 성공"),
            @ApiResponse(code = 404, message = "해당 유저 선호 카테고리를 찾을 수 없습니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @GetMapping("/category/{userId}")
    public SuperResponse getUserCategoryIndexes(
            @PathVariable("userId") Long userId
    ) {
        LOGGER.info("[UserController] User 선호 카테고리 조회 시도");
        SuperResponse getUserCategoryIndexesResponse;
        try {
            getUserCategoryIndexesResponse = userService.getUserCategoryIndexes(userId);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[UserController] User 선호 카테고리 조회 성공");

        return getUserCategoryIndexesResponse;
    }


    @ApiOperation("User - 회원 탈퇴를 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User - 회원 탈퇴 성공"),
            @ApiResponse(code = 402, message = "유저 탈퇴 과정에서 오류가 발생했습니다."),
            @ApiResponse(code = 404, message = "해당 유저를 찾을 수 없습니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PatchMapping("/withdrawal")
    public SuperResponse withdrawalUser(
            @RequestBody UserWithdrawalDto userWithdrawalDto
            ) {
        LOGGER.info("[UserController] 유저 탈퇴 시도");
        SuperResponse withdrawalUserResponse;
        try {
            withdrawalUserResponse = userService.withdrawalUser(userWithdrawalDto);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[UserController] 유저 탈퇴 성공");

        return withdrawalUserResponse;
    }


    @ApiOperation("User - 비밀번호를 변경 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User - 비밀번호를 변경 성공"),
            @ApiResponse(code = 402, message = "비밀번호 변경 과정에서 오류가 발생했습니다."),
            @ApiResponse(code = 404, message = "해당 유저를 찾을 수 없습니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PatchMapping("/password")
    public SuperResponse changePassword(
            @RequestBody UserPasswordChangeDto userPasswordChangeDto
    ) {
        LOGGER.info("[UserController] 비밀번호 변경 시도");

        SuperResponse passwordChangeResponse;
        try {
            passwordChangeResponse = userService.changePassword(userPasswordChangeDto);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }

        LOGGER.info("[UserController] 비밀번호 변경 성공");

        return passwordChangeResponse;
    }

}
