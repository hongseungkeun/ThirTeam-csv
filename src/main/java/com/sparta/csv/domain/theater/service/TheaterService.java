package com.sparta.csv.domain.theater.service;

import com.sparta.csv.domain.movie.dto.response.MovieResponse;
import com.sparta.csv.domain.theater.dto.request.TheaterCreateRequest;
import com.sparta.csv.domain.theater.dto.response.TheaterResponse;
import com.sparta.csv.domain.theater.entity.Theater;
import com.sparta.csv.domain.theater.repository.TheaterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.sparta.csv.domain.theater.entity.Theater.newTheater;

@Service
@RequiredArgsConstructor
public class TheaterService {

    private final TheaterRepository theaterRepository;

    public TheaterResponse createTheater(TheaterCreateRequest req) {
        Theater theater = newTheater(req);
        theater = theaterRepository.save(theater);
        return TheaterResponse.from(theater);
    }
    public Theater findTheaterById(Long theaterId) {
        return theaterRepository.findById(theaterId).orElseThrow();
    }

}
