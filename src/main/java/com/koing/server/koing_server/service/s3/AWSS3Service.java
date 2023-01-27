package com.koing.server.koing_server.service.s3;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.FileConvertException;
import com.koing.server.koing_server.common.exception.InternalServerException;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.service.s3.component.AWSS3Component;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AWSS3Service {

    private final Logger LOGGER = LoggerFactory.getLogger(AWSS3Service.class);
    private final AWSS3Component awss3Component;

    public SuperResponse uploadFile(MultipartFile multipartFile) throws FileConvertException {

        LOGGER.info("[AWSS3Service] 파일 업로드 시도");

        try {
            String uploadedUrl = awss3Component.convertAndUploadFiles(multipartFile, "static");
            LOGGER.info("[AWSS3Service] 파일 업로드 성공");
            return SuccessResponse.success(SuccessCode.UPLOAD_S3_SUCCESS, uploadedUrl);
        } catch (IOException ioException) {
            throw new FileConvertException("파일 변환과정에서 오류가 발생했습니다.");
        } catch (FileConvertException fileConvertException) {
            throw new FileConvertException("파일 변환과정에서 오류가 발생했습니다.");
        } catch (Exception exception) {
            throw new InternalServerException("예상치 못한 서버 에러가 발생했습니다.");
        }
    }

}
