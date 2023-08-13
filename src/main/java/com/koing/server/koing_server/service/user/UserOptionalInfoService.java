package com.koing.server.koing_server.service.user;

import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.*;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.domain.cryptogram.Cryptogram;
import com.koing.server.koing_server.domain.cryptogram.repository.CryptogramRepositoryImpl;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.domain.user.UserOptionalInfo;
import com.koing.server.koing_server.domain.user.repository.UserOptionalInfoRepository;
import com.koing.server.koing_server.domain.user.repository.UserOptionalInfoRepositoryImpl;
import com.koing.server.koing_server.domain.user.repository.UserRepository;
import com.koing.server.koing_server.domain.user.repository.UserRepositoryImpl;
import com.koing.server.koing_server.service.s3.AWSS3Service;
import com.koing.server.koing_server.service.s3.component.AWSS3Component;
import com.koing.server.koing_server.service.user.dto.UserOptionalInfoCreateDto;
import com.koing.server.koing_server.service.user.dto.UserProfileDto;
import com.koing.server.koing_server.service.user.dto.UserProfileUpdateDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserOptionalInfoService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserOptionalInfoService.class);
    private final UserOptionalInfoRepository userOptionalInfoRepository;
    private final UserOptionalInfoRepositoryImpl userOptionalInfoRepositoryImpl;
    private final UserRepositoryImpl userRepositoryImpl;
    private final UserRepository userRepository;
    private final AWSS3Component awss3Component;
    private final CryptogramRepositoryImpl cryptogramRepositoryImpl;

    @Transactional
    public SuperResponse createUserOptionalInfo(UserOptionalInfoCreateDto userOptionalInfoCreateDto) throws BoilerplateException {

        LOGGER.info("[UserOptionalInfoService] UserOptionalInfo 생성 시도");

        User user = userRepositoryImpl.loadUserByUserId(userOptionalInfoCreateDto.getUserId(), true);

        if (user == null) {
            throw new NotFoundException("해당 user를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_USER_EXCEPTION);
        }
        LOGGER.info("[UserOptionalInfoService] UserId로 User 조회 성공");

        LOGGER.info("[UserOptionalInfoService] 프로필 이미지 s3에 upload 시도");
        List<String> uploadedImageUrls = new ArrayList<>();
        if (userOptionalInfoCreateDto.getImageFiles() != null) {
            for (MultipartFile multipartFile : userOptionalInfoCreateDto.getImageFiles()) {
                try {
                    uploadedImageUrls.add(awss3Component.convertAndUploadFiles(multipartFile, "profile/image"));
                } catch (IOException ioException) {
                    throw new IOFailException("이미지 저장 과정에서 오류가 발생했습니다.", ErrorCode.DB_FAIL_UPLOAD_IMAGE_FAIL_EXCEPTION);
                } catch (Exception exception) {
                    System.out.println(exception);
                    System.out.println(exception.getMessage());
                    throw new InternalServerException("예상치 못한 서버 에러가 발생하였습니다.", ErrorCode.INTERNAL_SERVER_EXCEPTION);
                }
            }
        }

        LOGGER.info("[UserOptionalInfoService] 프로필 이미지 s3에 upload 완료 = " + uploadedImageUrls);

        String userEmail = user.getEmail();
        boolean isVerified = false;

        if (cryptogramRepositoryImpl.hasCryptogramByUserEmail(userEmail)) {
            Cryptogram cryptogram = cryptogramRepositoryImpl.findCryptogramByUserEmail(userEmail);
            isVerified = cryptogram.isVerified();
        }

        UserOptionalInfo userOptionalInfo = new UserOptionalInfo(userOptionalInfoCreateDto, uploadedImageUrls, isVerified);
        UserOptionalInfo savedUserOptionalInfo = userOptionalInfoRepository.save(userOptionalInfo);

        if (savedUserOptionalInfo == null) {
            throw new DBFailException("userOptionalInfo를 저장하는데 오류가 발생했습니다.", ErrorCode.DB_FAIL_CREATE_USER_OPTIONAL_INFO_FAIL_EXCEPTION);
        }

        LOGGER.info("[UserOptionalInfoService] UserOptionalInfo 생성 성공");

        user.setUserOptionalInfo(savedUserOptionalInfo);
        User updatedUser = userRepository.save(user);

        if (updatedUser.getUserOptionalInfo() == null) {
            throw new DBFailException("user에 userOptionalInfo를 mapping하는 과정에서 오류가 발생했습니다.", ErrorCode.DB_FAIL_UPDATE_USER_OPTIONAL_INFO_FAIL_EXCEPTION);
        }
        LOGGER.info("[UserOptionalInfoService] User에 UserOptionalInfo mapping 성공 = " + updatedUser);

        return SuccessResponse.success(SuccessCode.USER_OPTIONAL_INFO_CREATE_SUCCESS, updatedUser);
    }

    @Transactional
    public SuperResponse getUserProfile(Long userId) {
        LOGGER.info("[UserService] 유저 선택정보 조회 시도");

        UserOptionalInfo userOptionalInfo= getUserOptionalInfo(userId);
        LOGGER.info("[UserService] 유저 선택정보 조회 성공");

        return SuccessResponse.success(SuccessCode.GET_USER_OPTIONAL_INFO_SUCCESS, new UserProfileDto(userOptionalInfo));
    }

    @Transactional
    public SuperResponse updateUserProfile(Long userId, UserProfileUpdateDto userProfileUpdateDto, List<MultipartFile> multipartFiles) {
        LOGGER.info("[UserService] 유저 프로필 업데이트 시도");

        UserOptionalInfo userOptionalInfo = getUserOptionalInfo(userId);
        LOGGER.info("[UserService] 유저 선택사항 조회 성공");

        userOptionalInfo.setDescription(userProfileUpdateDto.getDescription());
        userOptionalInfo.setLanguages(userProfileUpdateDto.getLanguages());
        userOptionalInfo.setAreas(userProfileUpdateDto.getAreas());
        userOptionalInfo.setJob(userProfileUpdateDto.getJob());
        userOptionalInfo.setUniversityEmail(userProfileUpdateDto.getUniversityEmail());
        userOptionalInfo.setCompany(userProfileUpdateDto.getCompany());
        userOptionalInfo.setImageUrls(uploadImage(userProfileUpdateDto.getUploadedImageFiles(), multipartFiles));
        userOptionalInfo.setCertified(userProfileUpdateDto.isCertified());

        UserOptionalInfo updatedUserOptionalInfo = userOptionalInfoRepository.save(userOptionalInfo);

//        if (userOptionalInfo.equals(updatedUserOptionalInfo)) {
//            throw new DBFailException("유저 선택사항 업데이트 과정에서 오류가 발생했습니다.", ErrorCode.DB_FAIL_UPDATE_USER_OPTIONAL_INFO_FAIL_EXCEPTION);
//        }

        return SuccessResponse.success(SuccessCode.USER_OPTIONAL_INFO_UPDATE_SUCCESS, null);
    }

    private List<String> uploadImage(List<String> uploadedImageUrls, List<MultipartFile> multipartFiles) {
        LOGGER.info("[TourService] 투어 썸네일 s3에 upload 시도");
        List<String> profileImages = new ArrayList<>();

        if (multipartFiles != null) {
            for (MultipartFile multipartFile : multipartFiles) {
                try {
                    profileImages.add(awss3Component.convertAndUploadFiles(multipartFile, "profile/image"));
                } catch (IOException ioException) {
                    throw new IOFailException("이미지 저장 과정에서 오류가 발생했습니다.", ErrorCode.DB_FAIL_UPLOAD_IMAGE_FAIL_EXCEPTION);
                }
            }
        }
        else {
            profileImages.addAll(uploadedImageUrls);
        }

        LOGGER.info("[TourService] 투어 썸네일 s3에 upload 완료 = " + profileImages);

        return profileImages;
    }

    private UserOptionalInfo getUserOptionalInfo(Long userId) {
        UserOptionalInfo userOptionalInfo = userOptionalInfoRepositoryImpl.findUserOptionalInfoByUserId(userId);
        if (userOptionalInfo == null) {
            throw new NotFoundException("해당 유저의 선택사항을 찾을 수 없습니다.", ErrorCode.NOT_FOUND_USER_OPTIONAL_INFO_EXCEPTION);
        }

        return userOptionalInfo;
    }

}
