package com.sparta.csv.domain.screening.dto.request;

import java.time.LocalDateTime;

public record CreateScreeningRequest(
        Long theaterId,
        LocalDateTime screeningTime
) {
}