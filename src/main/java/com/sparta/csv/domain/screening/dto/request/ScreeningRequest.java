package com.sparta.csv.domain.screening.dto.request;

import java.time.LocalDateTime;

public record ScreeningRequest(
        Long theaterId,
        LocalDateTime screeningTime
) {
}