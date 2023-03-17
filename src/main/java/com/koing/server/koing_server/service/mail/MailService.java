package com.koing.server.koing_server.service.mail;

import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.exception.InternalServerException;
import com.koing.server.koing_server.common.properties.EmailProperties;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.domain.cryptogram.Cryptogram;
import com.koing.server.koing_server.service.cryptogram.CryptogramService;
import com.koing.server.koing_server.service.mail.dto.MailSendDto;
import com.koing.server.koing_server.service.sign.SignService;
import com.koing.server.koing_server.service.sign.dto.SignInTemporaryPasswordDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class MailService {

    private final Logger LOGGER = LoggerFactory.getLogger(MailService.class);
    private final CryptogramService cryptogramService;
    private final EmailProperties emailProperties;
    private final SignService signService;

    public SuperResponse sendMail(MailSendDto mailSendDto) {

        // 외부 properties 에서 email, password 가져오기
//        emailProperties.initEmailProperties();

        String sendUserEmail = emailProperties.getEmail(); // 네이버일 경우 네이버 계정, gmail경우 gmail 계정
        String password = emailProperties.getPassword();   // 패스워드
        String targetEmail = mailSendDto.getTargetEmail();

        // SMTP 서버 정보를 설정한다.
        Properties prop = setProperties();
        Session session = setSession(prop, sendUserEmail, password);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sendUserEmail));

            //수신자메일주소
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(targetEmail));

            // Subject
            message.setSubject("KOING 인증메일 입니다."); //메일 제목을 입력

            String cipher = cryptogramService.createCipher();
            Cryptogram cryptogram;
            System.out.println("cipher = " + cipher);

            if (!cryptogramService.hasCryptogram(targetEmail)) {
                cryptogram = cryptogramService.createCryptogram(cipher, targetEmail);
                LOGGER.info("[MailService] Cryptogram 생성 성공 %s = " + cryptogram);
            }
            else {
                cryptogram = cryptogramService.updateCryptogram(cipher, targetEmail);
                LOGGER.info("[MailService] Cryptogram 업데이트 성공 %s = " + cryptogram);
            }

            // Text
            StringBuffer stringBuffer = writeMessage(targetEmail, cipher);
            message.setText(stringBuffer.toString());    //메일 내용을 입력

            // send the message
            Transport.send(message); ////전송
            LOGGER.info("[MailService] Email 전송 성공");
        } catch (AddressException addressException) {
            throw new InternalServerException("AddressException이 발생했습니다.");
        } catch (MessagingException messagingException) {
            throw new InternalServerException("MessagingException이 발생했습니다.");
        } catch (Exception exception) {
            throw exception;
        }

        return SuccessResponse.success(SuccessCode.EMAIL_SEND_SUCCESS, null);
    }

    public SuperResponse sendTemporaryPassword(SignInTemporaryPasswordDto signInTemporaryPasswordDto) {

        // 외부 properties 에서 email, password 가져오기
//        emailProperties.initEmailProperties();

        String sendUserEmail = emailProperties.getEmail(); // 네이버일 경우 네이버 계정, gmail경우 gmail 계정
        String password = emailProperties.getPassword();   // 패스워드
        String targetEmail = signInTemporaryPasswordDto.getUserEmail();

        // SMTP 서버 정보를 설정한다.
        Properties prop = setProperties();
        Session session = setSession(prop, sendUserEmail, password);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sendUserEmail));

            //수신자메일주소
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(targetEmail));

            // Subject
            message.setSubject("KOING 임시 비밀번호 입니다."); //메일 제목을 입력

            String newPassword = signService.createTemporaryPassword(signInTemporaryPasswordDto);

            // Text
            StringBuffer stringBuffer = writeNewPasswordMessage(targetEmail, newPassword);
            message.setText(stringBuffer.toString());    //메일 내용을 입력

            // send the message
            Transport.send(message); ////전송
            LOGGER.info("[MailService] Email 전송 성공");
        } catch (AddressException addressException) {
            throw new InternalServerException("AddressException이 발생했습니다.");
        } catch (MessagingException messagingException) {
            throw new InternalServerException("MessagingException이 발생했습니다.");
        } catch (Exception exception) {
            throw exception;
        }

        return SuccessResponse.success(SuccessCode.CREATE_TEMPORARY_PASSWORD_SUCCESS, null);
    }

    private Properties setProperties() {
        Properties prop = new Properties();

        prop.put("mail.smtp.host", "smtp.gmail.com");
//        prop.put("mail.smtp.host", "smtp.naver.com");
        prop.put("mail.smtp.port", 465);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
//        prop.put("mail.smtp.ssl.trust", "smtp.naver.com");

        return prop;
    }

    private Session setSession(Properties prop, String senderEmail, String senderPassword) {
        return Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(senderEmail, senderPassword);
            }
        });
    }

    private StringBuffer writeMessage(String targetEmail, String cipher) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("안녕하세요!\n");
        stringBuffer.append(targetEmail + " 님의 인증문자열은 '" + cipher + "' 입니다.\n");
        stringBuffer.append("받으신 인증문자열을 인증칸에 넣어서 인증을 완료하시고 회원가입을 진행해주세요.\n");
        stringBuffer.append("감사합니다!");

        return stringBuffer;
    }

    private StringBuffer writeNewPasswordMessage(String targetEmail, String newPassword) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("안녕하세요!\n");
        stringBuffer.append(targetEmail + " 님의 임시 비밀번호는 " + newPassword + " 입니다.\n");
        stringBuffer.append("로그인 후 보안을 위해 비밀번호 변경을 진행해 주세요!.\n");

        return stringBuffer;
    }

}
