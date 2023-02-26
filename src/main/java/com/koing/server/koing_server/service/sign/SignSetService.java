package com.koing.server.koing_server.service.sign;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.common.exception.InternalServerException;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.service.sign.dto.SignUpRequestDto;
import com.koing.server.koing_server.service.sign.dto.SignUpSetCreateDto;
import com.koing.server.koing_server.service.user.UserOptionalInfoService;
import com.koing.server.koing_server.service.user.dto.UserOptionalInfoCreateDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SignSetService {

    private final Logger LOGGER = LoggerFactory.getLogger(SignService.class);
    private final SignService signService;
    private final UserOptionalInfoService userOptionalInfoService;

    @Transactional
    public SuperResponse signUpSet(SignUpSetCreateDto signUpSetCreateDto, List<MultipartFile> imageFiles) {
        LOGGER.info("[SignSetService] 회원가입 시도");

        SignUpRequestDto signUpRequestDto = new SignUpRequestDto(signUpSetCreateDto);
        SuperResponse signUpResponse = signService.signUp(signUpRequestDto);

        LOGGER.info("[SignSetService] 회원가입 성공");

        LOGGER.info("[SignSetService] 유저 선택정보 생성 시도");
        if (!(signUpResponse.getData() instanceof Long)) {
            throw new InternalServerException("예상치 못한 서버 에러가 발생하였습니다.", ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }

        Long userId = (Long) signUpResponse.getData();

        UserOptionalInfoCreateDto userOptionalInfoCreateDto =
                new UserOptionalInfoCreateDto(userId, signUpSetCreateDto, imageFiles);
        SuperResponse userOptionalInfoResponse = userOptionalInfoService.createUserOptionalInfo(userOptionalInfoCreateDto);
        LOGGER.info("[SignSetService] 유저 선택정보 생성 성공");

        return SuccessResponse.success(SuccessCode.SIGN_UP_SET_SUCCESS, userOptionalInfoResponse.getData());
    }

}
