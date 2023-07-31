package com.koing.server.koing_server.controller.s3;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.FileConvertException;
import com.koing.server.koing_server.service.s3.AWSS3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "AWS-S3", description = "AWS-S3 API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/s3")
public class AWSS3Controller {

    private final Logger LOGGER = LoggerFactory.getLogger(AWSS3Controller.class);
    private final AWSS3Service awss3Service;

    @Operation(description = "AWSS3 : 파일을 업로드 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "AWS-S3 : 파일 업로드 성공"),
            @ApiResponse(responseCode = "408", description = "파일 변환과정에서 오류가 발생했습니다."),
            @ApiResponse(responseCode = "500", description = "예상치 못한 서버 에러가 발생했습니다.")
    })
    @PostMapping("/upload")
    public SuperResponse uploadFile(@RequestParam("images") MultipartFile multipartFile) {
        LOGGER.info("[AWSS3Controller] 파일 업로드 시도");

        SuperResponse uploadFileResponse;
        try {
            uploadFileResponse = awss3Service.uploadFile(multipartFile);
        } catch (FileConvertException fileConvertException) {
            return ErrorResponse.error(ErrorCode.FILE_CONVERT_ERROR);
        } catch (Exception exception) {
            return ErrorResponse.error(ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }

        return uploadFileResponse;
    }

}
