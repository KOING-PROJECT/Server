package com.koing.server.koing_server.service.mail;

import com.koing.server.koing_server.common.properties.EmailProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.PasswordAuthentication;
import java.util.Properties;
import java.util.Random;
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

    private final CryptogramService cryptogramService;
    private final EmailProperties emailProperties;

    public EmailSendResponse gmailSend(EmailSendRequest emailSendRequest) {

        // 외부 properties 에서 email, password 가져오기
        emailProperties.initEmailProperties();

        String user = emailProperties.getEmail(); // 네이버일 경우 네이버 계정, gmail경우 gmail 계정
        String password = emailProperties.getPassword();   // 패스워드
        String targetEmail = emailSendRequest.getUserEmail();

        // SMTP 서버 정보를 설정한다.
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", 465);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(user, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));

            //수신자메일주소
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(targetEmail));

            // Subject
            message.setSubject("Mail send test"); //메일 제목을 입력

            // Random 문자열 생성
            int leftLimit = 97; // letter 'a'
            int rightLimit = 122; // letter 'z'
            int targetStringLength = 8;
            Random random = new Random();
            String cryptogram = random.ints(leftLimit, rightLimit + 1)
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString().toUpperCase();
            System.out.println(cryptogram);

            // Cryptogram db에 저장
            if(!cryptogramService.addCryptogram(cryptogram, targetEmail).getTargetEmail().equals(targetEmail)) {
                System.out.println("Cryptogram 저장 실패!!!!!!!!!!");
                throw new SuperException(CustomExceptionType.CRYPTOGRAM_SAVE_EXCEPTION);
            }

            // Text
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("안녕하세요!\n");
            stringBuffer.append(targetEmail + " 님의 인증문자열은 '" + cryptogram + "' 입니다.\n");
            stringBuffer.append("받으신 인증문자열을 인증칸에 넣어서 인증을 완료하시고 회원가입을 진행해주세요.\n");
            stringBuffer.append("감사합니다!");
            message.setText(stringBuffer.toString());    //메일 내용을 입력

            // send the message
            Transport.send(message); ////전송
            System.out.println("email sent successfully!!!");
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (SuperException e) {
            e.printStackTrace();
        }

        return EmailSendResponse.builder()
                .httpStatus(SuccessType.EMAIL_SEND_SUCCESS.getHttpStatus())
                .responseCode(SuccessType.EMAIL_SEND_SUCCESS.getResponseCode())
                .result(SuccessType.EMAIL_SEND_SUCCESS.getResult())
                .responseMessage(SuccessType.EMAIL_SEND_SUCCESS.getResponseMessage())
                .build();
    }

}
