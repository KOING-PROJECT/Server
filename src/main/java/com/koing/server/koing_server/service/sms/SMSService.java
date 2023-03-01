package com.koing.server.koing_server.service.sms;

import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.service.sms.dto.SMSRequestDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

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

    public SuperResponse sendSMS() {

        String path = String.format("/services/%s/messages", serviceId);

        URI uri = UriComponentsBuilder
                .fromUriString("https://sens.apigw.ntruss.com/sms/v2")
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
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            e.printStackTrace();
        } catch (InvalidKeyException invalidKeyException) {
            e.printStackTrace();
        }

        if (signature.equals(null)) {

        }

        RequestEntity<SMSRequestDto> requestEntity = RequestEntity
                .post(uri)
                .contentType(mediaType)
                .header("x-ncp-apigw-timestamp", time)
                .header("x-ncp-iam-access-key", accessKey)
                .header()


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

}
