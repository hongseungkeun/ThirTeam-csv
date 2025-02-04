package com.sparta.csv.domain.movie.dto.request;

import java.time.LocalDate;

public record CreateMovieRequest(
        String title,
        Integer runTime,
        LocalDate release_date,
        String description
) {
}
