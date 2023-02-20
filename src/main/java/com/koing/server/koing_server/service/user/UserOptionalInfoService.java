package com.koing.server.koing_server.service.user;

import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.common.exception.DBFailException;
import com.koing.server.koing_server.common.exception.IOFailException;
import com.koing.server.koing_server.common.exception.NotFoundException;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.domain.user.UserOptionalInfo;
import com.koing.server.koing_server.domain.user.repository.UserOptionalInfoRepository;
import com.koing.server.koing_server.domain.user.repository.UserRepository;
import com.koing.server.koing_server.domain.user.repository.UserRepositoryImpl;
import com.koing.server.koing_server.service.s3.AWSS3Service;
import com.koing.server.koing_server.service.s3.component.AWSS3Component;
import com.koing.server.koing_server.service.user.dto.UserOptionalInfoCreateDto;
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
    private final UserRepositoryImpl userRepositoryImpl;
    private final UserRepository userRepository;
    private final AWSS3Component awss3Component;

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
                }
            }
        }

        LOGGER.info("[UserOptionalInfoService] 프로필 이미지 s3에 upload 완료 = " + uploadedImageUrls);
        UserOptionalInfo userOptionalInfo = new UserOptionalInfo(userOptionalInfoCreateDto, uploadedImageUrls);
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

}
