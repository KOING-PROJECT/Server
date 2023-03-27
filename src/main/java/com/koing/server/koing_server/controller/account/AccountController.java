package com.koing.server.koing_server.controller.account;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.controller.review.ReviewController;
import com.koing.server.koing_server.service.account.AccountService;
import com.koing.server.koing_server.service.account.dto.AccountCreateDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Account")
@RequestMapping("/account")
@RestController
@RequiredArgsConstructor
public class AccountController {

    private final Logger LOGGER = LoggerFactory.getLogger(ReviewController.class);
    private final AccountService accountService;

    @ApiOperation("Account - 계좌 정보를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Account - 계좌 정보 생성 성공"),
            @ApiResponse(code = 402, message = "계좌 생성 과정에서 오류가 발생했습니다."),
            @ApiResponse(code = 402, message = "유저 업데이트 과정에서 오류가 발생했습니다. 다시 시도해 주세요."),
            @ApiResponse(code = 404, message = "해당 유저를 찾을 수 없습니다."),
            @ApiResponse(code = 406, message = "해당 유저의 계좌 정보가 이미 존재합니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping("")
    public SuperResponse createAccount(@RequestBody AccountCreateDto accountCreateDto) {
        LOGGER.info("[AccountController] 계좌 정보 생성 시도");
        SuperResponse accountCreateResponse;
        try {
            accountCreateResponse = accountService.createAccount(accountCreateDto);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[AccountController] 계좌 정보 생성 성공");

        return accountCreateResponse;
    }


    @ApiOperation("Account - 계좌 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Account - 계좌 정보 조회 성공"),
            @ApiResponse(code = 404, message = "해당 유저의 계좌 정보를 찾을 수 없습니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @GetMapping("/{userId}")
    public SuperResponse getAccountInfo(@PathVariable("userId") Long userId) {
        LOGGER.info("[AccountController] 계좌 정보 조회 시도");
        SuperResponse accountGetResponse;
        try {
            accountGetResponse = accountService.getAccountInfo(userId);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[AccountController] 계좌 정보 조회 성공");

        return accountGetResponse;
    }

    @ApiOperation("Account - 계좌 정보를 업데이트 합니다.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Account - 계좌 정보 업데이트 성공"),
            @ApiResponse(code = 402, message = "계좌 업데이트 과정에서 오류가 발생했습니다."),
            @ApiResponse(code = 404, message = "해당 유저의 계좌 정보를 찾을 수 없습니다."),
            @ApiResponse(code = 406, message = "수정할 계좌 정보가 없습니다."),
            @ApiResponse(code = 500, message = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PatchMapping("")
    public SuperResponse updateAccount(@RequestBody AccountCreateDto accountCreateDto) {
        LOGGER.info("[AccountController] 계좌 정보 업데이트 시도");
        SuperResponse accountUpdateResponse;
        try {
            accountUpdateResponse = accountService.updateAccountInfo(accountCreateDto);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            System.out.println(exception);
            System.out.println(exception.getMessage());
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[AccountController] 계좌 정보 업데이트 성공");

        return accountUpdateResponse;
    }

}
