package com.koing.server.koing_server.controller.keyword;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.BoilerplateException;
import com.koing.server.koing_server.service.keyword.KeywordService;
import com.koing.server.koing_server.service.keyword.dto.KeywordCreateDto;
import com.koing.server.koing_server.service.keyword.dto.KeywordDeleteDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Keyword", description = "Keyword API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/keyword")
public class KeywordController {

    private final Logger LOGGER = LoggerFactory.getLogger(KeywordController.class);
    private final KeywordService keywordService;

    @Operation(description = "Keyword - 키워드들을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Keyword - 키워드들 생성 성공"),
            @ApiResponse(responseCode = "402", description = "키워드 생성 과정에서 오류가 발생했습니다."),
            @ApiResponse(responseCode = "406", description = "이미 존재하는 키워드 입니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping("")
    public SuperResponse createKeywords(@RequestBody KeywordCreateDto keywordCreateDto) {

        LOGGER.info("[KeywordController] 키워드들 생성 시드");

        SuperResponse createKeywordsResponse;
        try {
            createKeywordsResponse = keywordService.createKeywords(keywordCreateDto);;
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[KeywordController] 키워드들 생성 성공");

        return createKeywordsResponse;
    }

    @Operation(description = "Keyword - 키워드들을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Keyword - 키워드들 조회 성공"),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @GetMapping("")
    public SuperResponse getKeywords() {

        LOGGER.info("[KeywordController] 키워드들 조회 시드");

        SuperResponse getKeywordsResponse;
        try {
            getKeywordsResponse = keywordService.getKeywords();;
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[KeywordController] 키워드들 생성 성공");

        return getKeywordsResponse;
    }


    @Operation(description = "Keyword - 키워드들을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Keyword - 키워드들 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 키워드 입니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @DeleteMapping("")
    public SuperResponse deleteKeywords(@RequestBody KeywordDeleteDto keywordDeleteDto) {

        LOGGER.info("[KeywordController] 키워드들 삭제 시도");

        SuperResponse deleteKeywordsResponse;
        try {
            deleteKeywordsResponse = keywordService.deleteKeywords(keywordDeleteDto);;
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[KeywordController] 키워드들 삭제 성공");

        return deleteKeywordsResponse;
    }


    @Operation(description = "Keyword - 키워드 조회수를 업데이트 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Keyword - 키워드 조회수 업데이트 성공"),
            @ApiResponse(responseCode = "402", description = "키워드 업데이트 과정에서 오류가 발생했습니다."),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 키워드 입니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PatchMapping("/{keywordName}")
    public SuperResponse countKeywordSearchAmount(@PathVariable("keywordName") String keywordName) {

        LOGGER.info("[KeywordController] 키워드 조회수 업데이트 시도");

        SuperResponse countKeywordSearchAmountResponse;
        try {
            countKeywordSearchAmountResponse = keywordService.countKeywordSearchAmount(keywordName);
        } catch (BoilerplateException boilerplateException) {
            return ErrorResponse.error(boilerplateException.getErrorCode());
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }
        LOGGER.info("[KeywordController] 키워드 조회수 업데이트 성공");

        return countKeywordSearchAmountResponse;
    }

}
