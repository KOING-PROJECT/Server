package com.koing.server.koing_server.service.account.component;

import com.koing.server.koing_server.common.encryption.KISA_SEED_CBC;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class SEED_CBC {

    private final Charset UTF_8 = StandardCharsets.UTF_8;

    @Value("${account.privacy.secret}")
    private byte[] accountSecret;

    @Value("${account.privacy.vector}")
    private byte[] accountVector;

    public String encrypt(String messageData) {
        Base64.Encoder encoder = Base64.getEncoder();

        byte[] byteDate = messageData.getBytes(UTF_8);
        byte[] encryptedData = KISA_SEED_CBC.SEED_CBC_Encrypt(accountSecret, accountVector, byteDate, 0, byteDate.length);

        return new String(encoder.encode(encryptedData), UTF_8);
    }

    public String decrypt(String encryptedData) {
        Base64.Decoder decoder = Base64.getDecoder();

        byte[] decryptedData = decoder.decode(encryptedData);
        byte[] messageData = KISA_SEED_CBC.SEED_CBC_Decrypt(accountSecret, accountVector, decryptedData, 0, decryptedData.length);

        return new String(messageData, UTF_8);
    }

}
