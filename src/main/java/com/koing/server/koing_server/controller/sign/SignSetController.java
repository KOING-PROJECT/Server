package com.koing.server.koing_server.controller.sign;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.service.sign.SignService;
import com.koing.server.koing_server.service.sign.SignSetService;
import com.koing.server.koing_server.service.sign.dto.SignUpRequestDto;
import com.koing.server.koing_server.service.sign.dto.SignUpSetCreateDto;
import com.koing.server.koing_server.service.user.UserOptionalInfoService;
import com.koing.server.koing_server.service.user.dto.UserOptionalInfoCreateDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags = "SignSet")
@RequestMapping("/sign-set")
@RestController
@RequiredArgsConstructor
public class SignSetController {

    private final Logger LOGGER = LoggerFactory.getLogger(SignController.class);
    private final SignSetService signSetService;

    @ApiOperation("signUpSet : 회원가입과 유저선택 정보를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "sign-up-set : 회원가입과 유저선택 정보 생성 성공"),
            @ApiResponse(code = 402, message = "회원가입 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(code = 404, message = "해당 유저를 찾을 수 없습니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping(value = "/sign-up",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public SuperResponse signUpSet(
            @RequestPart(value = "imageFiles", required = false) List<MultipartFile> imageFiles,
            @RequestPart SignUpSetCreateDto signUpSetCreateDto
    ) {
        LOGGER.info("[SignController] 회원가입 시도");

        SuperResponse signUpSetResponse;

        try {
            signUpSetResponse = signSetService.signUpSet(signUpSetCreateDto, imageFiles);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[SignController] 회원가입 성공");

        return signUpSetResponse;
    }
}
