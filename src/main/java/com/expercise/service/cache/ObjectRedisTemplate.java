package com.expercise.service.cache;

import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;

public final class ObjectRedisTemplate extends RedisTemplate<String, Serializable> {
}
