package com.koing.server.koing_server.service.sms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.InternalServerException;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.service.sms.dto.SMSRequestDto;
import com.koing.server.koing_server.service.sms.dto.SMSResponseDto;
import com.koing.server.koing_server.service.sms.dto.SMSSendDto;
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
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SMSService {

    private final Logger LOGGER = LoggerFactory.getLogger(SMSService.class);

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

        String certificationNumber = createCertificationNumber();
        String content = createContent(certificationNumber);

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

    private String createContent(String certificationNumber) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("안녕하세요!\n");
        stringBuffer.append("KOING 회원가입 인증번호는 " + certificationNumber + "입니다.");

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

}
