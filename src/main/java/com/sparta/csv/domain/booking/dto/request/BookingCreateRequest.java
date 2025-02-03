package com.sparta.csv.domain.booking.dto.request;

import java.util.List;

public record BookingCreateRequest(
    List<Long> seatIds
) {
}
