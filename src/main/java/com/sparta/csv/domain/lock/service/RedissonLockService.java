package com.sparta.csv.domain.lock.service;

import com.sparta.csv.domain.lock.exception.DistributedLockException;
import com.sparta.csv.global.exception.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "lock.type", havingValue = "redisson")
@Slf4j
public class RedissonLockService implements LockService {

    private final RedissonClient redissonClient;

    @Override
    public boolean lock(String key) {
        log.info("lockType: Redisson");

        RLock rLock = redissonClient.getLock(LOCK_PREFIX + key);

        try {
            return rLock.tryLock(WAIT_TIME, LEASE_TIME, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new DistributedLockException(ErrorCode.LOCK_TIMEOUT);
        }
    }

    @Override
    public void unlock(String key) {
        RLock rLock = redissonClient.getLock(LOCK_PREFIX + key);

        // 현재 쓰레드가 락을 가지고 있을 경우에만 락 해제
        if (rLock.isHeldByCurrentThread()) {
            rLock.unlock();
        }
    }
}
