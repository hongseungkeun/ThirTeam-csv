package com.sparta.csv.domain.screening.dto.response;

import java.time.LocalDateTime;

public record screeningResponse(
        Long theaterId,
        LocalDateTime screeningTime) {
}
