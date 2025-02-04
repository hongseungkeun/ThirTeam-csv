package com.sparta.csv.domain.booking.exception;

import com.sparta.csv.global.exception.CustomRuntimeException;
import com.sparta.csv.global.exception.error.ErrorCode;

public class SeatNotInTheaterException extends CustomRuntimeException {
    public SeatNotInTheaterException(ErrorCode errorCode) {
        super(errorCode);
    }
}
