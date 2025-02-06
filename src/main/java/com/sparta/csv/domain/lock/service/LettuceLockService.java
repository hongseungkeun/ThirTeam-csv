package com.sparta.csv.domain.lock.service;

import com.sparta.csv.domain.lock.exception.DistributedLockException;
import com.sparta.csv.domain.lock.repository.LettuceLockRepository;
import com.sparta.csv.global.exception.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "lock.type", havingValue = "lettuce", matchIfMissing = true)
@Slf4j
public class LettuceLockService implements LockService {

    private final LettuceLockRepository lettuceLockRepository;

    public boolean lock(String key) {
        log.info("lockType: Lettuce");

        long startTime = System.currentTimeMillis();

        try {
            while (System.currentTimeMillis() - startTime < WAIT_TIME * 1000) {
                // setnx 명령어 사용 - key(key) value("lock")
                if (lettuceLockRepository.tryLock(LOCK_PREFIX + key, LEASE_TIME)) {
                    return true;
                }

                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            throw new DistributedLockException(ErrorCode.LOCK_TIMEOUT);
        }

        return false;
    }

    public void unlock(String key) {
        lettuceLockRepository.unlock(LOCK_PREFIX + key);
    }
}
