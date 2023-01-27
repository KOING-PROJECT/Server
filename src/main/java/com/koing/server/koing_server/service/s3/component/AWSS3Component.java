package com.koing.server.koing_server.service.s3.component;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.koing.server.koing_server.common.exception.FileConvertException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AWSS3Component {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3Client amazonS3Client;
    private final Logger LOGGER = LoggerFactory.getLogger(AWSS3Component.class);

    public String convertAndUploadFiles(MultipartFile multipartFile, String s3DirName) throws IOException {
        File uploadFile = convertFile(multipartFile)
                .orElseThrow(() -> new FileConvertException("파일 변환과정에서 오류가 발생했습니다."));
        return upload(uploadFile, s3DirName);
    }

    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeLocalFile(uploadFile);

        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));

        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeLocalFile(File targetFile) {
        if (targetFile.delete()) {
            LOGGER.info("[AWSS3Component] Local 파일 삭제 성공");
            return;
        }
        LOGGER.info("[AWSS3Component] Local 파일 삭제 실패");
    }

    private Optional<File> convertFile(MultipartFile multipartFile) throws IOException {
        File convertFile = new File(System.getProperty("user.dir") + "/" + multipartFile.getOriginalFilename());

        if (convertFile.createNewFile()) {
            try (FileOutputStream fileOutputStream = new FileOutputStream(convertFile)) {
                fileOutputStream.write(multipartFile.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

}
