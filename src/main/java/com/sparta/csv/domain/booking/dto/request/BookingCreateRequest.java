package com.sparta.csv.domain.booking.dto.request;

import com.sparta.csv.domain.booking.dto.BookingValidation;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record BookingCreateRequest(
        @NotEmpty(message = BookingValidation.SEAT_IDS_NOT_EMPTY_MESSAGE)
        List<@NotNull(message = BookingValidation.SEAT_ID_NOT_NULL_MESSAGE) Long> seatIds
) {
}
