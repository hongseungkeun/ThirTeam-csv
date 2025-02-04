package com.sparta.csv.global.exception;

import com.sparta.csv.global.exception.error.ErrorCode;

public class BadRequestException extends CustomRuntimeException {
	public BadRequestException(ErrorCode errorCode) {
		super(errorCode);
	}
}
