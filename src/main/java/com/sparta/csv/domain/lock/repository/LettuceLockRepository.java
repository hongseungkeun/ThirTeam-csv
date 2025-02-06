package com.sparta.csv.domain.lock.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class LettuceLockRepository {

    private final RedisTemplate<String, String> redisTemplate;

    private static final String LOCK_VALUE = "locked";

    public Boolean tryLock(String key, long leaseTime) {
        return redisTemplate.opsForValue()
                .setIfAbsent(key, LOCK_VALUE, Duration.ofSeconds(leaseTime));
    }

    public void unlock(String key) {
        redisTemplate.delete(key);
    }
}
