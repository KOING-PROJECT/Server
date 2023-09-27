package com.koing.server.koing_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class KoingServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(KoingServerApplication.class, args);
    }

}
