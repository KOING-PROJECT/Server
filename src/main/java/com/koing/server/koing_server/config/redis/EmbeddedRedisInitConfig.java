package com.koing.server.koing_server.config.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

@Slf4j
@Configuration
@RequiredArgsConstructor
@Profile(value = "local | test")
public class EmbeddedRedisInitConfig {

    private final RedisTemplate<String, String> redisTemplate;

    @EventListener(ApplicationReadyEvent.class)
    public void initEmbeddedRedis() {
        final Set<String> keys = redisTemplate.keys("*");
        if (keys != null) {
            redisTemplate.delete(keys);
            log.info("임베디드 레디스 데이터 초기화");
        }
    }
}
