package com.koing.server.koing_server.service.cryptogram;

import com.koing.server.koing_server.common.dto.ErrorResponse;
import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.DBFailException;
import com.koing.server.koing_server.common.exception.NotFoundException;
import com.koing.server.koing_server.common.exception.UnAuthorizedException;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.domain.cryptogram.Cryptogram;
import com.koing.server.koing_server.domain.cryptogram.repository.CryptogramRepository;
import com.koing.server.koing_server.domain.cryptogram.repository.CryptogramRepositoryImpl;
import com.koing.server.koing_server.service.cryptogram.dto.CryptogramVerifyDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CryptogramService {

    private final Logger LOGGER = LoggerFactory.getLogger(CryptogramService.class);
    private final CryptogramRepositoryImpl cryptogramRepositoryImpl;
    private final CryptogramRepository cryptogramRepository;

    public boolean hasCryptogram(String userEmail) {
        boolean hasCryptogram = cryptogramRepositoryImpl.hasCryptogramByUserEmail(userEmail);
        LOGGER.info("[CryptogramService] 요청한 User의 cryptogram 존재여부 = " + hasCryptogram);

        return hasCryptogram;
    }

    public Cryptogram getCryptogram(String userEmail) {
        Cryptogram cryptogram = cryptogramRepositoryImpl.findCryptogramByUserEmail(userEmail);

        LOGGER.info("[CryptogramService] 요청한 User의 cryptogram 가져오기 = " + cryptogram);

        return cryptogram;
    }

    public Cryptogram createCryptogram(String cipher, String targetEmail) {
        LOGGER.info("[CryptogramService] cipher %s = " + cipher);
        Cryptogram cryptogram = Cryptogram.builder()
                .cryptogram(cipher)
                .targetEmail(targetEmail)
                .verified(false)
                .build();

        LOGGER.info("[CryptogramService] cryptogram 생성 시도");
        Cryptogram savedCryptogram = cryptogramRepository.save(cryptogram);

        if (savedCryptogram.getCryptogram() == null) {
            throw new DBFailException("Cryptogram 생성에 실패했습니다. 다시 시도해 주세요.");
        }

        LOGGER.info("[CryptogramService] cryptogram 생성 성공 %s = " + savedCryptogram);

        return savedCryptogram;
    }

    public Cryptogram updateCryptogram(String cipher, String userEmail) {

        Cryptogram cryptogram = getCryptogram(userEmail);

//        String newCipher = createCipher();
//        LOGGER.info("[CryptogramService] new cipher 생성 %s = " + cipher);

        cryptogram.setCryptogram(cipher);
        LOGGER.info("[CryptogramService] cryptogram update 시도");

        Cryptogram updatedCryptogram = cryptogramRepository.save(cryptogram);
        if (!updatedCryptogram.getCryptogram().equals(cipher)) {
            throw new DBFailException("Cryptogram 업데이트에 실패했습니다. 다시 시도해 주세요.");
        }
        LOGGER.info("[CryptogramService] cryptogram update 성공 %s = " + updatedCryptogram);

        return updatedCryptogram;
    }

    public SuperResponse verifyCryptogram(CryptogramVerifyDto cryptogramVerifyDto) {

        String userEmail = cryptogramVerifyDto.getUserEmail();
        String inputCryptogram = cryptogramVerifyDto.getCryptogram();

        if (!hasCryptogram(userEmail)) {
            throw new NotFoundException("해당 이메일에 Cryptogram이 없습니다. 이메일 인증을 요청해주세요.", ErrorCode.NOT_FOUND_CRYPTOGRAM_EXCEPTION);
        }
        LOGGER.info("[CryptogramService] cryptogram 존재 확인");

        Cryptogram cryptogram = getCryptogram(userEmail);

        if (!cryptogram.getCryptogram().equals(inputCryptogram)) {
            throw new UnAuthorizedException("Cryptogram이 일치하지 않습니다.", ErrorCode.UNAUTHORIZED_CRYPTOGRAM_NOT_MATCH_EXCEPTION);
        }
        LOGGER.info("[CryptogramService] cryptogram 일치 확인");

        if(!LocalDateTime.now().isBefore(cryptogram.getUpdatedAt().plusMinutes(3))) {
            // 유효기간 만료된 토큰
            throw new UnAuthorizedException("Cryptogram이 만료되었습니다. 이메일 인증을 다시 요청해주세요.", ErrorCode.UNAUTHORIZED_CRYPTOGRAM_EXPIRE_EXCEPTION);
        }
        LOGGER.info("[CryptogramService] cryptogram 만료되지 않음을 확인");

        cryptogram.setVerified(true);
        Cryptogram updatedCryptogram = cryptogramRepository.save(cryptogram);

        if (!updatedCryptogram.isVerified()) {
            throw new DBFailException("Cryptogram 인증과정에서 오류가 발생했습니다.", ErrorCode.DB_FAIL_VERIFY_CRYPTOGRAM_FAIL_EXCEPTION);
        }
        LOGGER.info("[CryptogramService] cryptogram 인증 완료");

        return SuccessResponse.success(SuccessCode.CRYPTOGRAM_CERTIFICATION_SUCCESS, updatedCryptogram.isVerified());
    }

    public void deleteCryptogram(String userEmail) {
        // 회원가입 완료하면 삭제하도록
        Cryptogram cryptogram = getCryptogram(userEmail);
        cryptogramRepository.delete(cryptogram);
    }

    public String createCipher() {
        // 8자리 문자
//        int leftLimit = 97; // letter 'a'
//        int rightLimit = 122; // letter 'z'
//        int targetStringLength = 8;
//        Random random = new Random();
//
//        String cipher = random.ints(leftLimit, rightLimit + 1)
//                .limit(targetStringLength)
//                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
//                .toString().toUpperCase();

//        return cipher;

        // 4자리 숫자
        int createNum = 0;
        int targetNumberLength = 4;
        Random random = new Random();
        String cipher = "";

        for (int i = 0; i < targetNumberLength; i++) {
            createNum = random.nextInt(9);
            cipher += Integer.toString(createNum);
        }

        return cipher;
    }

}
