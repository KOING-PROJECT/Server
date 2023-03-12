package com.koing.server.koing_server.service.user;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.enums.UserRole;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.DBFailException;
import com.koing.server.koing_server.common.exception.IOFailException;
import com.koing.server.koing_server.common.exception.NotAcceptableException;
import com.koing.server.koing_server.common.exception.NotFoundException;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.domain.tour.Tour;
import com.koing.server.koing_server.domain.tour.repository.Tour.TourRepositoryImpl;
import com.koing.server.koing_server.domain.user.UserOptionalInfo;
import com.koing.server.koing_server.domain.user.repository.UserOptionalInfoRepository;
import com.koing.server.koing_server.domain.user.repository.UserOptionalInfoRepositoryImpl;
import com.koing.server.koing_server.domain.user.repository.UserRepository;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.domain.user.repository.UserRepositoryImpl;
import com.koing.server.koing_server.service.s3.component.AWSS3Component;
import com.koing.server.koing_server.service.user.dto.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final UserRepositoryImpl userRepositoryImpl;
    private final TourRepositoryImpl tourRepositoryImpl;

    @Transactional
    public SuperResponse getUsers() {

        LOGGER.info("[UserService] 유저 리스트 조회 시도");

        List<User> users = userRepositoryImpl.findAllUserByEnabled(true);

        List<UserListDto> userListDtos = new ArrayList<>();
        for (User user : users) {
            userListDtos.add(new UserListDto(user));
        }
        LOGGER.info("[UserService] 유저 리스트 조회 성공");

        return SuccessResponse.success(SuccessCode.GET_USERS_SUCCESS, new UserListResponseDto(userListDtos));
    }

    @Transactional
    public SuperResponse getGuides() {

        LOGGER.info("[UserService] 가이드 리스트 조회 시도");

        List<User> users = userRepositoryImpl.findAllGuideByEnabled(true);

        List<UserGuideListDto> userGuideListDtos = new ArrayList<>();
        for (User user : users) {
            userGuideListDtos.add(new UserGuideListDto(user));
        }
        LOGGER.info("[UserService] 가이드 리스트 조회 성공");

        return SuccessResponse.success(SuccessCode.GET_GUIDES_SUCCESS, new UserGuideListResponseDto(userGuideListDtos));
    }

    @Transactional
    public User loadUserByUserEmail(String email) {
        User user = userRepositoryImpl.loadUserByUserEmail(email, true);

        return user;
    }

    @Transactional
    public SuperResponse pressFollow(Long targetUserId, Long myUserId) {
        LOGGER.info("[UserService] 유저 팔로우 업데이트 시도");

        User targetUser = getUser(targetUserId);
        LOGGER.info("[UserService] 대상 유저 조회 성공");

        if (!targetUser.getRoles().contains(UserRole.ROLE_GUIDE.getRole())) {
            throw new NotAcceptableException("해당 유저는 가이드가 아니므로 팔로우 할 수 없습니다.", ErrorCode.NOT_ACCEPTABLE_NOT_GUIDE_EXCEPTION);
        }

        int beforeTargerUserFollower;

        if (targetUser.getFollower() == null) {
            beforeTargerUserFollower = 0;
        }
        else {
            beforeTargerUserFollower = targetUser.getFollower().size();
        }

        User loginUser = getUser(myUserId);
        LOGGER.info("[UserService] 로그인 유저 조회 성공");
        int beforeLoginUserFollowing;

        if (loginUser.getFollowing() == null) {
            beforeLoginUserFollowing = 0;
        }
        else {
            beforeLoginUserFollowing = loginUser.getFollowing().size();
        }

        loginUser.pressFollow(targetUser);

        User updatedLoginUser = userRepository.save(loginUser);
        User updatedTargetUser = userRepository.save(targetUser);

        System.out.println(updatedLoginUser);

        if (beforeTargerUserFollower == updatedTargetUser.getFollower().size()) {
            throw new DBFailException("팔로우 처리과정에서 오류가 발생했습니다.", ErrorCode.DB_FAIL_PRESS_FOLLOW_USER_FAIL_EXCEPTION);
        }

        if (beforeLoginUserFollowing == updatedLoginUser.getFollowing().size()) {
            throw new DBFailException("팔로우 처리과정에서 오류가 발생했습니다.", ErrorCode.DB_FAIL_PRESS_FOLLOW_USER_FAIL_EXCEPTION);
        }

        LOGGER.info("[UserService] 유저 팔로우 업데이트 성공");

        return SuccessResponse.success(SuccessCode.PRESS_LIKE_TOUR_UPDATE_SUCCESS, null);
    }

    @Transactional
    public SuperResponse getFollowing(Long userId) {
        LOGGER.info("[UserService] 팔로잉하는 유저 조회 시도");

        User user = getUser(userId);
        LOGGER.info("[UserService] 로그인 유저 조회 성공");

        Set<User> followingUsers = user.getFollowing();

        List<UserFollowDto> userFollowDtos = new ArrayList<>();
        for (User followingUser : followingUsers) {
            userFollowDtos.add(new UserFollowDto(followingUser));
        }
        LOGGER.info("[UserService] 팔로잉하는 유저 조회 성공");

        return SuccessResponse.success(SuccessCode.GET_LIKE_TOURS_SUCCESS, new UserFollowListResponseDto(userFollowDtos));
    }

    public SuperResponse getMyInfo(Long userId, String today) {
        LOGGER.info("[UserService] My page 정보 조회 시도");

        User user = getUser(userId);
        LOGGER.info("[UserService] 로그인 유저 조회 성공");

        if (user.getRoles().contains(UserRole.ROLE_TOURIST.getRole())) {
            UserTouristMyPageDto userTouristMyPageDto = new UserTouristMyPageDto(user, today);
            LOGGER.info("[UserService] Tourist My page 정보 조회 성공");

            return SuccessResponse.success(SuccessCode.GET_TOURIST_INFO_SUCCESS, userTouristMyPageDto);
        }
        else if(user.getRoles().contains(UserRole.ROLE_GUIDE.getRole())) {
            UserGuideMyPageDto userGuideMyPageDto = new UserGuideMyPageDto(user, today);
            LOGGER.info("[UserService] Guide My page 정보 조회 성공");

            return SuccessResponse.success(SuccessCode.GET_GUIDE_INFO_SUCCESS, userGuideMyPageDto);
        }

        return ErrorResponse.error(ErrorCode.NOT_ACCEPTABLE_USER_NOT_HAVE_ROLE_EXCEPTION);
    }

    public SuperResponse getGuideDetailInfo(Long guideId, Long loginUserId, Long currentTourId) {
        LOGGER.info("[UserService] 가이드 세부 정보 조회 시도");

        User guide = getUser(guideId);
        LOGGER.info("[UserService] 가이드 조회 성공");

        User loginUser = getUser(loginUserId);
        LOGGER.info("[UserService] 로그인 유저 조회 성공");

        Tour currentTour = getTour(currentTourId);
        LOGGER.info("[UserService] 현재 투어 조회 성공");

        UserGuideDetailInfoDto userGuideDetailInfoDto = new UserGuideDetailInfoDto(guide, loginUser, currentTour);
        LOGGER.info("[UserService] 가이드 세부 정보 조회 성공");

        return SuccessResponse.success(SuccessCode.GET_TOUR_GUIDE_DETAIL_INFO_SUCCESS, userGuideDetailInfoDto);
    }

    public SuperResponse getUserDetailInfoFromMyPage(Long userId) {
        LOGGER.info("[UserService] 유저 세부 정보 조회 시도");

        User user = getUser(userId);
        LOGGER.info("[UserService] 유저 조회 성공");

        UserDetailInfoFromMyPageDto userDetailInfoFromMyPageDto = new UserDetailInfoFromMyPageDto(user);
        LOGGER.info("[UserService] 유저 세부 정보 조회 성공");

        return SuccessResponse.success(SuccessCode.GET_USER_DETAIL_INFO_FROM_MY_PAGE_SUCCESS, userDetailInfoFromMyPageDto);
    }

    @Transactional
    public SuperResponse updateUserCategoryIndexes(UserCategoryIndexesUpdateDto userCategoryIndexesUpdateDto) {
        LOGGER.info("[UserService] 유저 선호 카테고리 업데이트 시도");

        User user = getUser(userCategoryIndexesUpdateDto.getUserId());

        LOGGER.info("[UserService] 유저 조회 성공");

        Set<Integer> beforeCategoryIndexes = user.getCategoryIndexes();

        user.setCategoryIndexes(userCategoryIndexesUpdateDto.getCategoryIndexes());

        User updatedUser = userRepository.save(user);

        if (beforeCategoryIndexes.equals(updatedUser.getCategoryIndexes())) {
            throw new DBFailException("유저 선호 카테고리 업데이트 과정에서 오류가 발생했습니다.", ErrorCode.DB_FAIL_UPDATE_USER_CATEGORY_INDEXES_EXCEPTION);
        }
        LOGGER.info("[UserService] 유저 선호 카테고리 업데이트 성공");

        return SuccessResponse.success(SuccessCode.USER_CATEGORY_INDEXES_UPDATE_SUCCESS, null);
    }

    public SuperResponse getUserCategoryIndexes(Long userId) {
        LOGGER.info("[UserService] 유저를 통한 선호 카테고리 조회 시도");

        User user = getUser(userId);

        Set<Integer> userCategoryIndexes = user.getCategoryIndexes();

        LOGGER.info("[UserService] 유저를 통한 선호 카테고리 조회 성공");

        return SuccessResponse.success(SuccessCode.GET_USER_CATEGORY_INDEXES_SUCCESS, userCategoryIndexes);
    }

    private User getUser(Long userId) {
        User user = userRepositoryImpl.loadUserByUserId(userId, true);
        if (user == null) {
            throw new NotFoundException("해당 유저를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_USER_EXCEPTION);
        }

        return user;
    }

    private Tour getTour(Long tourId) {
        Tour tour = tourRepositoryImpl.findTourByTourId(tourId);
        if (tour == null) {
            throw new NotFoundException("해당 투어를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_TOUR_EXCEPTION);
        }

        return tour;
    }

}
