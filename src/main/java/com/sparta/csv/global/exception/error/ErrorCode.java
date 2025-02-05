package com.sparta.csv.global.exception.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 유저 관련 익셉션
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED,"Token Not Found"),
    FORBIDDEN_USER_ACCESS(HttpStatus.FORBIDDEN, "사용자의 리소스에 접근할 권한이 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "조회되는 회원 정보가 없습니다."),
    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED,"가입되지 않은 회원입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 비밀번호 혹은 기존과 동일한 비밀번호입니다."),

    // 예매 관련 익셉션
    BOOKING_NOT_AVAILABLE(HttpStatus.BAD_REQUEST, "해당 예매 내역을 찾을 수 없거나, 완료된 상태가 아닙니다."),
    BOOKING_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 예매 내역을 찾을 수 없습니다."),
    ALREADY_EXIST_BOOKED_SEAT(HttpStatus.CONFLICT, "이미 예약된 좌석입니다"),

    // 좌석 관련 익셉션
    SEAT_NOT_IN_THEATER(HttpStatus.BAD_REQUEST, "선택한 좌석이 해당 상영관에 존재하지 않습니다."),
    SEAT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 좌석을 찾을 수 없습니다."),
    SEAT_NOT_REMAIN(HttpStatus.BAD_REQUEST, "남은 좌석이 없습니다.");

    private final HttpStatus status;
    private final String message;
}
