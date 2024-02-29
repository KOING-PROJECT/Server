package com.koing.server.koing_server.service.account.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.Base64;

@Component
public class AES_Encryption {

    private String alg;

    private String key;

    private static final int IV_SIZE = 16;

    public AES_Encryption(@Value("${account.AES.alg}") String algorithm, @Value("${account.AES.key}") String accountSecretString) {
        this.alg = algorithm;
        this.key = accountSecretString;
    }

    public String encrypt(String plainText) throws Exception {
        SecretKey keyspec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

        //set iv as random 16byte
        byte[] iv = new byte[IV_SIZE];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        AlgorithmParameterSpec ivspec = new IvParameterSpec(iv);

        // Encryption
        Cipher cipher = Cipher.getInstance(alg);
        cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);

        int blockSize = 128; //block size
        byte[] dataBytes = plainText.getBytes("UTF-8");

        //find fillChar & pad
        int plaintextLength = dataBytes.length;
        int fillChar = ((blockSize - (plaintextLength % blockSize)));
        plaintextLength += fillChar; //pad

        byte[] plaintext = new byte[plaintextLength];
        Arrays.fill(plaintext, (byte) fillChar);
        System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

        //encrypt
        byte[] cipherBytes = cipher.doFinal(plaintext);

        //add iv to front of cipherBytes
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
        outputStream.write( iv );
        outputStream.write( cipherBytes );

        //encode into base64
        byte [] encryptedIvText = outputStream.toByteArray();
        return new String(Base64.getEncoder().encode(encryptedIvText), "UTF-8");
    }

    public String decrypt(String encryptedIvText) throws Exception {
        //decode with base64 decoder
        byte [] encryptedIvTextBytes = Base64.getDecoder().decode(encryptedIvText);

        // Extract IV.
        byte[] iv = new byte[IV_SIZE];
        System.arraycopy(encryptedIvTextBytes, 0, iv, 0, iv.length);

        // Extract encrypted part.
        int encryptedSize = encryptedIvTextBytes.length - IV_SIZE;
        byte[] encryptedBytes = new byte[encryptedSize];
        System.arraycopy(encryptedIvTextBytes, IV_SIZE, encryptedBytes, 0, encryptedSize);

        // Decryption
        Cipher cipher = Cipher.getInstance(alg);
        SecretKey keyspec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
        AlgorithmParameterSpec ivspec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
        byte[] aesdecode = cipher.doFinal(encryptedBytes);

        // unpad
        byte[] origin = new byte[aesdecode.length - (aesdecode[aesdecode.length - 1])];
        System.arraycopy(aesdecode, 0, origin, 0, origin.length);

        return new String(origin, "UTF-8");
    }
}
