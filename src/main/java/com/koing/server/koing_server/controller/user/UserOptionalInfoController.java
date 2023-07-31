package com.koing.server.koing_server.controller.user;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.service.user.UserOptionalInfoService;
import com.koing.server.koing_server.service.user.dto.UserOptionalInfoCreateDto;
import com.koing.server.koing_server.service.user.dto.UserProfileUpdateDto;
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

@Tag(name = "UserOptionalInfo", description = "UserOptionalInfo API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user-optional-info")
public class UserOptionalInfoController {

    private final Logger LOGGER = LoggerFactory.getLogger(UserOptionalInfoController.class);
    private final UserOptionalInfoService userOptionalInfoService;

    @Operation(description = "UserOptionalInfo - 유저 선택정보를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "UserOptionalInfo - 유저 선택정보 생성 성공"),
            @ApiResponse(responseCode = "402", description = "유저 선택정보 생성 과정에서 오류가 발생했습니다."),
            @ApiResponse(responseCode = "402", description = "유저 선택정보 업데이트 과정에서 오류가 발생했습니다."),
            @ApiResponse(responseCode = "404", description = "해당 유저를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping("")
    public SuperResponse createUserOptionalInfo(@RequestBody UserOptionalInfoCreateDto userOptionalInfoCreateDto) {
        LOGGER.info("[UserOptionalInfoController] 유저 선택정보 생성 시도");
        SuperResponse userOptionalInfoResponse;
        try {
            userOptionalInfoResponse = userOptionalInfoService.createUserOptionalInfo(userOptionalInfoCreateDto);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[UserOptionalInfoController] 유저 선택정보 생성 성공");
        return userOptionalInfoResponse;
    }


    @Operation(description = "UserOptionalInfo - 유저 선택정보를 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "UserOptionalInfo - 투어 선택정보 업데이트 성공"),
            @ApiResponse(responseCode = "402", description = "이미지 저장 과정에서 오류가 발생했습니다."),
            @ApiResponse(responseCode = "404", description = "해당 유저의 선택사항을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PatchMapping(value = "/{userId}",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public SuperResponse updateTour(
            @PathVariable("userId") Long userId,
            @RequestPart(value = "profileImages", required = false) List<MultipartFile> profileImages,
            @RequestPart UserProfileUpdateDto userProfileUpdateDto
    ) {
        // 완성 된 tour를 수정하거나, 임시 저장 중인 tour를 다시 임시 저장 할 때 사용
        LOGGER.info("[UserOptionalInfoController] 유저 선택정보 update 시도");
        SuperResponse updateUserOptionalInfoResponse;
        try {
            updateUserOptionalInfoResponse = userOptionalInfoService.updateUserProfile(userId, userProfileUpdateDto, profileImages);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[UserOptionalInfoController] 유저 선택정보 update 성공");

        return updateUserOptionalInfoResponse;
    }


    @Operation(description = "UserOptionalInfo - 유저 선택정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "UserOptionalInfo - 유저 선택정보 조회 성공"),
            @ApiResponse(responseCode = "404", description = "해당 유저의 선택사항을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @GetMapping("/{userId}")
    public SuperResponse getUserOptionalInfo(@PathVariable("userId") Long userId) {
        // 완성 된 tour를 수정하거나, 임시 저장 중인 tour를 다시 임시 저장 할 때 사용
        LOGGER.info("[UserOptionalInfoController] 유저 선택정보 조회 시도");
        SuperResponse getUserOptionalInfoResponse;
        try {
            getUserOptionalInfoResponse = userOptionalInfoService.getUserProfile(userId);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            System.out.println(exception);
            System.out.println(exception.getMessage());
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[UserOptionalInfoController] 유저 선택정보 조회 성공");

        return getUserOptionalInfoResponse;
    }

}
