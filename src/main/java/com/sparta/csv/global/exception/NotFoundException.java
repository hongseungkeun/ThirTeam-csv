package com.sparta.csv.global.exception;

import com.sparta.csv.global.exception.error.ErrorCode;

public class NotFoundException extends CustomRuntimeException{
    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
