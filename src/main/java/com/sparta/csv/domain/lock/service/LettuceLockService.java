package com.sparta.csv.domain.lock.service;

import com.sparta.csv.domain.lock.exception.DistributedLockException;
import com.sparta.csv.domain.lock.repository.LettuceLockRepository;
import com.sparta.csv.global.exception.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LettuceLockService {

    private final LettuceLockRepository lettuceLockRepository;

    private static final long WAIT_TIME = 6L;
    private static final long LEASE_TIME = 5L;

    public boolean lock(String key) {
        long startTime = System.currentTimeMillis();

        try {
            while (System.currentTimeMillis() - startTime < WAIT_TIME * 1000) {
                // setnx 명령어 사용 - key(key) value("lock")
                if (lettuceLockRepository.tryLock(key, LEASE_TIME)) {
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
        lettuceLockRepository.unlock(key);
    }
}
