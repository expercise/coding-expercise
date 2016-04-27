package com.expercise.service.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class CacheService {

    @Autowired
    @Qualifier("objectRedisTemplate")
    private RedisTemplate<String, Serializable> redisTemplate;


    public <T extends Serializable> void rightPush(String key, T value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public <T extends Serializable> T leftPop(String key) {
        return (T) redisTemplate.opsForList().leftPop(key);
    }
}