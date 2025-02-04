package com.sparta.csv.global.exception;

import com.sparta.csv.global.exception.error.ErrorCode;

public class ForbiddenException extends CustomRuntimeException {
    public ForbiddenException(ErrorCode errorCode) {
        super(errorCode);
    }
}