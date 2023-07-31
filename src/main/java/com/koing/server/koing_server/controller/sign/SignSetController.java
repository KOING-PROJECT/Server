package com.koing.server.koing_server.controller.sign;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.service.sign.SignSetService;
import com.koing.server.koing_server.service.sign.dto.SignUpSetCreateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "SignSet", description = "SignSet API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/sign-set")
public class SignSetController {

    private final Logger LOGGER = LoggerFactory.getLogger(SignController.class);
    private final SignSetService signSetService;

    @Operation(description = "signUpSet : 회원가입과 유저선택 정보를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "sign-up-set : 회원가입과 유저선택 정보 생성 성공"),
            @ApiResponse(responseCode = "402", description = "회원가입 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(responseCode = "404", description = "해당 유저를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
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
