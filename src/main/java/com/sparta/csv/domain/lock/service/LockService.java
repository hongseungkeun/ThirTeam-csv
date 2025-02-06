package com.sparta.csv.domain.lock.service;

public interface LockService {

    long WAIT_TIME = 6L;
    long LEASE_TIME = 5L;
    String LOCK_PREFIX = "lock:";

    boolean lock(String key);

    void unlock(String key);
}
