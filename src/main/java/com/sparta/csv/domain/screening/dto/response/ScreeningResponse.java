package com.sparta.csv.domain.screening.dto.response;

import com.sparta.csv.domain.screening.entity.Screening;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ScreeningResponse(
        Long theaterId,
        LocalDateTime screeningTime) {
    public static ScreeningResponse from(Screening screening) {
        return ScreeningResponse.builder()
                .theaterId(screening.getScreeningId())
                .screeningTime(screening.getShowTime())
                .build();
    }
}
