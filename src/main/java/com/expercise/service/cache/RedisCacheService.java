package com.expercise.service.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class RedisCacheService {

    @Autowired
    private ObjectRedisTemplate redisTemplate;

    public <T extends Serializable> void rightPush(String key, T value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    public <T extends Serializable> void addAllToList(String key, Collection<T> values) {
        redisTemplate.opsForList().leftPushAll(key, (Collection<Serializable>) values);
    }

    public <T extends Serializable> void set(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public <T extends Serializable> T get(String key) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public <T extends Serializable> T leftPop(String key) {
        return (T) redisTemplate.opsForList().leftPop(key);
    }

    public void zAdd(String key, Serializable value, Long score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    public Long zRevRankFor(String key, Long id) {
        return redisTemplate.opsForZSet().reverseRank(key, id);
    }

    public Set<ZSetOperations.TypedTuple<Serializable>> findScoresByKey(String key, int start, int count) {
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, count);
    }

    public void putToHashMap(String mapName, Object key, Object value) {
        redisTemplate.opsForHash().put(mapName, key, value);
    }

    public <K, V> Map<K, V> getMap(String mapName) {
        return (Map<K, V>) redisTemplate.opsForHash().entries(mapName);
    }

    public void incrementHashMapValue(String mapName, Object key) {
        redisTemplate.opsForHash().increment(mapName, key, 1);
    }

    public <T extends Serializable> List<T> getList(String listName) {
        return (List<T>) redisTemplate.opsForList().range(listName, 0, redisTemplate.opsForList().size(listName));
    }

}