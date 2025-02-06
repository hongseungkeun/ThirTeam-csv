package com.sparta.csv.domain.booking.dto.response;

public record TopBookMovie(
        Integer rank,
        String title,
        Long bookingCount) {
}
