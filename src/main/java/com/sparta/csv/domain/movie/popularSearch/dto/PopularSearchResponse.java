package com.sparta.csv.domain.movie.popularSearch.dto;

public record PopularSearchResponse(
        Long rating,
        String search,
        Long count
) {
}
