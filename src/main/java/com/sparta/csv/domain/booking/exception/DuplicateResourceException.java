package com.sparta.csv.domain.booking.exception;

import com.sparta.csv.global.exception.CustomRuntimeException;
import com.sparta.csv.global.exception.error.ErrorCode;

public class DuplicateResourceException extends CustomRuntimeException {
    public DuplicateResourceException(ErrorCode errorCode) {
        super(errorCode);
    }
}
