package com.koing.server.koing_server.service.sms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.DBFailException;
import com.koing.server.koing_server.common.exception.InternalServerException;
import com.koing.server.koing_server.common.exception.NotFoundException;
import com.koing.server.koing_server.common.exception.UnAuthorizedException;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.domain.cryptogram.Cryptogram;
import com.koing.server.koing_server.domain.sms.SMS;
import com.koing.server.koing_server.domain.sms.repository.SMSRepository;
import com.koing.server.koing_server.domain.sms.repository.SMSRepositoryImpl;
import com.koing.server.koing_server.service.sms.dto.SMSRequestDto;
import com.koing.server.koing_server.service.sms.dto.SMSResponseDto;
import com.koing.server.koing_server.service.sms.dto.SMSSendDto;
import com.koing.server.koing_server.service.sms.dto.SMSVerifyDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SMSService {

    private final Logger LOGGER = LoggerFactory.getLogger(SMSService.class);
    private final SMSRepository smsRepository;
    private final SMSRepositoryImpl smsRepositoryImpl;

    @Value("${naver.cloud.platform.service.id}")
    private String serviceId;

    @Value("${naver.cloud.platform.access-key}")
    private String accessKey;

    @Value("${naver.cloud.platform.secret-key}")
    private String secretKey;

    @Value("${naver.cloud.platform.from-phone-number}")
    private String fromPhoneNumber;

    public SuperResponse sendSMS(SMSRequestDto smsRequestDto) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {

        LOGGER.info("[SMSService] 인증번호 전송 시도");

        String path = String.format("/sms/v2/services/%s/messages", serviceId);

        URI uri = UriComponentsBuilder
                .fromUriString("https://sens.apigw.ntruss.com")
                .path(path)
                .encode()
                .build()
                .toUri();

        Charset utf8 = Charset.forName("UTF-8");
        MediaType mediaType = new MediaType("application", "json", utf8);
        String time = Long.toString(System.currentTimeMillis());

        String signature = null;

        try {
            signature = makeSignature(path, time);
        } catch (java.io.UnsupportedEncodingException unsupportedEncodingException) {
            throw new UnsupportedEncodingException();
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new NoSuchAlgorithmException();
        } catch (InvalidKeyException invalidKeyException) {
            throw new InvalidKeyException();
        }

        if (signature.equals(null)) {
            throw new InternalServerException("예상치 못한 서버 에러가 발생하였습니다.", ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }

        SMS sms;
        String targetPhoneNumber = smsRequestDto.getCountryCode() + "-" + smsRequestDto.getReceivePhoneNumber();
        String certificationNumber = createCertificationNumber();

        if (!smsRepositoryImpl.hasSMSByTargetPhoneNumber(targetPhoneNumber)) {
            sms = createSMS(targetPhoneNumber, certificationNumber);
        }
        else {
            sms = updateSMS(targetPhoneNumber, certificationNumber);
        }

        if (sms == null) {
            throw new InternalServerException("예상치 못한 서버 에러가 발생하였습니다.", ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }

        String content = createContent(sms.getCertificationNumber(), smsRequestDto.getCountryCode());

        SMSSendDto smsSendDto = createSMSSend(smsRequestDto.getCountryCode(), fromPhoneNumber, content, smsRequestDto.getReceivePhoneNumber());

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(smsSendDto);

        RequestEntity<String> requestEntity = RequestEntity
                .post(uri)
                .contentType(mediaType)
                .header("x-ncp-apigw-timestamp", time)
                .header("x-ncp-iam-access-key", accessKey)
                .header("x-ncp-apigw-signature-v2", signature)
                .body(jsonBody);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        ResponseEntity<SMSResponseDto> responseEntity = restTemplate.exchange(requestEntity, SMSResponseDto.class);

        if (responseEntity.getStatusCodeValue() != 202) {
            throw new InternalServerException("예상치 못한 서버 에러가 발생하였습니다.", ErrorCode.INTERNAL_SERVER_EXCEPTION);
        }

        LOGGER.info("[SMSService] 인증번호 전송 성공");

        return SuccessResponse.success(SuccessCode.SMS_SEND_SUCCESS, responseEntity.getBody());
    }

    public SuperResponse verifySMS(SMSVerifyDto smsVerifyDto) {

        LOGGER.info("[SMSService] 인증번호 인증 시도");

        String targetPhoneNumber = smsVerifyDto.getCountryCode() + "-" + smsVerifyDto.getReceivePhoneNumber();
        String certificationNumber = smsVerifyDto.getCertificationNumber();

        if (!smsRepositoryImpl.hasSMSByTargetPhoneNumber(targetPhoneNumber)) {
            throw new NotFoundException("해당 번호의 문자인증을 찾을 수 없습니다.", ErrorCode.NOT_FOUND_SMS_EXCEPTION);
        }

        SMS sms = smsRepositoryImpl.findSMSByTargetPhoneNumber(targetPhoneNumber);

        if (!sms.getCertificationNumber().equals(certificationNumber)) {
            throw new UnAuthorizedException("인증번호가 일치하지 않습니다.", ErrorCode.UNAUTHORIZED_CERTIFICATION_NUMBER_EXCEPTION);
        }
        LOGGER.info("[SMSService] 인증번호 일치 확인");

        if(!LocalDateTime.now().isBefore(sms.getUpdatedAt().plusMinutes(3))) {
            // 유효기간 만료된 인증번호
            throw new UnAuthorizedException("인증번호가 만료되었습니다. 휴대폰 인증을 다시 요청해주세요.", ErrorCode.UNAUTHORIZED_CERTIFICATION_NUMBER_EXPIRE_EXCEPTION);
        }
        LOGGER.info("[SMSService] 인증번호가 만료되지 않음을 확인");

        sms.setVerified(true);
        SMS updatedSMS = smsRepository.save(sms);

        if (!updatedSMS.isVerified()) {
            throw new DBFailException("문자 인증 과정에서 오류가 발생했습니다.", ErrorCode.DB_FAIL_VERIFY_SMS_FAIL_EXCEPTION);
        }

        LOGGER.info("[SMSService] 인증번호 인증 완료");

        return SuccessResponse.success(SuccessCode.SMS_CERTIFICATION_SUCCESS, updatedSMS.isVerified());
    }

    private String makeSignature(String path, String time) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        String space = " ";					// one space
        String newLine = "\n";					// new line
        String method = "POST";					// method
        String url = path;	// url (include query string)

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(time)
                .append(newLine)
                .append(accessKey)
                .toString();

        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        String encodeBase64String = Base64.encodeBase64String(rawHmac);

        return encodeBase64String;
    }

    private SMSSendDto createSMSSend(String countryCode, String fromPhoneNumber, String content, String receivePhoneNumber) {
        SMSSendDto SMSSendDto = new SMSSendDto(countryCode, fromPhoneNumber, content, receivePhoneNumber);

        return SMSSendDto;
    }

    private String createContent(String certificationNumber, String countryCode) {
        StringBuffer stringBuffer = new StringBuffer();

        if (countryCode.equals("82")) {
            stringBuffer.append("안녕하세요!\n");
            stringBuffer.append("KOING 회원가입 인증번호는 " + certificationNumber + "입니다.");
        }
        else {
            stringBuffer.append("[KOING] verification: " + certificationNumber);
        }

        return stringBuffer.toString();
    }

    private String createCertificationNumber() {
        // 4자리 숫자
        int createNum = 0;
        int targetNumberLength = 4;
        Random random = new Random();
        String certificationNumber = "";

        for (int i = 0; i < targetNumberLength; i++) {
            createNum = random.nextInt(9);
            certificationNumber += Integer.toString(createNum);
        }

        return certificationNumber;
    }

    private SMS createSMS(String targetPhoneNumber, String certificationNumber) {
        LOGGER.info("[SMSService] 인증번호 생성 시도");

        SMS sms = SMS.builder()
                .certificationNumber(certificationNumber)
                .targetPhoneNumber(targetPhoneNumber)
                .verified(false)
                .build();

        SMS savedSMS = smsRepository.save(sms);

        if (savedSMS == null) {
            throw new DBFailException("문자 인증을 생성하는 과정에서 오류가 발생했습니다.", ErrorCode.DB_FAIL_CREATE_SMS_FAIL_EXCEPTION);
        }
        LOGGER.info("[SMSService] 인증번호 생성 완료");

        return savedSMS;
    }

    private SMS updateSMS(String targetPhoneNumber, String newCertificationNumber) {
        LOGGER.info("[SMSService] 인증번호 생성 시도");

        SMS sms = smsRepositoryImpl.findSMSByTargetPhoneNumber(targetPhoneNumber);
        sms.setCertificationNumber(newCertificationNumber);

        SMS updatedSMS = smsRepository.save(sms);

        if (updatedSMS.getCertificationNumber() != newCertificationNumber) {
            throw new DBFailException("문자 인증을 업데이트하는 과정에서 오류가 발생했습니다.", ErrorCode.DB_FAIL_CREATE_SMS_FAIL_EXCEPTION);
        }
        LOGGER.info("[SMSService] 인증번호 업데이트 시도");

        return updatedSMS;
    }

}
