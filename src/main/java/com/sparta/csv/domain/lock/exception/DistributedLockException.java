package com.sparta.csv.domain.lock.exception;

import com.sparta.csv.global.exception.CustomRuntimeException;
import com.sparta.csv.global.exception.error.ErrorCode;

public class DistributedLockException extends CustomRuntimeException {
    public DistributedLockException(ErrorCode errorCode) {
        super(errorCode);
    }
}
