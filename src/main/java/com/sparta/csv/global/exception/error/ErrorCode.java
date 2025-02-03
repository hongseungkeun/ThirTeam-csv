package com.sparta.csv.global.exception.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 유저 관련 익셉션

    // 예매 관련 익셉션
    ALREADY_EXIST_BOOKED_SEAT(HttpStatus.CONFLICT, "이미 예약된 좌석입니다"),

    // 좌석 관련 익셉션
    SEAT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 좌석을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
