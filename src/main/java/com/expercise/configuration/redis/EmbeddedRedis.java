package com.expercise.configuration.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
@Profile("!prod")
public class EmbeddedRedis {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmbeddedRedis.class);

    @Value("${spring.redis.port}")
    private int redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void startRedis() {
        try {
            redisServer = new RedisServer(redisPort);
            redisServer.start();
        } catch (Exception e) {
            LOGGER.error("Embedded redis couldn't be started: ", e);
        }
    }

    @PreDestroy
    public void stopRedis() {
        try {
            redisServer.stop();
        } catch (Exception e) {
            LOGGER.error("Embedded redis couldn't be stopped: ", e);
        }
    }
}