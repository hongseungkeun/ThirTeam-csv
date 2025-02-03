package com.sparta.csv.domain.screening.dto.response;

import java.time.LocalDateTime;

public record ScreeningResponse(
        Long theaterId,
        LocalDateTime screeningTime) {
}
