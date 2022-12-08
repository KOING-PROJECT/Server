package com.koing.server.koing_server.common.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
            FileReader resources= new FileReader("C:/Users/MinGyu/DeskTop/Programming/PCRoomBooking_config/email_config.properties");

            // 노트북 버전 경로
//            FileReader resources= new FileReader("C:/Users/littl/IdeaProjects/pcRoomBooking_config/email_config.properties");
            Properties properties = new Properties();

            properties.load(resources);
            this.email = properties.get("email").toString();
            this.password = properties.get("password").toString();

        } catch (Exception e) {
            System.out.println("email_config file read error");
            throw new UsernameNotFoundException("not found file");
            // exception 발생 시키기
        }
    }

}