package com.koing.server.koing_server.common.properties;

import com.koing.server.koing_server.common.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.util.Properties;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class EmailProperties {

    private String email;
    private String password;

    public void initEmailProperties() {
        try {
            // PC버전 경로
//            FileReader resources= new FileReader("C:/Users/MinGyu/DeskTop/Programming/PCRoomBooking_config/email_config.properties");

            // 노트북 버전 경로
            FileReader resources= new FileReader("C:/Users/littl/KOING/server/src/main/resources/email_config.properties");
            Properties properties = new Properties();

            properties.load(resources);
            this.email = properties.get("email").toString();
            this.password = properties.get("password").toString();

        } catch (Exception e) {
            System.out.println("email_config file read error");
            throw new NotFoundException("Email property를 찾을 수 없습니다.");
            // exception 발생 시키기
        }
    }

}