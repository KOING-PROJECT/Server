package com.koing.server.koing_server.config.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@Configuration
@RequiredArgsConstructor
@Profile(value = "test")
public class EmbeddedRedisConfig {

    @Value("${spring.redis.host}")
    public String host;
    @Value("${spring.redis.port}")
    private int port;
    private RedisServer redisServer;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        final RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration(host, port);
        final LettuceConnectionFactory redisConnectionFactory = new LettuceConnectionFactory(standaloneConfiguration);
        log.info("EmbeddedRedisConnectionFactory(Lettuce) configuration enabled host :: {}, port :: {} ",
                redisConnectionFactory.getHostName(), redisConnectionFactory.getPort());
        return redisConnectionFactory;
    }

    @PostConstruct
    public void redisServer() {
        redisServer = RedisServer.builder()
                .port(port)
                .setting("maxmemory 128M") //maxheap 128M
                .build();
        try {
            redisServer.start();
            log.info("임베디드 레디스 서버 시작 {} port ", port);
        } catch (RuntimeException e) {
            log.info("레디스 서버가 이미 실행중입니다. {} port", port);
        }
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
            log.info("임베디드 레디스 서버 종료 {} port", port);
        }
    }
}
