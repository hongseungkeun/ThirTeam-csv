package com.sparta.csv.domain.theater.dto.response;

import com.sparta.csv.domain.movie.dto.response.MovieResponse;
import com.sparta.csv.domain.movie.entity.Movie;
import com.sparta.csv.domain.theater.entity.Theater;
import lombok.Builder;

import java.time.LocalDate;
@Builder
public record TheaterResponse (
        Long id,
        String name

){
    public static TheaterResponse from(Theater theater){
        return TheaterResponse.builder()
                .id(theater.getId())
                .name(theater.getName())
                .build();
    }
}
