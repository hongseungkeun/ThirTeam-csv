package com.sparta.csv.global.exception;

import com.sparta.csv.global.exception.error.ErrorCode;

public class UnauthorizedException extends CustomRuntimeException {
	public UnauthorizedException(ErrorCode errorCode) {
		super(errorCode);
	}
}
