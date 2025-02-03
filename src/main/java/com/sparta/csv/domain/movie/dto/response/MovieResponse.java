package com.sparta.csv.domain.movie.dto.response;

import com.sparta.csv.domain.movie.entity.Movie;
import lombok.Builder;

import java.time.LocalDate;
@Builder
public record MovieResponse (
        Long id,
        String title,
        Integer runTime,
        LocalDate release_date,
        String description

){
    public static MovieResponse from(Movie movie){
        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .runTime(movie.getRunTime())
                .release_date(movie.getRelease_date())
                .description(movie.getDescription())
                .build();
    }
}
